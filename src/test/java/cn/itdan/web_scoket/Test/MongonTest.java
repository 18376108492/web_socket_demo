package cn.itdan.web_scoket.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;

public class MongonTest {

    private static MongoCollection<Document> mongoCollection;

    @Before
    public void init() {
        //获取mongo链接
        com.mongodb.client.MongoClient mongoClients = MongoClients.create("mongodb://admin:admin@123.57.128.124:27017/admin");
        MongoDatabase mongoDatabase = mongoClients.getDatabase("admin");
        mongoCollection = mongoDatabase.getCollection("admin");
    }

    @Test
    public void testDemo01() throws Exception {
          FindIterable<?> fi = mongoCollection.find();
          //遍历结果  
            for (Object o : fi) {
            System.out.println("查询的数据为:"+o);
        }
    }


    @Test
    public void testInsertDemo() throws Exception{
      //插入数据
        Document document=new Document();
      document.append("id","2000");
      document.append("age","18");
      document.append("sex","man");
      mongoCollection.insertOne(document);

      //查询上条数据
        FindIterable<?> iterable= mongoCollection.find(Filters.eq("id","2000"));
         for (Object o:iterable){
             System.out.println("查询的数据为:"+o);
         }
    }


    @Test
    public void testUpdateDemo() throws Exception{
     mongoCollection.updateOne(Filters.eq("id","2000"),Updates.set("name","wuyifan"));

        //查询上条数据
        FindIterable<?> iterable= mongoCollection.find(Filters.eq("id","2000"));
        for (Object o:iterable){
            System.out.println("查询的数据为:"+o);
        }
    }

    @Test
    public void testFind() throws Exception {
        mongoCollection.find(Filters.and(
                Filters.lt("age", "50")
        )).forEach((Consumer<? super Document>) document -> {
            System.out.println("json" + document.toJson());
        });
    }

    @Test
    public void testDeleteDemo() throws Exception{
    DeleteResult deleteResult= mongoCollection.deleteMany(Filters.eq("id","2000"));
        System.out.println("删除结果:"+deleteResult);
    }



    @Test
    public void testDemo02() throws Exception {
        MongoClient client = null;
        try {
            // 用户名 数据库 密码  
            MongoCredential credential = MongoCredential.createCredential("admin", "admin", "admin".toCharArray());
            //IP port  
            ServerAddress addr = new ServerAddress("123.57.128.124", 27017);
            client = new MongoClient(addr, Arrays.asList(credential));
            //得到数据库  
            MongoDatabase mdb = client.getDatabase("admin");
            //创建一个集合
            mdb.createCollection("aaa");
            //得到Table  
            MongoCollection<?> table = mdb.getCollection("admin");
            //查询所有  
            FindIterable<?> fi = table.find();
//遍历结果  
            for (Object o : fi) {
                System.out.println(o);
            }
            //删除一个数据
            table.deleteOne(new Document("username", "user1"));
            //创建一个索引
            table.createIndex(new Document("username", 1));
            //table.insertOne(new Document("name","张三").append("age", 20));
            System.out.println(table.listIndexes());
            for (Object o : table.listIndexes()) {
                System.out.println(o);
            }
            System.out.println(123);
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }

    }

}
