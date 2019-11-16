package cn.itdan.web_scoket.spring;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocket  extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("获取到的消息："+message.getPayload());
        session.sendMessage(new TextMessage("消息已收到"));
        if(message.getPayload().equals("10")){
            for (int i = 0; i <10 ; i++) {
                session.sendMessage(new TextMessage("消息>" + i));
                try {
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
         Integer uid=(Integer) session.getAttributes().get("uid");
         session.sendMessage(new TextMessage(uid+",欢迎你"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("断开链接");
    }
}
