package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.ShareDataEntity;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface ShareDataDao {

    /**
     * 根据beginTime、ndTime、share_type(分享数据类型)统计数据
     *
     * @param beginTime  统计开始时间
     * @param endTime    统计结束时间
     * @param share_type 分享数据类型
     * @return 统计数据
     */
    int countByTime(String beginTime, String endTime, String share_type);


}
