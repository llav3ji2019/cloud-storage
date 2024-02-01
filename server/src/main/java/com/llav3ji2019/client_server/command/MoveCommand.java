package com.llav3ji2019.client_server.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class MoveCommand extends Command {
    private static final String FILE_IS_NOT_EXISTED = "File doesn't exist";
    private static final String SUCCESSFULLY_MOVED = "File is successfully moved to another dir";
    private static final String FOLDER_CREATION_FAILED = "Can't create folder";
    private static final String FILE_MOVEMENT_FAILED = "Can't move file";

    private final Path srcPath;
    private final Path destFolder;

    public MoveCommand(List<String> parameters) {
        srcPath = Path.of(parameters.get(0));
        destFolder = Path.of(parameters.get(1));
    }

    @Override
    public void execute() {
        if (Files.notExists(srcPath)) {
            responseMsg.append(FILE_IS_NOT_EXISTED);
            return;
        }
        if (srcPath.getParent().equals(destFolder)) {
            responseMsg.append(SUCCESSFULLY_MOVED);
            return;
        }
        if (Files.notExists(destFolder)) {
            try {
                Files.createDirectories(destFolder);
            } catch (IOException e) {
                responseMsg.append(FOLDER_CREATION_FAILED);
                return;
            }
        }
        try {
            Path fullDestPath = destFolder.resolve(srcPath.getFileName());
            Files.move(srcPath, fullDestPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            responseMsg.append(FILE_MOVEMENT_FAILED);
            return;
        }
        responseMsg.append(SUCCESSFULLY_MOVED);
    }
}
