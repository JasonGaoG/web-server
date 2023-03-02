package com.sunlight.portal.socketserver.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.sunlight.portal.socketserver.vo.MessageVO;

import java.io.InputStream;

public class SslChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(10443);

        config.setKeyStorePassword("test1234");
        InputStream stream = SslChatLauncher.class.getResourceAsStream("/keystore.jks");
        config.setKeyStore(stream);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", MessageVO.class, new DataListener<MessageVO>() {
            @Override
            public void onData(SocketIOClient client, MessageVO data, AckRequest ackRequest) {

            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
