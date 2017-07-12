package cn.inphoto.daoimpl;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.user.MediaDataEntity;
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
    public List<MediaDataEntity> findByPage(TablePage tablePage) {

        try (Session session = sessionFactory.openSession()) {

            Query query;

            // 判断是否有category_id参数
            if (tablePage.getCategory_id() == 0) {

                query = session.createQuery(" from MediaDataEntity where userId = :user_id and mediaState = :media_state order by deleteTime desc");

            } else {

                query = session.createQuery(" from MediaDataEntity where userId = :user_id and mediaState = :media_state and categoryId = :category_id order by mediaId desc");
                query.setParameter("category_id", tablePage.getCategory_id());

            }

            query.setParameter("user_id", tablePage.getUser_id());
            query.setParameter("media_state", tablePage.getMedia_state());

            query.setFirstResult(tablePage.getBegin());
            query.setMaxResults(tablePage.getPageSize());

            return query.list();
        }
    }

    @Override
    public int countByUser_idAndCategory_idAndMedia_state(Long user_id, Integer category_id, String media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = null;

            // 判断是否有category_id参数
            if (category_id == null) {
                query = session.createQuery(
                        "select count(*) from MediaDataEntity where userId = :user_id  and mediaState = :media_state");
            } else {
                query = session.createQuery(
                        "select count(*) from MediaDataEntity where userId = :user_id and mediaState = :media_state and categoryId = :category_id");
                query.setParameter("category_id", category_id);
            }

            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);

            return ((Long) query.uniqueResult()).intValue();
        }
    }

    @Override
    public int countByUser_idAndCategory_idAndMedia_state(Long user_id, Integer category_id, Date beginTime, Date endTime, String media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = null;

            if (category_id == null) {
                query = session.createQuery(
                        "select count(*) from MediaDataEntity where userId = :user_id  and mediaState = :media_state and" +
                                " overTime between :beginTime and :endTime");

            } else {
                query = session.createQuery(
                        "select count(*) from MediaDataEntity where" +
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
    public MediaDataEntity findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(Long user_id, int category_id, String media_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "from MediaDataEntity where userId = :user_id  and mediaState = :media_state and categoryId = :category_id order by createTime");

            query.setParameter("category_id", category_id);
            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);
            query.setMaxResults(1);

            return (MediaDataEntity) query.uniqueResult();
        }
    }

    @Override
    public MediaDataEntity findByMedia_id(Long media_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaDataEntity where mediaId = :media_id");

            query.setParameter("media_id", media_id);

            return (MediaDataEntity) query.uniqueResult();
        }
    }

    @Override
    public List<MediaDataEntity> findByMedia_ids(List<Long> media_ids) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaDataEntity where mediaId in (:ids)");

            query.setParameterList("ids", media_ids);

            return query.list();
        }

    }

    @Override
    public boolean updateMediaList(List<MediaDataEntity> mediaDataList) {

        boolean flag = false;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {

            for (MediaDataEntity m : mediaDataList
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
    public List<MediaDataEntity> findByOver_timeAndState(Date over_time, String media_data_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaDataEntity where overTime < :over_time and mediaState = :media_data_state");

            query.setParameter("over_time", over_time);
            query.setParameter("media_data_state", media_data_state);

            return query.list();
        }

    }

    @Override
    public List<MediaDataEntity> findByUser_idAndState(Long user_id,String media_state){
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaDataEntity where userId = :user_id and mediaState = :media_state");

            query.setParameter("user_id", user_id);
            query.setParameter("media_state", media_state);

            return query.list();
        }

    }

}
