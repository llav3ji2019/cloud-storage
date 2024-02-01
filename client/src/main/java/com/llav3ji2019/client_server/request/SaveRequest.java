package com.llav3ji2019.client_server.request;

import structures.AppCommand;
import structures.RequestData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveRequest implements Request {
    private static final String DEFAULT_SERVER_FOLDER = "server_data";
    private static final String DEFAULT_CLIENT_FOLDER = "client_data";

    private RequestData data;

    @Override
    public void generate() {
        Scanner in = new Scanner(System.in);
        List<String> parameters = new ArrayList<>();
        System.out.println("Enter your source filepath");
        String srcFilepath = DEFAULT_CLIENT_FOLDER + in.next();
        while (Files.notExists(Path.of(srcFilepath))) {
            System.out.println("There are no such file in your folder");
            System.out.println("Enter your source filepath again");
            srcFilepath = DEFAULT_CLIENT_FOLDER + in.next();
        }
        parameters.add(srcFilepath);
        System.out.println("Enter your destination path on server");
        String destPath = DEFAULT_SERVER_FOLDER + in.next();
        parameters.add(destPath);

        data = RequestData.builder()
                .command(AppCommand.SAVE)
                .parameters(parameters)
                .build();
    }

    @Override
    public RequestData getRequestData() {
        return data;
    }
}
