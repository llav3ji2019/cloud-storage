package com.llav3ji2019.client_server.network;

import com.llav3ji2019.client_server.handler.ClientHandler;
import com.llav3ji2019.client_server.utils.Callback;
import io.netty.handler.stream.ChunkedFile;
import lombok.Getter;
import structures.RequestData;
import encoder.RequestDataEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.io.File;
import java.io.IOException;

@Getter
public class Network {
    private static final String HOST = "localhost";
    private static final int PORT = 5001;

    private SocketChannel channel;
    private final Callback callback;

    public Network(Callback callback) {
        this.callback = callback;
        Thread t = new Thread(() -> {
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap clientBootstrap = new Bootstrap();
                clientBootstrap.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(
                                        new StringDecoder(),
                                        new RequestDataEncode(),
                                        new ClientHandler(callback)
                                );
                            }
                        });
                ChannelFuture future = clientBootstrap.connect(HOST, PORT).sync();
                future.channel()
                        .closeFuture()
                        .sync();
            } catch (Exception ignored) {
            } finally {
                workerGroup.shutdownGracefully();
            }
        });
        t.start();
        while (isNotReady()) {
            Thread.onSpinWait();
        }
    }

    public void close() {
        channel.close();
    }

    public void sendMessage(RequestData clientRequest) {
        if (channel != null) {
            channel.writeAndFlush(clientRequest);
        }
    }

    public void sendFile(File file) throws IOException {
        if (channel != null) {
            channel.writeAndFlush(new ChunkedFile(file));
        }
    }

    private boolean isNotReady() {
        return channel == null;
    }
}
