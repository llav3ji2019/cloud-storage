package com.llav3ji2019.client_server.request;

import structures.RequestData;

public interface Request {
    void generate();

    RequestData getRequestData();
}
