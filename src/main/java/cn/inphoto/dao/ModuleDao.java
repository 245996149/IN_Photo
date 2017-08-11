package cn.inphoto.dao;

import cn.inphoto.dbentity.admin.ModuleInfo;

import java.util.List;

public interface ModuleDao {

    /**
     * 查找所有模块
     *
     * @return 所有模块
     */
    List<ModuleInfo> findAll();

    /**
     * 根据id队列查找所有模块
     *
     * @param moduleIds id队列
     * @return 模块队列
     */
    List<ModuleInfo> findByModuleIds(List<Integer> moduleIds);

}
