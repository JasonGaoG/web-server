package com.sunlight.portal.socketserver.listeners;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.sunlight.portal.socketserver.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;

/**
 */
@Slf4j
public class MessageListener implements DataListener<MessageVO> {

    @Override
    public void onData(SocketIOClient socketIOClient, MessageVO data, AckRequest ackRequest) {
        log.info("chat MessageListener:" + JSON.toJSONString(data));
        if (data == null) {
        }
    }
}
