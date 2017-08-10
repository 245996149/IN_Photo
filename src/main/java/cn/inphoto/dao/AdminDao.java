package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.page.AdminPage;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.Category;
import cn.inphoto.dbentity.user.User;

import java.util.List;

/**
 * Created by root on 17-7-11.
 */
public interface AdminDao {

    /**
     * 根据admin_id查询管理员对象
     *
     * @param admin_id 用户名
     * @return 用户对象
     */
    AdminInfo findByAdmin_id(int admin_id);

    /**
     * 根据admin_name查询管理员对象
     *
     * @param admin_name 用户名
     * @return 用户对象
     */
    AdminInfo findByAdmin_name(String admin_name);

    /**
     * 根据Phone查询管理员对象
     *
     * @param Phone 手机号
     * @return 用户对象
     */
    AdminInfo findByPhone(String Phone);

    /**
     * 根据Email查询管理员对象
     *
     * @param Email 邮箱
     * @return 用户对象
     */
    AdminInfo findByEmail(String Email);

    /**
     * 根据表格分页对象查询用户对象列表
     *
     * @param adminPage 表格分页对象
     * @return 用户对象列表
     */
    List<AdminInfo> findByPage(AdminPage adminPage);

    /**
     * 根据表格分页对象统计用户对象列表
     *
     * @param adminPage 表格分页对象
     * @return 用户对象列表总数
     */
    int countByPage(AdminPage adminPage);
}
