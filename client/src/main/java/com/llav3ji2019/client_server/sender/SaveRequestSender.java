package com.llav3ji2019.client_server.sender;

import com.llav3ji2019.client_server.network.Network;
import com.llav3ji2019.client_server.utils.Pipeline;
import structures.RequestData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveRequestSender implements Sender {
    private final Network network;
    private final RequestData data;

    public SaveRequestSender(Network network, RequestData data) {
        this.network = network;
        this.data = data;
    }

    @Override
    public void send() {
        String srcFilename = data.parameters().get(0);
        data.parameters().remove(0);
        try {
            data.parameters().add(String.valueOf(Files.size(Path.of(srcFilename))));

            network.sendMessage(data);

            Pipeline.switchForSaveFile(network.getChannel().pipeline());

            Thread.sleep(3000);
            network.sendFile(new File(srcFilename));
            Thread.sleep(3000);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        Pipeline.switchDefault(network.getChannel().pipeline(), network.getCallback());
    }
}
