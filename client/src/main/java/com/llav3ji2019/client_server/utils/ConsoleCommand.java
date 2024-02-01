package com.llav3ji2019.client_server.utils;

public enum ConsoleCommand {
    CLOSE_CMD,
    LOGIN_CMD,
    DOWNLOAD_CMD,
    MOVE_CMD,
    SAVE_CMD;

    public static ConsoleCommand getConsoleCommand(String command) {
        return switch (command) {
            case "/close" -> CLOSE_CMD;
            case "/login" -> LOGIN_CMD;
            case "/download" -> DOWNLOAD_CMD;
            case "/move" -> MOVE_CMD;
            case "/save" -> SAVE_CMD;
            default -> throw new IllegalStateException("Unexpected value: " + command);
        };
    }
}
