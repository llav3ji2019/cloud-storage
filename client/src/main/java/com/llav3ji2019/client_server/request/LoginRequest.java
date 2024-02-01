package com.llav3ji2019.client_server.request;

import structures.AppCommand;
import structures.RequestData;
import structures.User;

import java.util.Scanner;

public class LoginRequest implements Request {
    private RequestData data;

    @Override
    public void generate() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your username");
        String username = in.next();
        System.out.println("Enter your password");
        String password = in.next();
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        data = RequestData.builder()
                .command(AppCommand.LOGIN)
                .parameters(user.toStringArray())
                .build();
    }

    @Override
    public RequestData getRequestData() {
        return data;
    }
}
