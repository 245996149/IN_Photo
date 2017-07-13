package cn.inphoto.user.test.dbTest;

import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dbentity.user.UserCategory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by kaxia on 2017/6/23.
 */
public class CategoryTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserCategoryDao userCategoryDao = ctx.getBean(UserCategoryDao.class);

    @Test
    public void autoCleanOverUserCategory() {

        Date date = new Date();

        List<UserCategory> userCategoryEntities = userCategoryDao.findByOverTimeByNormal(date, UserCategory.USER_CATEGORY_STATE_NORMAL);

        for (UserCategory u: userCategoryEntities
             ) {
            System.out.println(u.toString());
        }

    }

}
