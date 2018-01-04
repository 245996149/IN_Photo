package cn.inphoto.daoimpl;

import cn.inphoto.dao.ShareClickDataDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.user.ShareClickData;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 17-7-11.
 */
@Repository
public class ShareClickDataDaoImpl extends SuperDao implements ShareClickDataDao {


    @Override
    public int countByTimeTotal(Long user_id, Date beginTime, Date endTime) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "select count(*) from ShareClickData where userId = :user_id and " +
                            "clickTime between :beginTime and :endTime");

            query.setParameter("user_id", user_id);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);

            return ((Long) query.uniqueResult()).intValue();

        }
    }

    @Override
    public int countByTime(Long user_id, Integer category_id, Date beginTime, Date endTime) {
        try (Session session = sessionFactory.openSession()) {

            Query query;
            if (category_id == null || category_id == 0) {
                query = session.createQuery(
                        "select count(*) from ShareClickData where userId = :user_id and " +
                                "clickTime between :beginTime and :endTime");
            } else {
                query = session.createQuery(
                        "select count(*) from ShareClickData where userId = :user_id and categoryId = :category_id and " +
                                "clickTime between :beginTime and :endTime");
                query.setParameter("category_id", category_id);
            }

            query.setParameter("user_id", user_id);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);

            return ((Long) query.uniqueResult()).intValue();

        }
    }

    @Override
    public List<ShareClickData> findByTime(Long user_id, Integer category_id, Date beginTime, Date endTime) {
        try (Session session = sessionFactory.openSession()) {

            Query query;
            if (category_id == null || category_id == 0) {
                query = session.createQuery(
                        "from ShareClickData where userId = :user_id and " +
                                "clickTime between :beginTime and :endTime");
            } else {
                query = session.createQuery(
                        "from ShareClickData where userId = :user_id and categoryId = :category_id and " +
                                "clickTime between :beginTime and :endTime");
                query.setParameter("category_id", category_id);
            }

            query.setParameter("user_id", user_id);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);

            return query.list();

        }
    }
}
