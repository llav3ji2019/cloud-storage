package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.network.Network;
import structures.RequestData;

public class RequestSenderFactory {
    private final Network network;

    public RequestSenderFactory(Network network) {
        this.network = network;
    }

    public Sender createSender(RequestData data) {
        return switch (data.command()) {
            case LOGIN -> new LoginRequestSender(network, data);
            case SAVE -> new SaveRequestSender(network, data);
            case DOWNLOAD -> new DownloadRequestSender(network, data);
            case MOVE -> new MoveRequestSender(network, data);
            case RESPONSE -> throw new RuntimeException("Client cannot send server response");
        };
    }
}
