package cn.inphoto.dao;

import cn.inphoto.dbentity.user.MediaData;
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
    List<MediaData> findByPage(TablePage tablePage);

    /**
     * 根据user_id、category_id、media_state统计总数
     *
     * @param user_id          用户id
     * @param category_id      套餐系统id
     * @param media_state_list 媒体数据状态队列
     * @return 统计数据
     */
    int countByUser_idAndCategory_idAndMedia_state(Long user_id, Integer category_id, List<MediaData.MediaState> media_state_list);


    /**
     * 根据user_id、category_id、media_state、过期时间统计总数
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体书记状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @return 统计数据
     */
    int countByUser_idAndCategory_idAndMedia_stateAndOver_time(Long user_id, Integer category_id, Date beginTime, Date endTime, MediaData.MediaState media_state);

    /**
     * 根据user_id、category_id、media_state、创建时间统计总数
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体书记状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @return 统计数据
     */
    int countByUser_idAndCategory_idAndMedia_stateAndCreate_Time(Long user_id, Integer category_id, Date beginTime, Date endTime, MediaData.MediaState media_state);

    /**
     * 根据user_id、category_id、media_state查询时间最前的一个media
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体状态
     * @return
     */
    MediaData findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(Long user_id, int category_id, MediaData.MediaState media_state);

    /**
     * 根据user_id、category_id、media_state查询时间最前的一个media
     *
     * @param user_id     用户id
     * @param category_id 套餐系统id
     * @param media_state 媒体状态
     * @param number      查找的数量
     * @return
     */
    List<MediaData> findByUser_idAndCategory_idAndMedia_stateOrderByCreate_time(
            Long user_id, int category_id, MediaData.MediaState media_state, int number);

    /**
     * 根据媒体状态找到该状态的所有媒体
     *
     * @param media_state 媒体状态
     * @return 媒体队列
     */
    List<MediaData> findByState(MediaData.MediaState media_state,MediaData.MediaType media_type);

    /**
     * 根据media_id查询媒体对象
     *
     * @param media_id 媒体id
     * @return 媒体对象
     */
    MediaData findByMedia_id(Long media_id);

    /**
     * 根据media_id查询多个媒体对象
     *
     * @param media_ids 媒体id队列
     * @return 媒体对象
     */
    List<MediaData> findByMedia_ids(List<Long> media_ids);

    /**
     * 向数据库中更新多个对象
     *
     * @param mediaDataList 对象队列
     * @return 返回是否成功
     */
    boolean updateMediaList(List<MediaData> mediaDataList);

    /**
     * 找到回收站中所有时间过期的媒体数据
     *
     * @param over_time
     * @param media_data_state
     * @return
     */
    List<MediaData> findByOver_timeAndState(Date over_time, String media_data_state);

    /**
     * 根据用户及状态查询所有对应的媒体数据
     *
     * @param media_state
     * @return
     */
    List<MediaData> findByUser_idAndState(Long user_id, MediaData.MediaState media_state);

    /**
     * 根据用户、套餐及状态查询所有对应的媒体数据
     *
     * @param user_id     客户
     * @param category_id 套餐
     * @param media_state 状态
     * @return
     */
    List<MediaData> findByUser_idAndCategory_idAndState(Long user_id, Integer category_id, String media_state);

    List<MediaData> findByUser_idAndCategory_idAndBeginDateAndEndDate(Long user_id, Integer category_id, Date beginDate, Date endDate,MediaData.MediaType mediaType);

    MediaData findByMediaKey(String mediaKey);

}
