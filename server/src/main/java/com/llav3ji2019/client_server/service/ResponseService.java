package com.llav3ji2019.client_server.service;

import com.llav3ji2019.client_server.command.Command;
import com.llav3ji2019.client_server.sender.DownloadResponseSender;
import com.llav3ji2019.client_server.sender.LoginResponseSender;
import com.llav3ji2019.client_server.sender.MoveResponseSender;
import com.llav3ji2019.client_server.sender.ResponseSender;
import com.llav3ji2019.client_server.sender.SaveResponseSender;
import io.netty.channel.Channel;
import structures.RequestData;

public class ResponseService {
    private final ResponseSender sender;
    private final Channel channel;
    private final Command command;
    private final RequestData data;

    public ResponseService(Channel channel, Command command, RequestData data) {
        this.channel = channel;
        this.command = command;
        this.data = data;
        sender = generateResponseSender();
    }

    public void send() {
        sender.send();
    }

    private ResponseSender generateResponseSender() {
        ResponseSender result = new LoginResponseSender(channel, command, data);
        ResponseSender moveResponseSender = new MoveResponseSender(channel, command, data);
        ResponseSender saveResponseSender = new SaveResponseSender(channel, command, data);
        ResponseSender downloadResponseSender = new DownloadResponseSender(channel, command, data);

        result.setNextMessageSender(moveResponseSender);
        moveResponseSender.setNextMessageSender(saveResponseSender);
        saveResponseSender.setNextMessageSender(downloadResponseSender);
        return result;
    }
}
