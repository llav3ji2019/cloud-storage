package com.llav3ji2019.client_server.utils;

import com.llav3ji2019.client_server.command.CommandFactory;
import com.llav3ji2019.client_server.handler.SaveFileHandler;
import com.llav3ji2019.client_server.handler.ServerHandler;
import decoder.RequestDataDecoder;
import encoder.RequestDataEncode;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Pipeline {
    public static void switchDefault(ChannelPipeline pipeline) {
        pipeline.addLast(new RequestDataDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new ServerHandler(new CommandFactory()));
        pipeline.removeFirst();
    }

    public static void switchForUpload(ChannelPipeline pipeline, String fileName, long size) {
        pipeline.addFirst(new SaveFileHandler(fileName, size));
        pipeline.removeLast();
        pipeline.removeLast();
        pipeline.removeLast();
    }

    public static void switchForDownload(ChannelPipeline pipeline) {
        pipeline.addFirst(new ChunkedWriteHandler());
        pipeline.removeLast();
        pipeline.removeLast();
        pipeline.removeLast();
    }

    public static void switchForDownloadResponse(ChannelPipeline pipeline){
        pipeline.removeFirst();
        pipeline.removeFirst();
        pipeline.addFirst(new RequestDataDecoder());
        pipeline.addFirst(new RequestDataEncode());
    }
}
