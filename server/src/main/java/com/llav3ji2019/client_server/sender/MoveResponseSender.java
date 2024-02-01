package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.command.Command;
import com.llav3ji2019.client_server.command.MoveCommand;
import io.netty.channel.Channel;
import structures.RequestData;

public class MoveResponseSender extends ResponseSender {
    public MoveResponseSender(Channel channel, Command command, RequestData data) {
        super(channel, command, data);
    }

    @Override
    public void send() {
        if (command instanceof MoveCommand) {
            broadcastMessage(command.getResponseMsg() + "\n");
        }
        else if (nextResponseSender != null) {
            nextResponseSender.send();
        }
        else {
            throw new RuntimeException();
        }
    }
}
