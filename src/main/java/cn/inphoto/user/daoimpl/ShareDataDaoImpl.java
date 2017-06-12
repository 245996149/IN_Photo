package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dbentity.ShareDataEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class ShareDataDaoImpl extends SuperDao implements ShareDataDao {


    @Override
    public int countByTime(String beginTime, String endTime, String share_type) {

        Session session = sessionFactory.openSession();

        Query query = session.createQuery("select count(*) from ShareDataEntity where shareType = ? and shareTime between ? and ?");

        query.setParameter(0, share_type);
        query.setParameter(1, beginTime);
        query.setParameter(2, endTime);

        return (int) query.uniqueResult();

    }

    @Override
    public boolean addShareData(ShareDataEntity shareDataEntity) {

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        boolean flag = false;

        try {
            session.save(shareDataEntity);
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return flag;
    }
}
