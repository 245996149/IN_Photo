package cn.inphoto.user.dao;

import cn.inphoto.dbentity.UserCategoryEntity;

import java.util.Date;
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
    List<UserCategoryEntity> findByUser_idAndState(Long user_id, String user_category_state);

    /**
     * 根据user_id查询该用户所有套餐系统对象
     *
     * @param user_id 用户id
     * @return 用户套餐系统对象
     */
    List<UserCategoryEntity> findByUser_id(Long user_id);

    /**
     * 找到正常状态下结束时间在现在之前的套餐系统
     *
     * @param over_time           现在时间
     * @param user_category_state 用户状态
     * @return 用户套餐系统对象
     */
    List<UserCategoryEntity> findByOverTimeByNormal(Date over_time, String user_category_state);

    /**
     * 找到未生效状态下开始时间在现在之前的套餐系统
     *
     * @param begin_time          现在时间
     * @param user_category_state 用户状态
     * @return 用户套餐系统对象
     */
    List<UserCategoryEntity> findByNotStartBy(Date begin_time, String user_category_state);

    /**
     * 查询所有在某状态下的用户套餐系统
     *
     * @param user_category_state 某状态
     * @return 用户套餐系统对象
     */
    List<UserCategoryEntity> findByState(String user_category_state);

    /**
     * 更新数据库
     *
     * @param userCategoryList 更新的对象队列
     * @return 是否更新成功
     */
    boolean updateList(List<UserCategoryEntity> userCategoryList);

}
