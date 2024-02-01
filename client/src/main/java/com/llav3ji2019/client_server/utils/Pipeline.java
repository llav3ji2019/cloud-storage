package com.llav3ji2019.client_server.utils;

import com.llav3ji2019.client_server.handler.ClientHandler;
import com.llav3ji2019.client_server.handler.DownloadFileHandler;
import com.llav3ji2019.client_server.handler.ResponseHandler;
import decoder.RequestDataDecoder;
import encoder.RequestDataEncode;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Pipeline {

    public static void switchDefault(ChannelPipeline pipeline, Callback onMessageReceivedCallback) {
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new RequestDataEncode());
        pipeline.addLast(new ClientHandler(onMessageReceivedCallback));
        pipeline.removeFirst();
    }

    public static void switchForDownloadFile(ChannelPipeline pipeline, String fileName, long size, Callback onMessageReceivedCallback) {
        pipeline.addFirst(new DownloadFileHandler(fileName, size, onMessageReceivedCallback));
        pipeline.removeLast();
        pipeline.removeLast();
        pipeline.removeLast();
        pipeline.removeLast();
    }

    public static void switchForSaveFile(ChannelPipeline pipeline) {
        pipeline.addFirst(new ChunkedWriteHandler());
        pipeline.removeLast();
        pipeline.removeLast();
        pipeline.removeLast();
    }

    public static void switchForDownloadResponse(ChannelPipeline pipeline, Callback onMessageReceivedCallback){
        pipeline.removeFirst();
        pipeline.addFirst(new RequestDataDecoder());
        pipeline.addLast(new ResponseHandler(onMessageReceivedCallback));
    }

}
