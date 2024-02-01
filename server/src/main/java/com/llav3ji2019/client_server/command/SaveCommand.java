package com.llav3ji2019.client_server.command;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Getter
public class SaveCommand extends Command {

    private static final String SUCCESSFULLY_CREATED = "Dirs are successfully created";
    private static final String DIR_CREATION_FAILED = "Can't create dirs";

    private final Path destPath;
    private final long size;

    public SaveCommand(List<String> parameters) {
        destPath = Path.of(parameters.get(0));
        size = Long.parseLong(parameters.get(1));
    }

    @Override
    public void execute() {
        if (Files.notExists(destPath.getParent())) {
            try {
                Files.createDirectories(destPath.getParent());
            } catch (IOException e) {
                responseMsg.append(DIR_CREATION_FAILED);
                return;
            }
        }
        responseMsg.append(SUCCESSFULLY_CREATED);
    }
}
