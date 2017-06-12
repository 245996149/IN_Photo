package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.CategoryEntity;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface CategoryDao {

    CategoryEntity findByCategory_code(String category_code);

}
