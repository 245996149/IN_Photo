package cn.inphoto.task;

import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dbentity.user.UserCategory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.inphoto.util.DateUtil.getTodayDate;

@Component
public class UserCategoryTask {

    @Resource
    private UserCategoryDao userCategoryDao;

    private static UserCategoryTask userCategoryTask;

    public static void setUserCategoryTask(UserCategoryTask userCategoryTask) {
        UserCategoryTask.userCategoryTask = userCategoryTask;
    }

    @PostConstruct
    public void init() {
        userCategoryTask = this;
        userCategoryTask.userCategoryDao = this.userCategoryDao;
    }

    /**
     * 清理过期的套餐
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 20 2 * * ? ")
    public void cleanOverUserCategory() {

        System.out.println("清理过期的套餐");

        List<UserCategory> userCategoryList = userCategoryDao.findByState(UserCategory.USER_CATEGORY_STATE_NORMAL);

        if (userCategoryList.isEmpty()) {
            return;
        }

        List<UserCategory> updateUserCategoryList = new ArrayList<>();

        for (UserCategory uc : userCategoryList
                ) {
            if (uc.getEndTime().getTime() < getTodayDate().getTime()) {
                uc.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_OVER);
                updateUserCategoryList.add(uc);
            }
        }

        if (!updateUserCategoryList.isEmpty()) {

            userCategoryDao.updateList(updateUserCategoryList);

        }

    }

    /**
     * 自动开通套餐
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 30 2 * * ? ")
    public void openNotStartUserCategory() {

        System.out.println("自动开通套餐");

        List<UserCategory> userCategoryList = userCategoryDao.findByState(UserCategory.USER_CATEGORY_STATE_NOT_START);

        if (userCategoryList.isEmpty()) {
            return;
        }

        List<UserCategory> updateUserCategoryList = new ArrayList<>();

        for (UserCategory uc : userCategoryList
                ) {
            if (uc.getBeginTime().getTime() < getTodayDate().getTime()) {
                uc.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_NORMAL);
                updateUserCategoryList.add(uc);
            }
        }

        if (!updateUserCategoryList.isEmpty()) {

            userCategoryDao.updateList(updateUserCategoryList);

        }

    }


}
