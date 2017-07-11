package cn.inphoto.user.dao;

import cn.inphoto.dbentity.UsersEntity;

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
    UsersEntity findByUser_name(String user_name);

    /**
     * 根据user_id查询用户对象
     *
     * @param user_id 用户id号
     * @return 用户对象
     */
    UsersEntity findByUser_id(Long user_id);

    /**
     * 查询所有用户对象
     *
     * @return 用户对象
     */
    List<UsersEntity> findAll();

}
