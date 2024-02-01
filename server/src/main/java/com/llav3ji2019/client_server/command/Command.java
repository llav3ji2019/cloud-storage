package com.llav3ji2019.client_server.command;

import lombok.Getter;

@Getter
public abstract class Command {
    protected final StringBuilder responseMsg = new StringBuilder();

    public abstract void execute();
}
