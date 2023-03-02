package com.sunlight.portal.socketserver.listeners;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;

/**
 * socketio 断链事件
 */
@Slf4j
public class ClientDisconnectListener implements DisconnectListener {

    @Override
    public void onDisconnect(SocketIOClient socketIOClient) {
        log.info("chat com.binance.client disconnect ");
    }
}
