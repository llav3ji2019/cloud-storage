package com.llav3ji2019.client_server.service;

import com.llav3ji2019.client_server.request.Request;
import com.llav3ji2019.client_server.sender.RequestSenderFactory;
import com.llav3ji2019.client_server.sender.Sender;

public class RequestService {
    private final RequestSenderFactory factory;

    public RequestService(RequestSenderFactory factory) {
        this.factory = factory;
    }

    public void send(Request request) {
        Sender currentRequestSender = factory.createSender(request.getRequestData());
        currentRequestSender.send();
    }
}
