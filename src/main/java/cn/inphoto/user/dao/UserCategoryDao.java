package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.UserCategoryEntity;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface UserCategoryDao {

    UserCategoryEntity findByUser_idAndCategory_id(Long user_category_id, Integer user_id, String user_category_state);

    boolean addUserCategory(UserCategoryEntity userCategoryEntity);

}
