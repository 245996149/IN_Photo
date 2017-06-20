package cn.inphoto.user.util;

import cn.inphoto.user.dao.*;
import cn.inphoto.user.dbentity.CategoryEntity;
import cn.inphoto.user.dbentity.MediaCodeEntity;
import cn.inphoto.user.dbentity.ShareDataEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kaxia on 2017/6/12.
 */
public class DBUtil {

    // 读取配置
    static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    /**
     * 判断媒体验证码是否存在，存在则更新，不存在则创建
     *
     * @param mediaCodeEntity 媒体验证码
     * @return 更新、创建是否成功
     */
    public static boolean judgeMediaCode(MediaCodeEntity mediaCodeEntity) {

        MediaCodeDao mediaCodeDao = ctx.getBean(MediaCodeDao.class);
        UtilDao utilDao = ctx.getBean(UtilDao.class);

        // 查询媒体验证码对象
        MediaCodeEntity mediaCode = mediaCodeDao.findByUser_idAndCategory_idAndMedia_code(
                mediaCodeEntity.getUserId(), mediaCodeEntity.getCategoryId(), mediaCodeEntity.getMediaCode());

        // 判断媒体验证码对象是否有效
        if (mediaCode == null) {
            // 无效，新增一个媒体验证码对象
            return utilDao.save(mediaCodeEntity);
        } else {
            // 有效，给媒体验证码对象赋予新的值，并更新数据库
            mediaCode.setMediaId(mediaCodeEntity.getMediaId());
            return utilDao.update(mediaCode);
        }

    }

    /**
     * 查询今日数据并写入session
     *
     * @param session session
     * @param user    用户对象
     */
    public static void selectTodayData(HttpSession session, UsersEntity user) {

        ShareDataDao shareDataDao = ctx.getBean(ShareDataDao.class);

        // 创建日历对象
        Calendar calendar = Calendar.getInstance();

        // 给日历对象设置时间
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date begin = calendar.getTime();

        calendar.add(Calendar.DATE, 1);

        Date end = calendar.getTime();

        // 查询数据
        int click_num = shareDataDao.countByTimeTotal(user.getUserId(), begin, end, ShareDataEntity.SHARE_TYPE_WEB_CLICK);
        int chats_num = shareDataDao.countByTimeTotal(user.getUserId(), begin, end, ShareDataEntity.SHARE_TYPE_WECHAT_SHARE_CHATS);
        int moments_num = shareDataDao.countByTimeTotal(user.getUserId(), begin, end, ShareDataEntity.SHARE_TYPE_WECHAT_SHARE_MOMENTS);

        session.setAttribute("click_num", click_num);
        session.setAttribute("chats_num", chats_num);
        session.setAttribute("moments_num", moments_num);

    }

    /**
     * 判断application中的套餐系统信息是否有效，有效直接返回，无效查询后添加到application中，并返回
     *
     * @return 套餐系统信息
     */
    public static List<CategoryEntity> judgeCategory(HttpServletRequest request) {

        // 获取application对象
        ServletContext application = request.getSession().getServletContext();

        // 从application对象中获取套餐系统信息
        List<CategoryEntity> categoryList = (List<CategoryEntity>) application.getAttribute("category");

        // 判断套餐系统信息是否有效，无效获取新的套餐系统信息
        if (categoryList == null) {

            // 查询所有套餐系统信息
            CategoryDao categoryDao = ctx.getBean(CategoryDao.class);

            // 将套餐系统信息赋给套餐系统信息队列对象
            categoryList = categoryDao.findAll();

            application.setAttribute("category",categoryList);

        }

        return categoryList;

    }

}
