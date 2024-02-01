package com.llav3ji2019.client_server.handler;

import com.llav3ji2019.client_server.utils.Pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveFileHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = Logger.getLogger(ServerHandler.class.getName());
    private final Path filepath;
    private final long size;

    public SaveFileHandler(String filepath, long size) {
        this.filepath = Path.of(filepath);
        this.size = size;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.log(Level.INFO, cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        try (OutputStream outputStream = new FileOutputStream(filepath.toFile())) {
            while (byteBuf.readableBytes() > 0) {
                outputStream.write(byteBuf.readByte());
            }
            byteBuf.release();

            long currentSize = Files.size(filepath);
            if (size == currentSize) {
                Pipeline.switchDefault(ctx.pipeline());
            }
        }
    }
}
