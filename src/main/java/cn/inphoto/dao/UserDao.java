package cn.inphoto.dao;

import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.User;

import java.util.List;

/**
 * Created by kaxia on 2017/6/5.
 */
public interface UserDao {

    /**
     * 根据user_name查询用户对象
     *
     * @param user_name 用户名
     * @return 用户对象
     */
    User findByUser_name(String user_name);

    /**
     * 根据email查询用户对象
     *
     * @param email 邮箱地址
     * @return 用户对象
     */
    User findByEmail(String email);

    /**
     * 根据user_id查询用户对象
     *
     * @param user_id 用户id号
     * @return 用户对象
     */
    User findByUser_id(Long user_id);

    /**
     * 查询所有用户对象
     *
     * @return 用户对象
     */
    List<User> findAll();

    /**
     * 根据表格分页对象查询客户对象列表
     *
     * @param userPage 表格分页对象
     * @return 客户对象列表
     */
    List<User> findByPage(UserPage userPage);

    /**
     * 根据表格分页对象统计客户对象列表
     *
     * @param userPage 表格分页对象
     * @return 客户对象列表总数
     */
    int countByPage(UserPage userPage);

}
