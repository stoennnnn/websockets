package com.zql.controller;

/**
 * Created by 26725 on 2019/1/7.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * websocket拦截器功能：
 * 截取客户端建立的websocket连接时发送的url地址，并通过对该字符串的特殊标志的截取，获取
 * 客户端发送得唯一标志（自定义），并以键值对存放信息
 * Created by 26725 on 2019/1/4.
 */
@Component
@Slf4j
public class Interceptor implements HandshakeInterceptor {
    /**
     * 握手之前执行该方法，继续握手返回true，中断握手返回false。
     * 通过map设置websocketsession属性
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServerHttpRequest){
            String requestIp = serverHttpRequest.getRemoteAddress().getAddress().toString();
            log.info("请求的ip地址为:{},来源为：{}",requestIp,serverHttpRequest.getURI());
            map.put("USERIP",requestIp);
        }
        return true;
    }

    /**
     * 完成握手之后执行该方法，
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest,
                               ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler,
                               @Nullable Exception e) {
        log.info("进来websocket之后的afterHandShake拦截器！");

    }
}
