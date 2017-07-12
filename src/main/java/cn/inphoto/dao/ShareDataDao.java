package cn.inphoto.dao;

import java.util.Date;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface ShareDataDao {

    /**
     * * 根据user_id、beginTime、endTime、share_type(分享数据类型)统计数据
     *
     * @param user_id     用户id

     * @param beginTime   统计开始时间
     * @param endTime     统计结束时间
     * @param share_type  分享数据类型
     * @return 统计数据
     */
    int countByTimeTotal(Long user_id, Date beginTime, Date endTime, String share_type);

    /**
     * * 根据user_id、category_id、beginTime、endTime、share_type(分享数据类型)统计数据
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param beginTime   统计开始时间
     * @param endTime     统计结束时间
     * @param share_type  分享数据类型
     * @return 统计数据
     */
    int countByTime(Long user_id, int category_id, Date beginTime, Date endTime, String share_type);

}
