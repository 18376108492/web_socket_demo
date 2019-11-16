package cn.itdan.web_scoket.dao.impl;

import cn.itdan.web_scoket.dao.MessageDAO;
import cn.itdan.web_scoket.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MessageDAOImpl implements MessageDAO {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer rows) {
       //点到点，聊天记录查询

        //用户A发给用户B的条件
        Criteria criteriaFrom=new Criteria().andOperator(
                Criteria.where("from.id").is(fromId),
                Criteria.where("to.id").is(toId)
        );

        //用户B发给用户A的条件
        Criteria criteriaTo=new Criteria().andOperator(
                Criteria.where("from.id").is(toId),
                Criteria.where("to.id").is(fromId)
        );

        Criteria criteria=new Criteria().orOperator(criteriaFrom,criteriaTo);
        //设置分页条件
        PageRequest pageRequest=PageRequest.of(page-1,rows,
                Sort.by(Sort.Direction.ASC,"sendDate"));

        //创建查询条件s
        Query query=Query.query(criteria).with(pageRequest);
        List<Message> messageList=  mongoTemplate.find(query,Message.class);
        return messageList;
    }

    @Override
    public Message findMessageById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id), Message.class);
    }

    @Override
    public UpdateResult updateMessageState(ObjectId id, Integer status) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("status", status);
        if (status.intValue() == 1) {
            update.set("send_date", new Date());
        } else if (status.intValue() == 2) {
            update.set("read_date", new Date());
        }
        return this.mongoTemplate.updateFirst(query, update, Message.class);
    }

    @Override
    public Message saveMessage(Message message) {
        //新增信息
        //添加创建时间
        message.setSendDate(new Date());
        message.setStatus(1);
        message.setId(ObjectId.get());
        return  mongoTemplate.save(message);

    }

    @Override
    public DeleteResult deleteMessage(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.remove(query, Message.class);
    }
}
