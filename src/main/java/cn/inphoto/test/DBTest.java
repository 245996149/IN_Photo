package cn.inphoto.test;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.ClientDao;
import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-7-12.
 */
public class DBTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    AdminDao adminDao = ctx.getBean(AdminDao.class);
    ClientDao clientDao = ctx.getBean(ClientDao.class);
    UserDao userDao = ctx.getBean(UserDao.class);
    MediaDataDao mediaDataDao = ctx.getBean(MediaDataDao.class);

    @Test
    public void a() {
        List<ModuleInfo> a = adminDao.findModulesByAdmin(1);
        for (ModuleInfo m : a
                ) {
            System.out.println(m.toString());
        }
    }

    @Test
    public void b() {
        UserPage userPage = new UserPage();
//        userPage.setUserName("ming");
        userPage.setPhone("18817774173");
        List<User> a = userDao.findByPage(userPage);
        for (User m : a
                ) {
            System.out.println(m.toString());
        }
    }

    @Test
    public void c() {
        List<String> list = new ArrayList<>();
        list.add(MediaData.MEDIA_STATE_NORMAL);
        list.add(MediaData.MEDIA_STATE_WILL_DELETE);
//        int a = mediaDataDao.countByUser_idAndCategory_idAndMedia_state2(1L, 1, list);
//        System.out.println(a);
        
    }

}
