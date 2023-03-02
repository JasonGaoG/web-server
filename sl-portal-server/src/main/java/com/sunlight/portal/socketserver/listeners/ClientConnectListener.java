package com.sunlight.portal.socketserver.listeners;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @package com.components.socketserver.listeners
 * @description socket 连接
 * @date 2019/4/3
 */
@Slf4j
public class ClientConnectListener implements ConnectListener {
    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        log.info(socketIOClient.getSessionId().toString() + "connected to server!");
    }
}
