package cn.inphoto.dao;

import cn.inphoto.dbentity.user.ShareClickData;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 17-7-11.
 */
public interface ShareClickDataDao {

    int countByTimeTotal(Long user_id, Date beginTime, Date endTime);

    int countByTime(Long user_id, Integer category_id, Date beginTime, Date endTime);

    List<ShareClickData> findByTime(Long user_id, Integer category_id, Date beginTime, Date endTime);

}
