package cn.inphoto.daoimpl;

import cn.inphoto.dao.ShareDataDao;
import cn.inphoto.dao.SuperDao;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class ShareDataDaoImpl extends SuperDao implements ShareDataDao {

    Logger logger = Logger.getLogger(ShareDataDaoImpl.class);

    @Override
    public int countByTimeTotal(Long user_id, Date beginTime, Date endTime, String share_type) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "select count(*) from ShareDataEntity where userId = :user_id  and shareType = :share_type and " +
                            "shareTime between :beginTime and :endTime");

            query.setParameter("user_id", user_id);
            query.setParameter("share_type", share_type);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);

            return ((Long) query.uniqueResult()).intValue();

        }
    }

    @Override
    public int countByTime(Long user_id, int category_id, Date beginTime, Date endTime, String share_type) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "select count(*) from ShareDataEntity where userId = :user_id and categoryId = :category_id and shareType = :share_type and " +
                            "shareTime between :beginTime and :endTime");

            query.setParameter("category_id", category_id);
            query.setParameter("user_id", user_id);
            query.setParameter("share_type", share_type);
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);

            return ((Long) query.uniqueResult()).intValue();

        }
    }

}
