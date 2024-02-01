package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.network.Network;
import com.llav3ji2019.client_server.utils.Pipeline;
import structures.RequestData;

public class DownloadRequestSender implements Sender {
    private final Network network;
    private final RequestData data;

    public DownloadRequestSender(Network network, RequestData data) {
        this.network = network;
        this.data = data;
    }

    @Override
    public void send() {
        network.sendMessage(data);
        Pipeline.switchForDownloadResponse(network.getChannel().pipeline(), network.getCallback());
    }
}
