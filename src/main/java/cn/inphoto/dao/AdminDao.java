package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.AdminEntity;
import cn.inphoto.dbentity.user.UsersEntity;

/**
 * Created by root on 17-7-11.
 */
public interface AdminDao {

    /**
     * 根据user_name查询管理员对象
     *
     * @param admin_name 用户名
     * @return 用户对象
     */
    AdminEntity  findByAdmin_name(String admin_name);

}
