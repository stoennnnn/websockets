package com.zql.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 26725 on 2019/1/7.
 */

@Controller
@Slf4j
public  class Handler implements WebSocketHandler {
    /**
     * 新增websocket连接
     * @param webSocketSession
     * @throws Exception
     */
    private  static  final Map<String,WebSocketSession> clients = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        if (Optional.ofNullable(webSocketSession).isPresent()&&
                Optional.ofNullable(webSocketSession.getHandshakeHeaders()).isPresent()){
            List<String> realIps = webSocketSession.getHandshakeHeaders().get("x-real-ip");
            if (Optional.ofNullable(realIps).isPresent()&&!realIps.isEmpty()){
                String realIp = realIps.get(0);
                log.info("成功建立连接，连接的IP地址为：{}",realIp);
                log.info("当前在线人数为：{}",clients.size());
                clients.putIfAbsent(realIp,webSocketSession);
            }

        }
    }
    /**
     * 发送信息给指定ip
     * @param clientIp
     * @param message
     * @return
     */
    public void sendMessage(String clientIp, TextMessage message){


    }
    @Override
    public void handleMessage(WebSocketSession webSocketSession,
                              WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
