package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.page.TablePage;

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
    long countByUser_idAndCategory_idAndMedia_state(Long user_id, int category_id, String media_state);

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
}
