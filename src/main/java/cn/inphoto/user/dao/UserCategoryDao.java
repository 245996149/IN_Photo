package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.UserCategoryEntity;

import java.util.List;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface UserCategoryDao {

    /**
     * 根据user_id、category_id、user_category_state查询用户套餐系统对象
     *
     * @param user_id             用户id
     * @param category_id         套餐系统id
     * @param user_category_state 用户套餐系统对象状态
     * @return 用户套餐系统对象
     */
    UserCategoryEntity findByUser_idAndCategory_id(Long user_id, Integer category_id, String user_category_state);

    /**
     * 根据user_id、user_category_state查询该用户所有套餐系统对象
     *
     * @param user_id             用户id
     * @param user_category_state 用户套餐系统对象状态
     * @return 用户套餐系统对象
     */
    List<UserCategoryEntity> findByUser_idAndState(Long user_id,  String user_category_state);

}
