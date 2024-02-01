package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.command.Command;
import io.netty.channel.Channel;
import structures.RequestData;

public abstract class ResponseSender {
    protected ResponseSender nextResponseSender;
    protected final Channel channel;
    protected final Command command;
    protected final RequestData data;

    protected ResponseSender(Channel channel, Command command, RequestData data) {
        this.channel = channel;
        this.command = command;
        this.data = data;
    }

    public void setNextMessageSender(ResponseSender nextResponseSender) {
        this.nextResponseSender = nextResponseSender;
    }

    protected void broadcastMessage(String msg) {
        channel.writeAndFlush(msg);
    }

    protected void broadcastResponse(RequestData data) {
        channel.writeAndFlush(data);
    }

    public abstract void send();
}
