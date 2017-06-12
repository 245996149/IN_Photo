package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.ShareDataEntity;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface ShareDataDao {

    int countByTime(String beginTime,String endTime,String share_type);

    boolean addShareData(ShareDataEntity shareDataEntity);

}
