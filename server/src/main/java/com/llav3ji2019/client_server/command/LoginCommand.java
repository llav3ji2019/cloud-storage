package com.llav3ji2019.client_server.command;

import lombok.Getter;
import structures.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCommand extends Command {
    private static final String LOGIN_SUCCESS = "Success: You are login to your account";
    private static final String WRONG_PASSWORD = "Error: Your password is incorrect";
    private static final String WRONG_USERNAME = "Error: There is no account with this login";
    private static final String ATTEMPTS_MESSAGE = "login attempts left";
    private static final String REPEATED_LOGIN_MESSAGE = "Success: Can't to log in twice";
    private static final String SERVER_IS_BLOCKED = "Blocked: Server is blocked due to you have no attempts";
    private static final Map<String, String> users_accounts = new HashMap<>();

    private static int loginAttempts = 3;

    private final User newUser;

    @Getter
    private User userOnSever;

    static {
        users_accounts.putAll(Map.of(
                "Oleg123", "olegoleg",
                "admin", "adminadmin"
        ));
    }

    public LoginCommand(User currentUser, List<String> params) {
        this.userOnSever = currentUser;

        this.newUser = User.builder()
                .password(params.get(1))
                .username(params.get(0))
                .build();
    }

    @Override
    public void execute() {
        if (hasNoAttempts()) {
            responseMsg.append(SERVER_IS_BLOCKED);
            return;
        }

        if (newUser.equals(userOnSever)) {
            responseMsg.append(REPEATED_LOGIN_MESSAGE);
            return;
        }

        boolean isUsernameIncorrect = isNotRightUsername(newUser.username());
        boolean isPasswordIncorrect = isNotPasswordCorrect(newUser);
        if (isUsernameIncorrect || isPasswordIncorrect) {
            loginAttempts--;
            String errorMsg;
            if (isUsernameIncorrect) {
                errorMsg = getWrongUsernameMsg(loginAttempts);
            } else {
                errorMsg = getWrongPasswordMsg(loginAttempts);
            }
            responseMsg.append(errorMsg);

            if (hasNoAttempts()) {
                responseMsg.append(SERVER_IS_BLOCKED);
            }
            return;
        }

        responseMsg.append(LOGIN_SUCCESS);
        userOnSever = newUser;
        loginAttempts = 3;
    }

    private boolean hasNoAttempts() {
        return loginAttempts <= 0;
    }

    private boolean isRightUsername(String username) {
        return users_accounts.containsKey(username);
    }

    private boolean isNotRightUsername(String username) {
        return !isRightUsername(username);
    }

    private boolean isPasswordCorrect(User user) {
        String accountPassword = users_accounts.get(user.username());
        return accountPassword != null && accountPassword.equals(user.password());
    }

    private boolean isNotPasswordCorrect(User user) {
        return !isPasswordCorrect(user);
    }

    private String getWrongUsernameMsg(int loginAttemptsLeft) {
        return WRONG_USERNAME + " " + loginAttemptsLeft + " " + ATTEMPTS_MESSAGE + " ";
    }

    private String getWrongPasswordMsg(int loginAttemptsLeft) {
        return WRONG_PASSWORD + " " + loginAttemptsLeft + " " + ATTEMPTS_MESSAGE + " ";
    }
}
