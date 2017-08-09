package cn.inphoto.test;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.admin.AdminInfo;
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
    RoleDao roleDao = ctx.getBean(RoleDao.class);


    @Test
    public void b() {
        User user = userDao.findByUser_id(1L);
        System.out.println(user.toString());
    }

    @Test
    public void c() {
        List<String> list = new ArrayList<>();
        list.add(MediaData.MEDIA_STATE_NORMAL);
        list.add(MediaData.MEDIA_STATE_WILL_DELETE);
//        int a = mediaDataDao.countByUser_idAndCategory_idAndMedia_state2(1L, 1, list);
//        System.out.println(a);

    }

    @Test
    public void d() {
        AdminInfo adminInfo = adminDao.findByAdmin_name("ming123");

        System.out.println(adminInfo.toString());

    }

    @Test
    public void e() {
        List<RoleInfo> roleInfo = roleDao.findAllRole();

        for (RoleInfo r : roleInfo
                ) {

            System.out.println(r.toString());
        }

    }

    @Test
    public void f() {

        System.out.println(roleDao.deleteRole(2));

    }

    @Test
    public void g() {

        List<AdminInfo> adminInfos = roleDao.findAdminByRole_id(1);

        System.out.println(adminInfos.size());

        for (AdminInfo a: adminInfos
             ) {
            System.out.println(a);
        }

    }

}
