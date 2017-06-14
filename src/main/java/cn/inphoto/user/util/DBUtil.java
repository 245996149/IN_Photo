package cn.inphoto.user.util;

import cn.inphoto.user.dao.MediaCodeDao;
import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dao.UtilDao;
import cn.inphoto.user.dbentity.MediaCodeEntity;
import cn.inphoto.user.dbentity.ShareDataEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kaxia on 2017/6/12.
 */
public class DBUtil {

    /**
     * 判断媒体验证码是否存在，存在则更新，不存在则创建
     *
     * @param mediaCodeEntity 媒体验证码
     * @return 更新、创建是否成功
     */
    public static boolean judgeMediaCode(MediaCodeEntity mediaCodeEntity) {

        // 读取配置
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
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

        // 读取配置
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
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

}
