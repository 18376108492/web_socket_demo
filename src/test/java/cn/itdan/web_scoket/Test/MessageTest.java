package cn.itdan.web_scoket.Test;

import cn.itdan.web_scoket.dao.MessageDAO;
import cn.itdan.web_scoket.pojo.Message;
import cn.itdan.web_scoket.pojo.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageTest {

    @Autowired
    private MessageDAO messageDAO;

    @Test
    public void testSave() throws Exception {
        Message message = Message.builder()
                .id(ObjectId.get())
                .msg("你好")
                .sendDate(new Date())
                .status(1)
                .from(new User(1001L, "zhangsan"))
                .to(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);

        message = Message.builder()
                .id(ObjectId.get())
                .msg("你也好")
                .sendDate(new Date())
                .status(1)
                .to(new User(1001L, "zhangsan"))
                .from(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);

        message = Message.builder()
                .id(ObjectId.get())
                .msg("我在学习开发IM")
                .sendDate(new Date())
                .status(1)
                .from(new User(1001L, "zhangsan"))
                .to(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);

        message = Message.builder()
                .id(ObjectId.get())
                .msg("那很好啊！")
                .sendDate(new Date())
                .status(1)
                .to(new User(1001L, "zhangsan"))
                .from(new User(1002L, "lisi")).build();
        this.messageDAO.saveMessage(message);
        System.out.println("ok");
    }


    @Test
    public void testQueryList() throws Exception{
      List<Message> messageList= this.messageDAO.findListByFromAndTo(1001L,1002L,1,10);
       for (Message message:messageList){
           System.out.println(message.toString());
       }
    }

    @Test
    public void testFindById() throws Exception{
      Message message=  messageDAO.findMessageById("5dcf81a54d026815d00b6921");
        System.out.println(message);
    }


}
