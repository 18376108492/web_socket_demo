package cn.itdan.web_scoket.Test;

import cn.itdan.web_scoket.pojo.Address;
import cn.itdan.web_scoket.pojo.Person;
import cn.itdan.web_scoket.dao.PersonDAO;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot_mongo_demo {

    @Autowired
    private PersonDAO personDAO;

    @Test
    public void testInsert() throws Exception {
        Person person = new Person(ObjectId.get(), "dan", 200, new Address("长生路", "生长市", "666666"));
        personDAO.update(person);
    }

    @Test
    public void testQueryDemo01() throws Exception {
        List<Person> list = personDAO.queryPersonListByName("dan");
        for (Person person : list) {
            System.out.println(person.toString());
        }
    }

    @Test
    public void testQueryDemo02() throws Exception {
        List<Person> list = personDAO.queryPagePersonList(1, 3);
        for (Person person : list) {
            System.out.println(person.toString());
        }
    }

    @Test
    public void testUpdate() throws Exception {
//        Person person = new Person(ObjectId.get(), "王五", 20000, new Address("长生路", "生长市", "666666"));
//        UpdateResult updateResult = personDAO.update(person);
//        System.out.println(updateResult);

        Person person = new Person();
        person.setId(new ObjectId("5dceb98a4d02680870489434"));
        person.setAge(2355);
        UpdateResult update = this.personDAO.update(person);
        System.out.println(update);
    }

    @Test
    public void testDelete() throws Exception{
       DeleteResult deleteResult= personDAO.deleteById("5dceb98a4d02680870489434");
        System.out.println(deleteResult);
    }
}
