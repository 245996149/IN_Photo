package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.UsersEntity;

/**
 * Created by kaxia on 2017/6/5.
 */
public interface UserDao {

    public UsersEntity searchByUser_name(String user_name);

}
