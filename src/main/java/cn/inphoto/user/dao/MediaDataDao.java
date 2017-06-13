package cn.inphoto.user.dao;

import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.page.TablePage;

import java.util.List;

/**
 * Created by kaxia on 2017/6/12.
 */
public interface MediaDataDao {

    List<MediaDataEntity> findByPage(TablePage tablePage);

    boolean addMediaData(MediaDataEntity mediaDataEntity);

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

    boolean updateMediaData(MediaDataEntity mediaDataEntity);

    MediaDataEntity findByMedia_id(Long media_id);
}
