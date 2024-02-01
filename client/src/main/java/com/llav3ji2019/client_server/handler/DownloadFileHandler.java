package com.llav3ji2019.client_server.handler;

import com.llav3ji2019.client_server.utils.Callback;
import com.llav3ji2019.client_server.utils.Pipeline;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadFileHandler extends ChannelInboundHandlerAdapter {
    private final String filePath;
    private final long fileSize;
    private final Callback callback;

    public DownloadFileHandler(String filePath, long fileSize, Callback callback) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.callback = callback;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Path path = Path.of(filePath);
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            while (byteBuf.readableBytes() > 0) {
                outputStream.write(byteBuf.readByte());
            }
            byteBuf.release();
            long currentSize = Files.size(path);
            if (fileSize == currentSize) {
                Pipeline.switchDefault(ctx.channel().pipeline(), callback);
            }
        }
    }
}
