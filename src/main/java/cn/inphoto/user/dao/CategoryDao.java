package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.CategoryEntity;

import java.util.List;

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

    /**
     * 根据套餐id查询套餐系统
     *
     * @param category_id 简码
     * @return
     */
    CategoryEntity findByCategory_id(int category_id);

    /**
     * 查询系统中所有的套餐系统
     * @return 套餐系统队列
     */
    List<CategoryEntity> findAll();

}
