package cn.inphoto.user.test.dbTest;

import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.UsersEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kaxia on 2017/6/12.
 */
public class UserTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDao userDao = ctx.getBean(UserDao.class);

    @Test
    public void addUserTest() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserName("ming2");
        usersEntity.setPassword("123456");
        System.out.println(userDao.addUser(usersEntity));
    }

}
