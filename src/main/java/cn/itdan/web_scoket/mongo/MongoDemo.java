package cn.itdan.web_scoket.mongo;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.function.Consumer;


public class MongoDemo {

    public static void main(String [] args){
     //获取mongo链接
        MongoDatabase mongoDatabase= mongoClients.getDatabase("admin");
        MongoCollection<Document> mongoCollection= mongoDatabase.getCollection("'user");
        mongoCollection.find().forEach((Consumer<? super Document>) document ->{
            System.out.println(document.toJson());
        });
        //关闭链接
        mongoClients.close();
    }

}
