package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.user.Category;

import java.util.List;

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
    AdminInfo findByAdmin_name(String admin_name);

    /**
     * 根据admin_id查询该管理员所有模块
     *
     * @param admin_id
     * @return
     */
    List<ModuleInfo> findModulesByAdmin(int admin_id);

    /**
     * 根据admin_id查询该管理员可以管理的所有模块
     *
     * @param admin_id
     * @return
     */
    List<Category> findCategoryByAdmin(int admin_id);

}
