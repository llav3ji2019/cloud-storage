package com.llav3ji2019.client_server.command;

import structures.RequestData;
import structures.User;

public class CommandFactory {
    public Command createCommand(User currentUserOnServer, RequestData data) {
        return switch (data.command()) {
            case LOGIN -> new LoginCommand(currentUserOnServer, data.parameters());
            case SAVE -> new SaveCommand(data.parameters());
            case DOWNLOAD -> new DownloadCommand(data.parameters());
            case MOVE -> new MoveCommand(data.parameters());
            case RESPONSE -> throw new RuntimeException("Client cannot send server response");
        };
    }
}
