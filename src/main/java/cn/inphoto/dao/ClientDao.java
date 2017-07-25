package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.RoleInfo;

import java.util.List;

public interface ClientDao {

    /**
     * 根据输入的admin_id查找对应的RoleInfo
     *
     * @param admin_id
     * @return
     */
    List<RoleInfo> findRoleByAdminId(int admin_id);

}
