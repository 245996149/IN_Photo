package cn.inphoto.dao;

import cn.inphoto.dbentity.user.CodeWebInfo;
import cn.inphoto.dbentity.user.PicWebinfo;
import cn.inphoto.dbentity.user.ShareInfo;

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
    CodeWebInfo findCodeByUser_idAndCategory_id(Long user_id, Integer category_id, String code_web_info_state);

    /**
     * 根据user_id、categoey_id、pic_web_info_state查询展示页面设置
     *
     * @param user_id            用户id
     * @param category_id        套餐系统id
     * @param pic_web_info_state 状态
     * @return
     */
    PicWebinfo findPicByUser_idAndCategory_id(Long user_id, Integer category_id, String pic_web_info_state);

    /**
     * 根据pic_web_info_id查询展示页面设置
     * @param pic_web_info_id 展示页面设置id
     * @return 展示页面设置
     */
    PicWebinfo findPicByPic_id(Long pic_web_info_id);

    /**
     * 根据code_web_info_id查询提取页面设置
     * @param code_web_info_id 提取页面设置id
     * @return 提取页面设置
     */
    CodeWebInfo findCodeByCode_id(Long code_web_info_id);

    /**
     * 根据share_info_id查询分享设置
     * @param share_info_id 分享设置id
     * @return 分享设置
     */
    ShareInfo findShareByShare_id(Long share_info_id);

    /**
     * 根据User_id查询分享设置
     * @param user_id 分享设置id
     * @return 分享设置
     */
    ShareInfo findShareByUser_idAndCategory(Long user_id, Integer category_id);

}
