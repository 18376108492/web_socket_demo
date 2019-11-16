package cn.itdan.web_scoket.webscoker;

import cn.itdan.web_scoket.dao.MessageDAO;
import cn.itdan.web_scoket.dao.UserData;
import cn.itdan.web_scoket.pojo.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageHandler extends TextWebSocketHandler {

    @Autowired
    private MessageDAO messageDAO;

    private  static final Map<Long,WebSocketSession> SOCKET_SESSION_MAP=new HashMap<>();

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //获取当前用户的session,并将其放置到session中
        Long uid=(Long)session.getAttributes().get("uid");
        SOCKET_SESSION_MAP.put(uid,session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage
            textMessage) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");

        JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
        Long toId = jsonNode.get("toId").asLong();//获取接受方的ID
        String msg = jsonNode.get("msg").asText();//获取要发送的消息

        Message message = Message.builder()
                .from(UserData.USER_MAP.get(uid))
                .to(UserData.USER_MAP.get(toId))
                .msg(msg)
                .build();

        // 将消息保存到MongoDB
        message = this.messageDAO.saveMessage(message);

        // 判断to用户是否在线
        WebSocketSession toSession = SOCKET_SESSION_MAP.get(toId);
        if (toSession != null && toSession.isOpen()) {
            //TODO 具体格式需要和前端对接
            toSession.sendMessage(new
                    TextMessage(MAPPER.writeValueAsString(message)));
            // 更新消息状态为已读
            this.messageDAO.updateMessageState(message.getId(), 2);
        }

    }

}
