package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.CodeWebinfoEntity;
import cn.inphoto.user.dbentity.PicWebinfoEntity;

/**
 * Created by kaxia on 2017/6/5.
 */
public interface WebinfoDao {

    CodeWebinfoEntity findCodeByUser_idAndCategory_id(Long user_id, Integer category_id, String code_web_info_state);

    PicWebinfoEntity findPicByUser_idAndCategory_id(Long user_id, Integer category_id, String pic_web_info_state);

}
