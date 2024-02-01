package com.llav3ji2019.client_server.request;

import structures.AppCommand;
import structures.RequestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MoveRequest implements Request {
    private static final String DEFAULT_SERVER_FOLDER = "server_data";

    private RequestData data;

    @Override
    public void generate() {
        Scanner in = new Scanner(System.in);
        List<String> parameters = new ArrayList<>();
        System.out.println("Enter your server source filepath");
        String srcFilepath = in.next();
        parameters.add(DEFAULT_SERVER_FOLDER + srcFilepath);
        System.out.println("Enter your server dest folder path");
        String destFolderPath = in.next();
        parameters.add(DEFAULT_SERVER_FOLDER + destFolderPath);

        data = RequestData.builder()
                .command(AppCommand.MOVE)
                .parameters(parameters)
                .build();
    }

    @Override
    public RequestData getRequestData() {
        return data;
    }
}
