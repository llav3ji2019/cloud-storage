package com.llav3ji2019.client_server;

import com.llav3ji2019.client_server.command.CommandFactory;
import com.llav3ji2019.client_server.handler.ServerHandler;
import decoder.RequestDataDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {
    private final static Logger LOGGER = Logger.getLogger(ServerApp.class.getName());
    private static final int PORT = 5001;

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(
                                    new RequestDataDecoder(),
                                    new StringEncoder(),
                                    new ServerHandler(new CommandFactory())
                            );
                        }
                    });
            ChannelFuture future = b.bind(PORT).sync();
            future.channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
