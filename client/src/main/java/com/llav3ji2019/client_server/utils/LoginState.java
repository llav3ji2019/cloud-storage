package com.llav3ji2019.client_server.utils;

import java.util.Map;

public enum LoginState {
    AUTHORIZED,
    UNIDENTIFIED,
    BLOCKED;

    private static final Map<String, LoginState> LOGIN_STATES_MAP = Map.of(
            "success", AUTHORIZED,
            "error", UNIDENTIFIED,
            "blocked", BLOCKED);

    public static LoginState getFromString(String str) {
        return LOGIN_STATES_MAP.getOrDefault(str.toLowerCase(), UNIDENTIFIED);
    }
}
