package cn.inphoto.util;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.util.dbUtil.ClientUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kaxia on 2017/6/12.
 */
@Component
public class DBUtil {

    @Resource
    private ShareDataDao shareDataDao;

    @Resource
    private ShareClickDataDao shareClickDataDao;

    @Resource
    private MediaCodeDao mediaCodeDao;

    @Resource
    private UtilDao utilDao;

    @Resource
    private CategoryDao categoryDao;

    private static DBUtil dbUtil;

    public static void setDbUtil(DBUtil dbUtil) {
        DBUtil.dbUtil = dbUtil;
    }

    @PostConstruct
    public void init() {
        dbUtil = this;
        dbUtil.shareDataDao = this.shareDataDao;
        dbUtil.mediaCodeDao = this.mediaCodeDao;
        dbUtil.utilDao = this.utilDao;
        dbUtil.categoryDao = this.categoryDao;
    }

    /**
     * 判断媒体验证码是否存在，存在则更新，不存在则创建
     *
     * @param mediaCodeEntity 媒体验证码
     * @return 更新、创建是否成功
     */
    public static boolean judgeMediaCode(MediaCode mediaCodeEntity) {

        // 查询媒体验证码对象
        MediaCode mediaCode = dbUtil.mediaCodeDao.findByUser_idAndCategory_idAndMedia_code(
                mediaCodeEntity.getUserId(), mediaCodeEntity.getCategoryId(), mediaCodeEntity.getMediaCode());

        // 判断媒体验证码对象是否有效
        if (mediaCode == null) {
            // 无效，新增一个媒体验证码对象
            return dbUtil.utilDao.save(mediaCodeEntity);
        } else {
            // 有效，给媒体验证码对象赋予新的值，并更新数据库
            mediaCode.setMediaId(mediaCodeEntity.getMediaId());
            return dbUtil.utilDao.update(mediaCode);
        }

    }

    /**
     * 查询今日数据并写入session
     *
     * @param session session
     * @param user    用户对象
     */
    public static void selectTodayData(HttpSession session, User user) {


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
        int click_num = dbUtil.shareClickDataDao.countByTimeTotal(user.getUserId(), begin, end);
        int chats_num = dbUtil.shareDataDao.countByTimeTotal(user.getUserId(), begin, end, ShareData.SHARE_TYPE_WECHAT_SHARE_CHATS);
        int moments_num = dbUtil.shareDataDao.countByTimeTotal(user.getUserId(), begin, end, ShareData.SHARE_TYPE_WECHAT_SHARE_MOMENTS);

        session.setAttribute("click_num", click_num);
        session.setAttribute("chats_num", chats_num);
        session.setAttribute("moments_num", moments_num);

    }

    /**
     * 判断application中的套餐系统信息是否有效，有效直接返回，无效查询后添加到application中，并返回
     *
     * @return 套餐系统信息
     */
    public static List<Category> judgeCategory(HttpServletRequest request) {

        // 获取application对象
        ServletContext application = request.getSession().getServletContext();

        // 从application对象中获取套餐系统信息
        List<Category> categoryList = (List<Category>) application.getAttribute("category");

        // 判断套餐系统信息是否有效，无效获取新的套餐系统信息
        if (categoryList == null) {

            // 将套餐系统信息赋给套餐系统信息队列对象
            categoryList = dbUtil.categoryDao.findAll();

            application.setAttribute("category", categoryList);

        }

        return categoryList;

    }

    /**
     * 将媒体数据移入回收站
     *
     * @param mediaData
     */
    public static void changeMediaDataToRecycle(MediaData mediaData) {

        Date date = new Date();

        mediaData.setMediaState(MediaData.MediaState.Recycle);
        mediaData.setDeleteTime(new Timestamp((date.getTime())));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30);

        mediaData.setOverTime(new Timestamp(calendar.getTimeInMillis()));
    }



}
