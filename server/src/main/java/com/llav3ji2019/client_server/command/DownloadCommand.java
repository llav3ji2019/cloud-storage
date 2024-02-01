package com.llav3ji2019.client_server.command;

import lombok.Getter;
import structures.AppCommand;
import structures.RequestData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DownloadCommand extends Command {
    private static final String FILE_IS_NOT_EXISTS = "Err: Can't find file in server";
    private static final String SUCCESS_MSG = "OK: Configuration for downloading is completed";

    @Getter
    private RequestData data;

    private final Path serverFilepath;
    private final Path destFilepath;

    public DownloadCommand(List<String> parameters) {
        serverFilepath = Path.of(parameters.get(0));
        destFilepath = Path.of(parameters.get(1));
    }

    @Override
    public void execute() {
        if (Files.notExists(serverFilepath)) {
            responseMsg.append(FILE_IS_NOT_EXISTS);
            return;
        }
        String filesize;
        try {
            filesize = getFileSize();
        } catch (IOException e) {
            responseMsg.append(FILE_IS_NOT_EXISTS);
            return;
        }
        data = RequestData.builder()
                .command(AppCommand.RESPONSE)
                .parameters(
                        List.of(
                                destFilepath.toString(),
                                filesize
                        ))
                .build();

        responseMsg.append(SUCCESS_MSG);
    }

    private String getFileSize() throws IOException {
        return String.valueOf(Files.size(serverFilepath));
    }
}
