package cn.inphoto.user.dao;

import cn.inphoto.dbentity.MediaDataEntity;
import cn.inphoto.dbentity.page.TablePage;

import java.util.Date;
import java.util.List;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface MediaDataDao {

    /**
     * 根据表格分页对象查询媒体对象列表
     *
     * @param tablePage 表格分页对象
     * @return 媒体对象列表
     */
    List<MediaDataEntity> findByPage(TablePage tablePage);

    /**
     * 根据user_id、category_id、media_state统计总数
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体书记状态
     * @return 统计数据
     */
    int countByUser_idAndCategory_idAndMedia_state(Long user_id, Integer category_id, String media_state);

    /**
     * 根据user_id、category_id、media_state统计总数
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体书记状态
     * @return 统计数据
     */
    int countByUser_idAndCategory_idAndMedia_state(Long user_id, Integer category_id, Date beginTime, Date endTime, String media_state);

    /**
     * 根据user_id、category_id、media_state查询时间最前的一个media
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体状态
     * @return
     */
    MediaDataEntity findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(Long user_id, int category_id, String media_state);

    /**
     * 根据media_id查询媒体对象
     *
     * @param media_id 媒体id
     * @return 媒体对象
     */
    MediaDataEntity findByMedia_id(Long media_id);

    /**
     * 根据media_id查询多个媒体对象
     *
     * @param media_ids 媒体id队列
     * @return 媒体对象
     */
    List<MediaDataEntity> findByMedia_ids(List<Long> media_ids);

    /**
     * 向数据库中更新多个对象
     *
     * @param mediaDataList 对象队列
     * @return 返回是否成功
     */
    boolean updateMediaList(List<MediaDataEntity> mediaDataList);

    /**
     * 找到回收站中所有时间过期的媒体数据
     *
     * @param over_time
     * @param media_data_state
     * @return
     */
    List<MediaDataEntity> findByOver_timeAndState(Date over_time, String media_data_state);

    /**
     * 根据用户及状态查询所有对应的媒体数据
     *
     * @param media_state
     * @return
     */
    List<MediaDataEntity> findByUser_idAndState(Long user_id,String media_state);

}
