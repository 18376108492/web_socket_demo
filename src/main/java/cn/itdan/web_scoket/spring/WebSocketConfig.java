package cn.itdan.web_scoket.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyWebSocket myWebSocket;

    @Autowired
    private MyHandleShakeIntercepot myHandleShakeIntercepot;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //setAllowedOrigins设置允许跨域
        //addInterceptors添加拦截器
         webSocketHandlerRegistry.addHandler(this.myWebSocket,"/ws")
                 .setAllowedOrigins("*")
                 .addInterceptors(this.myHandleShakeIntercepot);
    }



}

