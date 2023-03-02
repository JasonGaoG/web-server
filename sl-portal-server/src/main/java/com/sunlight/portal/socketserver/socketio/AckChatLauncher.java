package com.sunlight.portal.socketserver.socketio;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.sunlight.portal.socketserver.vo.MessageVO;

public class AckChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("ackevent1", MessageVO.class, new DataListener<MessageVO>() {
            @Override
            public void onData(final SocketIOClient client, MessageVO data, final AckRequest ackRequest) {

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to com.binance.client
                    ackRequest.sendAckData("com.binance.client message was delivered to server!", "yeah!");
                }
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
