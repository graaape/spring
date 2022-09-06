import com.kuang.mapper.UserMapper;
import com.kuang.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class MyTest {
    @Test
    public void test() throws IOException {
//        String resources="mybatis_config.xml";
//        InputStream in = Resources.getResourceAsStream(resources);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = mapper.selectUser();
//        for (User user : users) {
//            System.out.println(user);
//        }
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        List<User> users = userMapper.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper2", UserMapper.class);
        List<User> users = userMapper.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
