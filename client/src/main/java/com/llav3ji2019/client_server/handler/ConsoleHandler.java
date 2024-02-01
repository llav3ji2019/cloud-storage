package com.llav3ji2019.client_server.handler;

import com.llav3ji2019.client_server.network.Network;
import com.llav3ji2019.client_server.request.DownloadRequest;
import com.llav3ji2019.client_server.request.LoginRequest;
import com.llav3ji2019.client_server.request.MoveRequest;
import com.llav3ji2019.client_server.request.Request;
import com.llav3ji2019.client_server.request.SaveRequest;
import com.llav3ji2019.client_server.sender.RequestSenderFactory;
import com.llav3ji2019.client_server.service.RequestService;
import com.llav3ji2019.client_server.utils.ConsoleCommand;
import com.llav3ji2019.client_server.utils.LoginState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.llav3ji2019.client_server.utils.ConsoleCommand.*;

public class ConsoleHandler {
    private static final Scanner IN = new Scanner(System.in);
    private static final Map<ConsoleCommand, Request> commandToRequestMap = new HashMap<>();
    private static final Set<ConsoleCommand> commandsWithTextResponse = new HashSet<>();

    private LoginState state = LoginState.UNIDENTIFIED;
    private final StringBuilder responseFromServer = new StringBuilder();
    private final Network network = new Network(responseFromServer::append);
    private final RequestService requestSender = new RequestService(new RequestSenderFactory(network));

    static {
        commandToRequestMap.putAll(
                Map.of(
                        LOGIN_CMD, new LoginRequest(),
                        DOWNLOAD_CMD, new DownloadRequest(),
                        SAVE_CMD, new SaveRequest(),
                        MOVE_CMD, new MoveRequest()
                )
        );

        commandsWithTextResponse.addAll(
                Set.of(
                        LOGIN_CMD,
                        MOVE_CMD
                )
        );
    }

    public void start() {
        ConsoleCommand currentCommand = getNewCommand();
        while (!currentCommand.equals(CLOSE_CMD)) {
            if (state == LoginState.AUTHORIZED || currentCommand.equals(LOGIN_CMD)) {
                Request request = getRequest(currentCommand);
                request.generate();
                requestSender.send(request);
                if (hasCmdResponseForPrinting(currentCommand)) {
                    printResponseFromServer();
                }
                updateState(currentCommand);
                responseFromServer.delete(0, responseFromServer.length());
            }
            else {
                System.out.println("You should login before sending requests to server");
            }

            currentCommand = getNewCommand();
        }
        network.close();
    }

    private ConsoleCommand getNewCommand() {
        System.out.println("Enter your command");
        return ConsoleCommand.getConsoleCommand(IN.next());
    }

    private void updateState(ConsoleCommand cmd) {
        if (cmd.equals(LOGIN_CMD)) {
            state = getLoginStateFromResponse();
        }
    }

    private LoginState getLoginStateFromResponse() {
        return LoginState.getFromString(responseFromServer.toString().split(":")[0]);
    }

    private boolean hasCmdResponseForPrinting(ConsoleCommand cmd) {
        return commandsWithTextResponse.contains(cmd);
    }

    private void printResponseFromServer() {
        while (responseFromServer.isEmpty()) {
            Thread.onSpinWait();
        }
        System.out.println("Response is: " + responseFromServer);
    }

    private static Request getRequest(ConsoleCommand currentCommand) {
        Request request = commandToRequestMap.get(currentCommand);
        if (request == null) {
            throw new RuntimeException();
        }
        return request;
    }
}
