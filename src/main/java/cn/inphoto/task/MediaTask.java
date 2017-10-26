package cn.inphoto.task;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.dbentity.user.UserCategory;
import cn.inphoto.log.UserLogLevel;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

import static cn.inphoto.util.DateUtil.*;

@Component
public class MediaTask {

    private Logger logger = Logger.getLogger(MediaTask.class);

    @Resource
    private MediaDataDao mediaDataDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserCategoryDao userCategoryDao;

    private static MediaTask mediaTask;

    public static void setMediaTask(MediaTask mediaTask) {
        MediaTask.mediaTask = mediaTask;
    }

    @PostConstruct
    public void init() {
        mediaTask = this;
        mediaTask.mediaDataDao = this.mediaDataDao;
        mediaTask.userDao = this.userDao;
        mediaTask.userCategoryDao = this.userCategoryDao;
    }

    /**
     * 清理用户套餐过期、其他原因导致的媒体数据超过套餐的媒体
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 0 3 * * ? ")
    public void cleanOverMedia() {

        // 查找所有正常状态的客户
        List<User> userList = userDao.findByState(User.USER_STATE_NORMAL);

        // 遍历客户
        for (User u : userList
                ) {

            // 查找该客户所有的正常状态下的媒体
            List<MediaData> mediaDataList = mediaDataDao.findByUser_idAndState(
                    u.getUserId(), MediaData.MediaState.Normal);

            // 判断媒体是否为空
            if (mediaDataList.isEmpty()) {
                break;
            }

            // 查找该客户所有的正常状态下的媒体
            List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndState(
                    u.getUserId(), UserCategory.USER_CATEGORY_STATE_NORMAL);

            List<MediaData> updateMediaDataList = new ArrayList<>();

            /*遍历媒体，找到没有套餐的所有媒体，将其加入到待更新队列，并将其从原队里中删除*/
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

            // 媒体筛选之后的队列
            Map<Integer, List<MediaData>> map = separateMediaByCategoryId(mediaDataList);

            /*遍历套餐，判断媒体队列中的媒体数量是否超过套餐量，超过则将最早的媒体加入到待更新队列*/
            for (UserCategory uc : userCategoryList
                    ) {
                if (map != null && map.containsKey(uc.getCategoryId())) {
                    if (map.get(uc.getCategoryId()).size() > uc.getMediaNumber()) {
                        List<MediaData> m2 = mediaDataDao.findByUser_idAndCategory_idAndMedia_stateOrderByCreate_time(
                                uc.getUserId(), uc.getCategoryId(), MediaData.MediaState.Normal,
                                map.get(uc.getCategoryId()).size() - uc.getMediaNumber());
                        updateMediaDataList.addAll(m2);
                    }
                }

            }

