package cn.inphoto.task;

import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dbentity.user.UserCategory;
import cn.inphoto.log.UserLogLevel;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.inphoto.util.DateUtil.getTodayDate;

@Component
public class UserCategoryTask {

    private Logger logger = Logger.getLogger(UserCategoryTask.class);

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

        List<UserCategory> userCategoryList = userCategoryDao.findByState(UserCategory.UserState.NORMAL);

        if (userCategoryList.isEmpty()) {
            return;
        }

        List<UserCategory> updateUserCategoryList = new ArrayList<>();

        StringBuilder a = new StringBuilder();

        for (UserCategory uc : userCategoryList
                ) {
            if (uc.getEndTime().getTime() < getTodayDate().getTime()) {
                uc.setUserCategoryState(UserCategory.UserState.OVER);
                updateUserCategoryList.add(uc);
                a.append(String.valueOf(uc.getUserCategoryId())).append("、");
            }
        }

        if (!updateUserCategoryList.isEmpty()) {

            if (userCategoryDao.updateList(updateUserCategoryList)) {

                logger.log(UserLogLevel.TASK,
                        "清理过期的用户套餐。共清理了userCategoryId为下列的用户套餐：" + a);

            }

        }

    }

    /**
     * 自动开通套餐
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 30 2 * * ? ")
    public void openNotStartUserCategory() {

        List<UserCategory> userCategoryList = userCategoryDao.findByState(UserCategory.UserState.NOT_START);

        if (userCategoryList.isEmpty()) {
            return;
        }

        List<UserCategory> updateUserCategoryList = new ArrayList<>();

        StringBuilder a = new StringBuilder();

        for (UserCategory uc : userCategoryList
                ) {
            if (uc.getBeginTime().getTime() < getTodayDate().getTime()) {
                uc.setUserCategoryState(UserCategory.UserState.NORMAL);
                updateUserCategoryList.add(uc);
                a.append(String.valueOf(uc.getUserCategoryId())).append("、");
            }
        }

        if (!updateUserCategoryList.isEmpty()) {

            if(userCategoryDao.updateList(updateUserCategoryList)){
                logger.log(UserLogLevel.TASK,
                        "自动开通用户套餐。共开通了userCategoryId为下列的用户套餐：" + a);
            }

        }

    }


}
