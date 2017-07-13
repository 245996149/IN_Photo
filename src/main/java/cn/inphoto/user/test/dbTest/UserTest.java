package cn.inphoto.user.test.dbTest;

import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.user.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kaxia on 2017/6/12.
 */
public class UserTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    UtilDao UtilDao = ctx.getBean(UtilDao.class);

    @Test
    public void addUserTest() {
        User user = new User();
        user.setUserName("ming2");
        user.setPassword("123456");
        System.out.println(UtilDao.save(user));
    }

}
