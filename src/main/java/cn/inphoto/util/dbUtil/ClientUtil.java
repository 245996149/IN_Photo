package cn.inphoto.util.dbUtil;

import cn.inphoto.dao.MediaCodeDao;
import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.user.MediaCode;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.UserCategory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.ResultMapUtil.createResult;

@Component
public class ClientUtil {

    @Resource
    private UserCategoryDao userCategoryDao;


    private static ClientUtil clientUtil;

    public static void setClientUtil(ClientUtil clientUtil) {
        ClientUtil.clientUtil = clientUtil;
    }

    @PostConstruct
    public void init() {
        clientUtil = this;
        clientUtil.userCategoryDao = this.userCategoryDao;
    }

    /**
     * 停用用户套餐
     *
     * @param userCategory_ids 要停用的的用户套餐的id
     * @return 包含结果和信息的map
     */
    public static Map stopUserCategory(List<Long> userCategory_ids) {

        // 查找对应的用户套餐
        List<UserCategory> userCategoryList = clientUtil.userCategoryDao.findByUser_category_ids(userCategory_ids);

        // 判断传入参数是否正确
        if (userCategoryList == null || userCategoryList.isEmpty()) {
            return createResult(false, "传入的参数不正确，请确认不为空");
        }

        // 更新用户套餐
        for (UserCategory uc : userCategoryList
                ) {
            uc.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_OVER);
            uc.setEndTime(new Timestamp(System.currentTimeMillis()));
        }

        // 更新数据库中的用户套餐
        if (!clientUtil.userCategoryDao.updateList(userCategoryList)) {

            // 更新失败
            return createResult(false, "更新对应的用户套餐时发生了错误，请稍后再试。");
        }

        return createResult(true, "更新成功，对应的媒体数据将在下一个数据库更新周期更新状态");

    }

}
