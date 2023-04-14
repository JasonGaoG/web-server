package com.sunlight.portal.socketserver.server;

import com.corundumstudio.socketio.*;
import com.sunlight.portal.socketserver.constant.CometEvent;
import com.sunlight.portal.socketserver.listeners.ClientConnectListener;
import com.sunlight.portal.socketserver.listeners.ClientDisconnectListener;
import com.sunlight.portal.socketserver.listeners.MessageListener;
import com.sunlight.portal.socketserver.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class Server {

    private SocketIOServer server;

    private int port = 3099;

    private String address = "0.0.0.0";

    private static Server instance = new Server();

    public static Server getInstance() {
        return instance;
    }

    private Server() {
    }

    public void startServer() {
        Configuration config = new Configuration();
        config.setHostname(address);
        config.setPort(port);
        config.setOrigin("*");
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        log.info("set socket config");

        server = new SocketIOServer(config);
        server.addNamespace("/socket.io");

        // 连接监听
        server.addConnectListener(new ClientConnectListener());

        // 断链监听
        server.addDisconnectListener(new ClientDisconnectListener());

        // 消息事件
        server.addEventListener(CometEvent.SYSTEM_EVENT, MessageVO.class, new MessageListener());

        // 启动即时通信服务器
        server.start();
        log.info("socketIo started success!");
    }

    public void stoptServer() {
        if (server != null) {
            server.stop();
            log.info("socketIo shutdown!");
        }
    }


    public void broadCastEvent(String event, Object data) {
//        Collection<SocketIOClient> clients = server.getAllClients();
//        for (SocketIOClient client : clients) {
//            System.out.println("clients:" + clients.size());
//            client.sendEvent(event, data);
//        }
        Collection<SocketIOClient> clients2 = server.getNamespace("/socket.io").getAllClients();
        for (SocketIOClient client : clients2) {
            client.sendEvent(event, data);
        }
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

}
