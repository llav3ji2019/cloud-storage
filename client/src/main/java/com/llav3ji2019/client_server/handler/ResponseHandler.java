package com.llav3ji2019.client_server.handler;

import com.llav3ji2019.client_server.utils.Callback;
import com.llav3ji2019.client_server.utils.Pipeline;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import structures.AppCommand;
import structures.RequestData;

public class ResponseHandler extends ChannelInboundHandlerAdapter {
    private final Callback callback;

    public ResponseHandler(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RequestData data && data.command() == AppCommand.RESPONSE) {
            String filePath = data.parameters().get(0);
            long size = Long.parseLong(data.parameters().get(1));
            Pipeline.switchForDownloadFile(ctx.pipeline(), filePath, size, callback);
        }
    }
}
