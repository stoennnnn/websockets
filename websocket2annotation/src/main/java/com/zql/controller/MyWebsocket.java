package com.zql.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 26725 on 2019/1/7.
 */
@ServerEndpoint(value="/websocket2")
@Component
@Slf4j
public class MyWebsocket {
    //当前连接数
    private static int onlineCount = 0;
    //存放每个客户端对应的websocket对象
    private static CopyOnWriteArrayList<MyWebsocket> websockets = new CopyOnWriteArrayList<>();
    //与客户端的会话
    private Session session;


    /**
     * 连接成功调用的
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websockets.add(this);
        onlineCount++;
        log.info("有新的连接加入，当前连接人数为：{}", onlineCount);
    }
}