package cn.inphoto.task;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.dbentity.user.UserCategory;
import cn.inphoto.util.DBUtil;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.DateUtil.getSevenDateLater;
import static cn.inphoto.util.DateUtil.getThirtyDateLater;
import static cn.inphoto.util.DateUtil.getTodayDate;

@Component
public class MediaTask {

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

            System.out.println(u.toString());

            // 查找该客户所有的正常状态下的媒体
            List<MediaData> mediaDataList = mediaDataDao.findByUser_idAndState(
                    u.getUserId(), MediaData.MEDIA_STATE_NORMAL);

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
                                uc.getUserId(), uc.getCategoryId(), MediaData.MEDIA_STATE_NORMAL,
                                map.get(uc.getCategoryId()).size() - uc.getMediaNumber());
                        updateMediaDataList.addAll(m2);
                    }
                }

            }

            System.out.println("..........................................");

            // 写入数据
            if (!updateMediaDataList.isEmpty()) {

                 /*遍历待更新队列，将队列中的媒体数据设置为待删除状态，并设置删除时间为7天后*/
                for (MediaData m : updateMediaDataList
                        ) {
                    m.setMediaState(MediaData.MEDIA_STATE_WILL_DELETE);
                    m.setDeleteTime(new Timestamp(getSevenDateLater().getTime()));
                    System.out.println(m.toString());
                }

                mediaDataDao.updateMediaList(updateMediaDataList);
            }

        }

    }

    /**
     * 清理过期待删除媒体数据
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 50 2 * * ? ")
    public void cleanWillDeleteMedia() {

        System.out.println("清理过期待删除媒体数据");

        // 查找所有待删除状态下的媒体数据
        List<MediaData> mediaDataList = mediaDataDao.findByState(MediaData.MEDIA_STATE_WILL_DELETE);

        // 判断队列不为空
        if (mediaDataList.isEmpty()) {
            return;
        }

        List<MediaData> updateMediaList = new ArrayList<>();

        // 遍历队列，找到删除时间早于今天0时的媒体数据，添加过期时间、回收站状态,并将其添加到待更新队列
        for (MediaData m : mediaDataList
                ) {
            if (m.getDeleteTime().getTime() < getTodayDate().getTime()) {
                m.setMediaState(MediaData.MEDIA_STATE_RECYCLE);
                m.setOverTime(new Timestamp(getThirtyDateLater().getTime()));
                updateMediaList.add(m);
            }
        }

        // 判断待更新队列是否为空
        if (updateMediaList.isEmpty()) {
            return;
        }

        mediaDataDao.updateMediaList(updateMediaList);

    }

    /**
     * 清理回收站中过期媒体数据
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 40 2 * * ? ")
    public void cleanRecycleMedia() {

        System.out.println("清理回收站中过期媒体数据");

        // 查找所有待删除状态下的媒体数据
        List<MediaData> mediaDataList = mediaDataDao.findByState(MediaData.MEDIA_STATE_RECYCLE);

        // 判断队列不为空
        if (mediaDataList.isEmpty()) {
            return;
        }

        List<MediaData> updateMediaList = new ArrayList<>();

        // 遍历队列，找到删除时间早于今天0时的媒体数据，添加过期时间、回收站状态,并将其添加到待更新队列
        for (MediaData m : mediaDataList
                ) {
            if (m.getOverTime().getTime() < getTodayDate().getTime()) {
                m.setMediaState(MediaData.MEDIA_STATE_DELETE);
                updateMediaList.add(m);
            }
        }

        // 判断待更新队列是否为空
        if (updateMediaList.isEmpty()) {
            return;
        }

        mediaDataDao.updateMediaList(updateMediaList);

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
