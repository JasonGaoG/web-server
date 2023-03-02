package com.sunlight.portal.socketserver.constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketContext {

    private static final Object clientsLock = new Object();

    private SocketContext() {

    }

    private static SocketContext instance = new SocketContext();

    public static SocketContext getInstatnce() {
        return instance;
    }
}
