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

    long countByUser_idAndCategory_idAndMedia_state(Long user_id, int category_id,String media_state);

    MediaDataEntity  findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(Long user_id, int category_id,String media_state);

    boolean updateMediaData(MediaDataEntity mediaDataEntity);

}
