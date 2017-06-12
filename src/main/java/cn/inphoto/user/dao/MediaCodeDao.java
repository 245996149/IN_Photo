package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.MediaCodeEntity;
import cn.inphoto.user.dbentity.UsersEntity;

/**
 * Created by kaxia on 2017/6/5.
 */
public interface MediaCodeDao {

    boolean saveMediaCode(MediaCodeEntity mediaCodeEntity);

    boolean updateMediaCode(MediaCodeEntity mediaCodeEntity);

    MediaCodeEntity findByUser_idAndCategory_idAndMedia_code(Long user_id,Integer category_id,String media_code);

}
