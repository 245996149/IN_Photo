package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.UtilDao;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

/**
 * Created by kaxia on 2017/6/13.
 */
@Repository
public class UtilDaoImpl extends SuperDao implements UtilDao {

    Logger logger = Logger.getLogger(UtilDaoImpl.class);

    @Override
    public boolean save(Object obj) {

        boolean flag = false;

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {

            session.save(obj);
            transaction.commit();
            flag = true;

        } catch (Exception e) {

            logger.info(getErrorInfoFromException(e));
            transaction.rollback();

        }

        return flag;

    }

    @Override
    public boolean update(Object obj) {

        boolean flag = false;

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {

            session.update(obj);
            transaction.commit();
            flag = true;

        } catch (Exception e) {

            logger.info(getErrorInfoFromException(e));
            transaction.rollback();

        }

        return flag;

    }
}
