package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dbentity.ShareDataEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

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
                    "select count(*) from ShareDataEntity where userId = ?  and shareType = ? and shareTime between ? and ?");

            query.setParameter(0, user_id);
            query.setParameter(1, share_type);
            query.setParameter(2, beginTime);
            query.setParameter(3, endTime);

            return ((Long) query.uniqueResult()).intValue();

        }
    }

    @Override
    public int countByTime(Long user_id, int category_id, Date beginTime, Date endTime, String share_type) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(
                    "select count(*) from ShareDataEntity where userId = ? and categoryId = ? and shareType = ? and shareTime between ? and ?");

            query.setParameter(0, user_id);
            query.setParameter(1, category_id);
            query.setParameter(2, share_type);
            query.setParameter(3, beginTime);
            query.setParameter(4, endTime);

            return ((Long) query.uniqueResult()).intValue();

        }
    }

}
