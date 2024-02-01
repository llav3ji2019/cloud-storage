package com.llav3ji2019.client_server.request;

import structures.AppCommand;
import structures.RequestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DownloadRequest implements Request {
    private static final String DEFAULT_SERVER_FOLDER = "server_data";
    private static final String DEFAULT_CLIENT_FOLDER = "client_data";

    private RequestData data;

    @Override
    public void generate() {
        Scanner in = new Scanner(System.in);
        List<String> parameters = new ArrayList<>();
        System.out.println("Enter your server filepath");
        String serverFilepath = in.next();
        parameters.add(DEFAULT_SERVER_FOLDER + serverFilepath);
        System.out.println("Enter your destination path");
        String destPath = in.next();
        parameters.add(DEFAULT_CLIENT_FOLDER + destPath);

        data = RequestData.builder()
                .command(AppCommand.DOWNLOAD)
                .parameters(parameters)
                .build();
    }

    @Override
    public RequestData getRequestData() {
        return data;
    }
}
