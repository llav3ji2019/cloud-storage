package com.llav3ji2019.client_server.handler;

import com.llav3ji2019.client_server.utils.Callback;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private final Callback onMessageReceivedCallback;
    private final static Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler(Callback onMessageReceivedCallback) {
        this.onMessageReceivedCallback = onMessageReceivedCallback;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
        if (onMessageReceivedCallback != null) {
            onMessageReceivedCallback.callback(s);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        LOGGER.log(Level.INFO, cause.getMessage());
    }
}
