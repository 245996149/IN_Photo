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
import java.util.*;

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

//            System.out.println(u.toString());

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

//            System.out.println("..........................................");

            // 写入数据
            if (!updateMediaDataList.isEmpty()) {

                 /*遍历待更新队列，将队列中的媒体数据设置为待删除状态，并设置删除时间为7天后*/
                for (MediaData m : updateMediaDataList
                        ) {
                    m.setMediaState(MediaData.MEDIA_STATE_WILL_DELETE);
                    m.setDeleteTime(new Timestamp(getSevenDateLater().getTime()));
//                    System.out.println(m.toString());
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
     * 判断待删除下媒体是否可以恢复成正常状态，可以则恢复
     */
    @Scheduled(cron = "0 45 2 * * ? ")
    public void changeWillDeleteMediaToNormal() {

        System.out.println("判断待删除下媒体是否可以恢复成正常状态，可以则恢复");

        // 查找所有正常状态的客户
        List<User> userList = userDao.findByState(User.USER_STATE_NORMAL);

        // 遍历客户
        for (User u : userList
                ) {

            // 查找所有待删除状态下的媒体数据
            List<MediaData> mediaDataList = mediaDataDao.findByUser_idAndState(
                    u.getUserId(), MediaData.MEDIA_STATE_WILL_DELETE);

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
                            u.getUserId(), m.getCategoryId(), Collections.singletonList(MediaData.MEDIA_STATE_NORMAL));
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

                 /*遍历待更新队列，将队列中的媒体数据设置为正常状态*/
                for (MediaData m : updateMediaList
                        ) {
                    m.setMediaState(MediaData.MEDIA_STATE_NORMAL);
                }

                mediaDataDao.updateMediaList(updateMediaList);
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
