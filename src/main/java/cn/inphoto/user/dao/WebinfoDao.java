package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.CodeWebinfoEntity;
import cn.inphoto.user.dbentity.PicWebinfoEntity;

/**
 * Created by kaxia on 2017/6/5.
 */
public interface WebinfoDao {

    /**
     * 根据user_id、categoey_id、code_web_info_state查询验证页面设置
     *
     * @param user_id             用户id
     * @param category_id         套餐系统id
     * @param code_web_info_state 状态
     * @return
     */
    CodeWebinfoEntity findCodeByUser_idAndCategory_id(Long user_id, Integer category_id, String code_web_info_state);

    /**
     * 根据user_id、categoey_id、pic_web_info_state查询展示页面设置
     *
     * @param user_id            用户id
     * @param category_id        套餐系统id
     * @param pic_web_info_state 状态
     * @return
     */
    PicWebinfoEntity findPicByUser_idAndCategory_id(Long user_id, Integer category_id, String pic_web_info_state);

}
