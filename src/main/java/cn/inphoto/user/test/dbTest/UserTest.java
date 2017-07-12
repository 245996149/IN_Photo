package cn.inphoto.user.test.dbTest;

import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.user.UsersEntity;
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
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserName("ming2");
        usersEntity.setPassword("123456");
        System.out.println(UtilDao.save(usersEntity));
    }

}
