package cn.inphoto.test;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.dbentity.page.AdminPage;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.dbentity.user.UserCategory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.inphoto.task.MediaTask.separateMediaByCategoryId;

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
    UserCategoryDao userCategoryDao = ctx.getBean(UserCategoryDao.class);

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

        for (AdminInfo a : adminInfos
                ) {
            System.out.println(a);
        }

    }

    @Test
    public void h() {

        AdminPage adminPage = new AdminPage();

//        adminPage.setPhone("18817774173");
        adminPage.setPageSize(2);

        System.out.println(adminPage.toString());

        List<AdminInfo> adminInfos = adminDao.findByPage(adminPage);

        for (AdminInfo a : adminInfos
                ) {
            System.out.println(a.toString());
        }

        System.out.println(adminInfos.size());
        System.out.println(adminDao.countByPage(adminPage));
    }

    @Test
    public void hi() {

        List<MediaData> mediaDataList = mediaDataDao.findByUser_idAndState(
                2L, MediaData.MEDIA_STATE_NORMAL);

        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndState(
                2L, UserCategory.USER_CATEGORY_STATE_NORMAL);

        List<MediaData> updateMediaDataList = new ArrayList<>();

        int num = mediaDataList.size();
        for (int i = 0; i < num; i++) {
            MediaData m = mediaDataList.get(i);
            boolean a = false;
            for (UserCategory uc : userCategoryList
                    ) {
                if (uc.getCategoryId() == m.getCategoryId()) {
                    a = true;
                }

            }
            if (!a) {
                updateMediaDataList.add(m);
                mediaDataList.remove(m);
                i--;
                num--;
            }
        }

        Map<Integer, List<MediaData>> map = separateMediaByCategoryId(mediaDataList);

        for (UserCategory uc : userCategoryList
                ) {
            if (map.get(uc.getCategoryId()).size() > uc.getMediaNumber()) {
                List<MediaData> m2 = mediaDataDao.findByUser_idAndCategory_idAndMedia_stateOrderByCreate_time(
                        uc.getUserId(), uc.getCategoryId(), MediaData.MEDIA_STATE_NORMAL,
                        map.get(uc.getCategoryId()).size() - uc.getMediaNumber());
                updateMediaDataList.addAll(m2);
            }

        }

        System.out.println("..........................................");

        for (MediaData m : updateMediaDataList
                ) {
            System.out.println(m.toString());
            m.setMediaState(MediaData.MEDIA_STATE_RECYCLE);
        }

        mediaDataDao.updateMediaList(updateMediaDataList);

    }

}
