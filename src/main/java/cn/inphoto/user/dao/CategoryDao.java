package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.CategoryEntity;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface CategoryDao {

    /**
     * 根据套餐简码查询套餐系统
     *
     * @param category_code 简码
     * @return
     */
    CategoryEntity findByCategory_code(String category_code);

}
