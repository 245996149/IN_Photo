package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.RoleInfo;

import java.util.List;

public interface RoleDao {

    /**
     * 查找所有的
     *
     * @return 所有角色对象
     */
    List<RoleInfo> findAllRole();

    /**
     * 删除角色及其对应的模块关系
     *
     * @param role_id 角色id
     * @return 是否成功
     */
    boolean deleteRole(int role_id);

    /**
     * 根据角色查询拥有该角色的所有用户
     *
     * @param role_id 角色id
     * @return 查询到的用户对象队列
     */
    List<AdminInfo> findAdminByRole_id(int role_id);

    /**
     * 根据角色id查询角色
     *
     * @param role_id 角色id
     * @return 对象
     */
    RoleInfo findByRole_id(int role_id);

    /**
     * 根据id队列查询角色
     *
     * @param roleIds id队列
     * @return 对象
     */
    List<RoleInfo> findByRoleIds(List<Integer> roleIds);

    /**
     * 更新角色对象
     *
     * @param roleInfo 角色对象
     * @return 是否成功
     */
    boolean updateRole(RoleInfo roleInfo);

}
