package com.zql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by 26725 on 2019/1/7.
 */
public class Config implements WebSocketConfigurer {
    @Autowired
    private Interceptor interceptor;
    @Autowired
    private Handler handler;

    //    @Autowired
//    public Congfig(Interceptor interceptor,Handler handler){
//        this.handler=handler;
//        this.interceptor=interceptor;
//    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler,"/websocket")
                .setAllowedOrigins("*")
                .addInterceptors(interceptor);
    }
}
