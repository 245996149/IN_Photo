package cn.inphoto.user.task;

import cn.inphoto.user.dao.MediaDataDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dao.UtilDao;
import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.UserCategoryEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import cn.inphoto.user.log.TaskLog;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static cn.inphoto.user.util.DBUtil.changeMediaDataToRecycle;

/**
 * 自动化任务
 * Created by root on 17-5-23.
 */
@Component
public class TaskJob {

    private static Logger logger = Logger.getLogger(TaskJob.class);

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    UserDao userDao;

    @Resource
    MediaDataDao mediaDataDao;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void cleanTemp() {
        System.out.println("任务运行中。。。。。。。" + System.currentTimeMillis());
    }

    /**
     * 每5分钟执行一次任务
     */
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void changeUserCategoryTable() {

        MDC.put("user_id", 0);
        MDC.put("category_id", 0);

        //自动更改有效时间过期的套餐系统状态
        autoCleanOverUserCategory();

        // 自动更改有效时间生效的套餐系统状态
        autoChangeNotStartUserCategory();

        //自动更新回收站中过期的媒体数据
        autoChangeRecycleState();

        autoChangeNormalState();

    }

    /**
     * 自动更改有效时间过期的套餐系统状态
     */
    public void autoCleanOverUserCategory() {

        Date date = new Date();

        List<UserCategoryEntity> userCategoryEntities = userCategoryDao.findByOverTimeByNormal(date, UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

        String s = "";

        for (UserCategoryEntity u : userCategoryEntities
                ) {
            u.setUserCategoryState(UserCategoryEntity.USER_CATEGORY_STATE_OVER);
            s += "," + u.getUserCategoryId();

        }

        if (userCategoryDao.updateList(userCategoryEntities)) {
            logger.log(TaskLog.TASK, "清理用户套餐系统超时任务成功，共改变" + userCategoryEntities.size() + "个用户套餐，id分别为：" + s);
        } else {
            logger.log(TaskLog.TASK, "清理用户套餐系统超时任务失败，找到" + userCategoryEntities.size() + "个用户套餐，id分别为：" + s);
        }

    }

    /**
     * 自动更改有效时间生效的套餐系统状态
     */
    public void autoChangeNotStartUserCategory() {

        Date date = new Date();

        List<UserCategoryEntity> userCategoryEntities = userCategoryDao.findByNotStartBy(date, UserCategoryEntity.USER_CATEGORY_STATE_NOT_START);

        String s = "";

        for (UserCategoryEntity u : userCategoryEntities
                ) {
            u.setUserCategoryState(UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);
            s += "," + u.getUserCategoryId();
        }

        if (userCategoryDao.updateList(userCategoryEntities)) {

            logger.log(TaskLog.TASK, "用户套餐系统生效任务成功，共改变" + userCategoryEntities.size() + "个用户套餐，id分别为：" + s);

        } else {
            logger.log(TaskLog.TASK, "用户套餐系统生效任务失败，找到" + userCategoryEntities.size() + "个用户套餐，id分别为：" + s);
        }

    }

    /**
     * 自动更新回收站中过期的媒体数据
     */
    public void autoChangeRecycleState() {

        Date date = new Date();

        List<MediaDataEntity> mediaDataList = mediaDataDao.findByOver_timeAndState(date, MediaDataEntity.MEDIA_STATE_RECYCLE);

        String s = "";

        for (MediaDataEntity m : mediaDataList
                ) {
            m.setMediaState(MediaDataEntity.MEDIA_STATE_DELETE);
            s += "," + m.getMediaId();
        }

        if (mediaDataDao.updateMediaList(mediaDataList)) {
            logger.log(TaskLog.TASK, "自动更新回收站中媒体数据的状态成功，共改变" + mediaDataList.size() + "个媒体数据，id分别为：" + s);
        } else {
            logger.log(TaskLog.TASK, "自动更新回收站中媒体数据的状态失败，找到" + mediaDataList.size() + "个媒体数据，id分别为：" + s);
        }

    }

    public void autoChangeNormalState() {

        List<UsersEntity> userList = userDao.findAll();

        for (UsersEntity u : userList) {

            List<UserCategoryEntity> userCategoryList = userCategoryDao.findByUser_idAndState(u.getUserId(), UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

            List<MediaDataEntity> mediaDataList = mediaDataDao.findByUser_idAndState(u.getUserId(), MediaDataEntity.MEDIA_STATE_NORMAL);

            for (MediaDataEntity m: mediaDataList
                 ) {
                boolean b = false;
                for (UserCategoryEntity uc:userCategoryList
                     ) {

                    if (uc.getCategoryId() == m.getCategoryId()) {
                        b = true;
                        break;
                    }

                }

                changeMediaDataToRecycle(m);

                if (b) {
                    mediaDataList.remove(m);
                }

            }

            mediaDataDao.updateMediaList(mediaDataList);

        }

    }

}