            // 写入数据
            if (!updateMediaDataList.isEmpty()) {

                StringBuilder a = new StringBuilder();
                 /*遍历待更新队列，将队列中的媒体数据设置为待删除状态，并设置删除时间为7天后*/
                for (MediaData m : updateMediaDataList
                        ) {
                    m.setMediaState(MediaData.MediaState.WillDelete);
                    m.setDeleteTime(new Timestamp(getSevenDateLater().getTime()));
                    a.append(String.valueOf(m.getMediaId())).append("、");
                }

                if (mediaDataDao.updateMediaList(updateMediaDataList)) {

                    MDC.put("user_info", "user_id=" + u.getUserId());

                    logger.log(UserLogLevel.TASK,
                            "清理user_id=" + u.getUserId() +
                                    " 的用户媒体数据量超过套餐的媒体。共清理了media_id为下列的媒体数据：" + a);
                }
            }

        }

    }

    /**
     * 清理过期待删除媒体数据
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 50 2 * * ? ")
    public void cleanWillDeleteMedia() {

        // 查找所有待删除状态下的媒体数据
        List<MediaData> mediaDataList = mediaDataDao.findByState(MediaData.MediaState.WillDelete);

        // 判断队列不为空
        if (mediaDataList.isEmpty()) {
            return;
        }

        List<MediaData> updateMediaList = new ArrayList<>();

        StringBuilder a = new StringBuilder();

        // 遍历队列，找到删除时间早于今天0时的媒体数据，添加过期时间、回收站状态,并将其添加到待更新队列
        for (MediaData m : mediaDataList
                ) {
            if (m.getDeleteTime().getTime() < getTodayDate().getTime()) {
                m.setMediaState(MediaData.MediaState.Recycle);
                m.setOverTime(new Timestamp(getThirtyDateLater().getTime()));
                updateMediaList.add(m);
                a.append(String.valueOf(m.getMediaId())).append("、");
            }
        }

        // 判断待更新队列是否为空
        if (updateMediaList.isEmpty()) {
            return;
        }

        if (mediaDataDao.updateMediaList(updateMediaList)) {
            logger.log(UserLogLevel.TASK,
                    "清理过期待删除媒体数据。共清理了media_id为下列的媒体数据：" + a);
        }

    }

    /**
     * 清理回收站中过期媒体数据
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 40 2 * * ? ")
    public void cleanRecycleMedia() {

        // 查找所有待删除状态下的媒体数据
        List<MediaData> mediaDataList = mediaDataDao.findByState(MediaData.MediaState.Recycle);

        // 判断队列不为空
        if (mediaDataList.isEmpty()) {
            return;
        }

        List<MediaData> updateMediaList = new ArrayList<>();

        StringBuilder a = new StringBuilder();

        // 遍历队列，找到删除时间早于今天0时的媒体数据，添加过期时间、回收站状态,并将其添加到待更新队列
        for (MediaData m : mediaDataList
                ) {
            if (m.getOverTime().getTime() < getTodayDate().getTime()) {
                m.setMediaState(MediaData.MediaState.Delete);
                updateMediaList.add(m);
                a.append(String.valueOf(m.getMediaId())).append("、");
            }
        }

        // 判断待更新队列是否为空
        if (updateMediaList.isEmpty()) {
            return;
        }

        if (mediaDataDao.updateMediaList(updateMediaList)) {
            logger.log(UserLogLevel.TASK,
                    "清理过期待删除媒体数据。共清理了media_id为下列的媒体数据：" + a);

        }

    }

    /**
     * 判断待删除下媒体是否可以恢复成正常状态，可以则恢复
     */
    @Scheduled(cron = "0 45 2 * * ? ")
    public void changeWillDeleteMediaToNormal() {

        // 查找所有正常状态的客户
        List<User> userList = userDao.findByState(User.USER_STATE_NORMAL);

        // 遍历客户
        for (User u : userList
                ) {

            // 查找所有待删除状态下的媒体数据
            List<MediaData> mediaDataList = mediaDataDao.findByUser_idAndState(
                    u.getUserId(), MediaData.MediaState.WillDelete);

            // 判断队列不为空
            if (mediaDataList.isEmpty()) {
                break;
            }

            List<MediaData> updateMediaList = new ArrayList<>();

            // 查找该客户所有的正常状态下的用户套餐
            List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndState(
                    u.getUserId(), UserCategory.USER_CATEGORY_STATE_NORMAL);

            Map<Integer, Long> media_count = new HashMap<>();

            // 循环所有的媒体
            for (MediaData m : mediaDataList
                    ) {

                // 判断map中是否有该媒体对应套餐的总数量，没有则从数据库中查询
                if (!media_count.containsKey(m.getCategoryId())) {
                    int num = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                            u.getUserId(), m.getCategoryId(), Collections.singletonList(MediaData.MediaState.Normal));
                    media_count.put(m.getCategoryId(), (long) num);
                }

                // 循环用户套餐
                for (UserCategory uc : userCategoryList
                        ) {
                    // 如果媒体所属的套餐等于用户套餐系统所属的套餐，则判断数量
                    if (m.getCategoryId() == uc.getCategoryId()) {

                        // 从map中取出该套餐正常媒体总数
                        long num = media_count.get(m.getCategoryId());
                        // 判断正常媒体总数小于用户套餐中规定的数量，则将该媒体移动到待更新队列，将正常媒体总数加1
                        if (num < uc.getMediaNumber()) {
                            updateMediaList.add(m);
                            num++;
                            media_count.put(m.getCategoryId(), num);
                        }
                        break;
                    }
                }
            }

            // 写入数据
            if (!updateMediaList.isEmpty()) {

                StringBuilder a = new StringBuilder();

                 /*遍历待更新队列，将队列中的媒体数据设置为正常状态*/
                for (MediaData m : updateMediaList
                        ) {
                    m.setMediaState(MediaData.MediaState.Normal);
                    a.append(String.valueOf(m.getMediaId())).append("、");
                }

                if (mediaDataDao.updateMediaList(updateMediaList)) {

                    MDC.put("user_info", "user_id=" + u.getUserId());
                    logger.log(UserLogLevel.TASK,
                            "清理user_id=" + u.getUserId() +
                                    " 的用户媒体数据量超过套餐的媒体。共清理了media_id为下列的媒体数据：" + a);
                }

            }

        }

    }

    /**
     * 分离媒体数据队列
     *
     * @param mediaDataList
     * @return
     */
    public static Map<Integer, List<MediaData>> separateMediaByCategoryId(List<MediaData> mediaDataList) {

        if (mediaDataList.isEmpty()) {
            return null;
        }

        Map<Integer, List<MediaData>> result = new HashMap<>();

        for (MediaData m : mediaDataList
                ) {

            List<MediaData> ml = new ArrayList<>();

            if (!result.containsKey(m.getCategoryId())) {
                ml.add(m);
            } else {
                ml = result.get(m.getCategoryId());
                ml.add(m);
            }

            result.put(m.getCategoryId(), ml);

        }

        return result;

    }

}
