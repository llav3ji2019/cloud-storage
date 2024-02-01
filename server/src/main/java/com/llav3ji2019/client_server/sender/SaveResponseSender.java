package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.command.Command;
import com.llav3ji2019.client_server.command.SaveCommand;
import com.llav3ji2019.client_server.utils.Pipeline;
import io.netty.channel.Channel;
import structures.RequestData;

public class SaveResponseSender extends ResponseSender {
    public SaveResponseSender(Channel channel, Command command, RequestData data) {
        super(channel, command, data);
    }

    @Override
    public void send() {
        if (command instanceof SaveCommand saveCommand) {
            broadcastMessage(command.getResponseMsg() + "\n");

            Pipeline.switchForUpload(channel.pipeline(), saveCommand.getDestPath().toString(), saveCommand.getSize());
        }
        else if (nextResponseSender != null) {
            nextResponseSender.send();
        }
        else {
            throw new RuntimeException();
        }
    }
}
