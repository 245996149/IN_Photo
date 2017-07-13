package cn.inphoto.test;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dbentity.admin.ModuleInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by root on 17-7-12.
 */
public class DBTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    AdminDao adminDao= ctx.getBean(AdminDao.class);

    @Test
    public void a(){
        List<ModuleInfo> a = adminDao.findModulesByAdmin(1);
        for (ModuleInfo m:a
             ) {
            System.out.println(m.toString());
        }
    }

}
