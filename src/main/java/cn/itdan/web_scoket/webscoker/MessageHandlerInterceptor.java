package cn.itdan.web_scoket.webscoker;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class MessageHandlerInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
       //使用拦截器在握手前获取用户的UID
        //获取通讯路径
        //ws://127.0.0.1:18081/ws/{uid}
        String path = serverHttpRequest.getURI().getPath();
        String[] ss = StringUtils.split(path, '/');
        if(ss.length != 2){
            return false;
        }
        if(!StringUtils.isNumeric(ss[1])){ //是否为数字
            return false;
        }
        map.put("uid", Long.valueOf(ss[1]));
        System.out.println("开始握手。。。。。。。");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手成功啦。。。。。。");
    }
}
