package cn.inphoto.daoimpl;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.page.TablePage;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static cn.inphoto.util.DirUtil.getErrorInfoFromException;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class MediaDataDaoImpl extends SuperDao implements MediaDataDao {

    Logger logger = Logger.getLogger(MediaDataDaoImpl.class);

    @Override
    public List<MediaData> findByPage(TablePage tablePage) {

        try (Session session = sessionFactory.openSession()) {

            Query query;

            // 判断是否有category_id参数
            if (tablePage.getCategory_id() == 0) {

                query = session.createQuery(" from MediaData where userId = :user_id and mediaState in (:media_state) " +
                        "and mediaType = :mediaType order by mediaId desc");

            } else {

                query = session.createQuery(" from MediaData where userId = :user_id and mediaState in (:media_state) " +
                        "and categoryId = :category_id and mediaType = :mediaType order by mediaId desc");
                query.setParameter("category_id", tablePage.getCategory_id());

            }

            query.setParameter("mediaType", MediaData.MediaType.MediaData);
            query.setParameter("user_id", tablePage.getUser_id());
            query.setParameter("media_state", tablePage.getMedia_state_list());

            query.setFirstResult(tablePage.getBegin());
            query.setMaxResults(tablePage.getPageSize());

            return query.list();
        }
    }

    @Override
    public int countByUser_idAndCategory_idAndMedia_state(Long user_id, Integer category_id, List<MediaData.MediaState> media_state_list) {
        try (Session session = sessionFactory.openSession()) {

            Query query;

            // 判断是否有category_id参数
            if (category_id == null || category_id == 0) {
                query = session.createQuery(
                        "select count(*) from MediaData where userId = :user_id  and " +
                                "mediaState in (:media_state_list) and mediaType = :mediaType");
            } else {
                query = session.createQuery(
                        "select count(*) from MediaData where userId = :user_id and " +
                                "categoryId = :category_id and mediaState in (:media_state_list) and mediaType = :mediaType");
                query.setParameter("category_id", category_id);
            }

            query.setParameter("user_id", user_id);
            query.setParameterList("media_state_list", media_state_list);
            query.setParameter("mediaType", MediaData.MediaType.MediaData);

            return ((Long) query.uniqueResult()).intValue();
        }
    }

    @Override
    public int countByUser_idAndCategory_idAndMedia_stateAndOver_time(Long user_id, Integer category_id, Date beginTime, Date endTime, MediaData.MediaState media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = null;

            if (category_id == null) {
                query = session.createQuery(
                        "select count(*) from MediaData where userId = :user_id  and mediaState = :media_state and" +
                                " overTime between :beginTime and :endTime");

            } else {
                query = session.createQuery(
                        "select count(*) from MediaData where" +
                                " userId = :user_id  and mediaState = :media_state and categoryId = :category_id and " +
                                "overTime between :beginTime and :endTime ");
                query.setParameter("category_id", category_id);
            }

            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);

            return ((Long) query.uniqueResult()).intValue();
        }
    }

    @Override
    public int countByUser_idAndCategory_idAndMedia_stateAndCreate_Time(Long user_id, Integer category_id, Date beginTime, Date endTime, MediaData.MediaState media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query;
            if (category_id == null || category_id == 0) {
                query = session.createQuery(
                        "select count(*) from MediaData where" +
                                " userId = :user_id  and mediaState = :media_state and " +
                                "createTime between :beginTime and :endTime and mediaType = :mediaType");
            } else {
                query = session.createQuery(
                        "select count(*) from MediaData where" +
                                " userId = :user_id  and mediaState = :media_state and categoryId = :category_id and " +
                                "createTime between :beginTime and :endTime and mediaType = :mediaType");
                query.setParameter("category_id", category_id);
            }

            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
            query.setParameter("mediaType", MediaData.MediaType.MediaData);

            return ((Long) query.uniqueResult()).intValue();
        }
    }

    @Override
    public MediaData findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(Long user_id, int category_id, MediaData.MediaState media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "from MediaData where userId = :user_id  and mediaState = :media_state and categoryId = :category_id order by createTime");

            query.setParameter("category_id", category_id);
            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);
            query.setMaxResults(1);

            return (MediaData) query.uniqueResult();
        }
    }

    @Override
    public List<MediaData> findByUser_idAndCategory_idAndMedia_stateOrderByCreate_time(
            Long user_id, int category_id, MediaData.MediaState media_state, int number) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "from MediaData where userId = :user_id  and mediaState = :media_state and categoryId = :category_id order by createTime");

            query.setParameter("category_id", category_id);
            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);
            query.setMaxResults(number);

            return query.list();
        }
    }

    @Override
    public List<MediaData> findByState(MediaData.MediaState media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "from MediaData where mediaState = :media_state");

            query.setParameter("media_state", media_state);

            return query.list();
        }
    }

    @Override
    public MediaData findByMedia_id(Long media_id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(MediaData.class, media_id);

        }
    }

    @Override
    public List<MediaData> findByMedia_ids(List<Long> media_ids) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaData where mediaId in (:ids)");

            query.setParameterList("ids", media_ids);

            return query.list();
        }

    }

    @Override
    public boolean updateMediaList(List<MediaData> mediaDataList) {

        boolean flag = false;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {

            for (MediaData m : mediaDataList
                    ) {
                session.update(m);
            }

            transaction.commit();
            flag = true;

        } catch (Exception e) {

            logger.info(getErrorInfoFromException(e));
            transaction.rollback();

        } finally {

            session.close();

        }

        return flag;

    }

    @Override
    public List<MediaData> findByOver_timeAndState(Date over_time, String media_data_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaData where overTime < :over_time and mediaState = :media_data_state");

            query.setParameter("over_time", over_time);
            query.setParameter("media_data_state", media_data_state);

            return query.list();
        }

    }

    @Override
    public List<MediaData> findByUser_idAndState(Long user_id, MediaData.MediaState media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaData where userId = :user_id and mediaState = :media_state");

            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);

            return query.list();
        }

    }

    @Override
    public List<MediaData> findByUser_idAndCategory_idAndState(Long user_id, Integer category_id, String media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "from MediaData where userId = :user_id and categoryId = :category_id and mediaState = :media_state");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("media_state", media_state);

            return query.list();
        }
    }

    @Override
    public List<MediaData> findByUser_idAndCategory_idAndBeginDateAndEndDate(
            Long user_id, Integer category_id, Date beginDate, Date endDate) {
        try (Session session = sessionFactory.openSession()) {
            Query query;
            if (beginDate != null && endDate != null) {
                query = session.createQuery(
                        "from MediaData where userId = :user_id and categoryId = :category_id " +
                                "and createTime between :beginDate and :endDate");
                query.setParameter("beginDate", beginDate);
                query.setParameter("endDate", endDate);
            } else if (beginDate != null) {
                query = session.createQuery(
                        "from MediaData where userId = :user_id and categoryId = :category_id " +
                                "and createTime > :beginDate");
                query.setParameter("beginDate", beginDate);
            } else {
                query = session.createQuery(
                        "from MediaData where userId = :user_id and categoryId = :category_id " +
                                "and createTime < :endDate");
                query.setParameter("endDate", endDate);
            }
            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);

            return query.list();
        }
    }

    @Override
    public MediaData findByMediaKey(String mediaKey) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "from MediaData where mediaKey = :mediaKey  ");

            query.setParameter("mediaKey", mediaKey);
            query.setMaxResults(1);

            return (MediaData) query.uniqueResult();
        }
    }

}
