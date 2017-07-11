package cn.inphoto.user.dao;

import cn.inphoto.dbentity.MediaCodeEntity;

import java.util.List;

/**
 * Created by kaxia on 2017/6/5.
 */
public interface MediaCodeDao {


    /**
     * 根据user_id、category_id、media_code(媒体验证码)查询媒体验证码对象
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_code  媒体验证码
     * @return 媒体验证码对象
     */
    MediaCodeEntity findByUser_idAndCategory_idAndMedia_code(Long user_id, Integer category_id, String media_code);

    /**
     * 根据user_id、category_id查询所有媒体验证码对象
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @return 媒体验证码对象List
     */
    List<MediaCodeEntity> findByUser_idAndCategory_id(Long user_id, Integer category_id);

}
