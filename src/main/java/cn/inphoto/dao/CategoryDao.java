package cn.inphoto.dao;

import cn.inphoto.dbentity.user.Category;

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
    Category findByCategory_code(String category_code);

    /**
     * 根据套餐id查询套餐系统
     *
     * @param category_id 简码
     * @return
     */
    Category findByCategory_id(int category_id);

    /**
     * 根据id队列查找所有的套餐系统
     *
     * @param categoryIds id队列
     * @return 套餐系统队列
     */
    List<Category> findByCategoryIds(List<Integer> categoryIds);

    /**
     * 查询系统中所有的套餐系统
     *
     * @return 套餐系统队列
     */
    List<Category> findAll();


}
