package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.network.Network;
import structures.RequestData;

public class LoginRequestSender implements Sender {
    private final Network network;
    private final RequestData data;

    public LoginRequestSender(Network network, RequestData data) {
        this.network = network;
        this.data = data;
    }

    @Override
    public void send() {
        network.sendMessage(data);
    }
}
