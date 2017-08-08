package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.RoleInfo;

import java.util.List;

public interface RoleDao {

    /**
     * 查找所有的
     *
     * @return
     */
    List<RoleInfo> findAllRole();

    boolean deleteRole(int role_id);

}
