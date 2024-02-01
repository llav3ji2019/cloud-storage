package com.llav3ji2019.client_server.handler;

import com.llav3ji2019.client_server.command.Command;
import com.llav3ji2019.client_server.command.CommandFactory;
import com.llav3ji2019.client_server.command.LoginCommand;
import com.llav3ji2019.client_server.service.ResponseService;
import structures.AppCommand;
import structures.RequestData;
import structures.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final String INCORRECT_ARGUMENTS = "Command arguments is bad";
    private static final Logger LOGGER = Logger.getLogger(ServerHandler.class.getName());

    private static User currentUser;
    private final CommandFactory commandFactory;

    public ServerHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RequestData data) {
            if (isRequestDataIncorrect(data)) {
                retryCommand(ctx.channel());
                return;
            }
            handle(ctx.channel() ,data);
        }
    }

    private void handle(Channel channel, RequestData data) {
        Command command = commandFactory.createCommand(currentUser, data);
        command.execute();

        if (command instanceof LoginCommand loginCommand) {
            currentUser = loginCommand.getUserOnSever();
        }
        var service = new ResponseService(channel, command, data);
        service.send();
    }

    private static void retryCommand(Channel channel) {
        channel.writeAndFlush(INCORRECT_ARGUMENTS);
    }

    private boolean isRequestDataIncorrect(RequestData data) {
        return data.parameters().size() < 2 || !AppCommand.contains(data.command());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        currentUser = null;
        LOGGER.log(Level.INFO, cause.getMessage());
        ctx.close();
    }
}
