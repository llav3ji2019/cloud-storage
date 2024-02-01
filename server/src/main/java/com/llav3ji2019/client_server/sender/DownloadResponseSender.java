package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.command.Command;
import com.llav3ji2019.client_server.command.DownloadCommand;
import com.llav3ji2019.client_server.utils.Pipeline;
import io.netty.channel.Channel;
import io.netty.handler.stream.ChunkedFile;
import structures.RequestData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DownloadResponseSender extends ResponseSender {
    public DownloadResponseSender(Channel channel, Command command, RequestData data) {
        super(channel, command, data);
    }

    @Override
    public void send() {
       if (command instanceof DownloadCommand downloadCommand) {
            Pipeline.switchForDownloadResponse(channel.pipeline());
            broadcastResponse(downloadCommand.getData());
            Pipeline.switchForDownload(channel.pipeline());

            try {
                channel.writeAndFlush(
                        new ChunkedFile(
                                new File(
                                        Path.of(
                                                data.parameters()
                                                        .get(0)
                                        ).toString()
                                )
                        )
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Pipeline.switchDefault(channel.pipeline());
        }
       else if (nextResponseSender != null) {
           nextResponseSender.send();
       }
       else {
           throw new RuntimeException();
       }
    }
}
