package com.sunlight.portal.socketserver.socketio;


import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.sunlight.portal.socketserver.vo.MessageVO;

public class NamespaceChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        final SocketIONamespace chat1namespace = server.addNamespace("/chat1");
        chat1namespace.addEventListener("message", MessageVO.class, new DataListener<MessageVO>() {
            @Override
            public void onData(SocketIOClient client, MessageVO data, AckRequest ackRequest) {
                // broadcast messages to all clients
                chat1namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });

        final SocketIONamespace chat2namespace = server.addNamespace("/chat2");
        chat2namespace.addEventListener("message", MessageVO.class, new DataListener<MessageVO>() {
            @Override
            public void onData(SocketIOClient client, MessageVO data, AckRequest ackRequest) {
                // broadcast messages to all clients
                chat2namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
