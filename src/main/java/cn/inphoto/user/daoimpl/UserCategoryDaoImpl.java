package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.UserCategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

/**
 * Created by kaxia on 2017/6/12.
 */
@Repository
public class UserCategoryDaoImpl extends SuperDao implements UserCategoryDao {

    Logger logger = Logger.getLogger(UserCategoryDaoImpl.class);

    @Override
    public UserCategoryEntity findByUser_idAndCategory_id(Long user_id, Integer category_id, String user_category_state) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(" from UserCategoryEntity where userId = :user_id and categoryId = :category_id  " +
                    "and userCategoryState = :user_category_state");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("user_category_state", user_category_state);

            return (UserCategoryEntity) query.uniqueResult();
        }

    }

    @Override
    public List<UserCategoryEntity> findByUser_idAndState(Long user_id, String user_category_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(" from UserCategoryEntity where userId = :user_id and userCategoryState = :user_category_state");

            query.setParameter("user_id", user_id);
            query.setParameter("user_category_state", user_category_state);

            return query.list();
        }

    }

    @Override
    public List<UserCategoryEntity> findByUser_id(Long user_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(" from UserCategoryEntity where userId = :user_id order by endTime desc ");

            query.setParameter("user_id", user_id);

            return query.list();
        }

    }

    @Override
    public List<UserCategoryEntity> findByOverTimeByNormal(Date over_time, String user_category_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(" from UserCategoryEntity where endTime < :over_time and userCategoryState = :user_category_state");

            query.setParameter("over_time", over_time);
            query.setParameter("user_category_state", user_category_state);

            return query.list();
        }

    }

    @Override
    public List<UserCategoryEntity> findByNotStartBy(Date begin_time, String user_category_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(" from UserCategoryEntity where beginTime > :begin_time and userCategoryState = :user_category_state");

            query.setParameter("begin_time", begin_time);
            query.setParameter("user_category_state", user_category_state);

            return query.list();
        }

    }

    @Override
    public List<UserCategoryEntity> findByState(String user_category_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery(" from UserCategoryEntity where userCategoryState = :user_category_state");

            query.setParameter("user_category_state", user_category_state);

            return query.list();
        }

    }

    @Override
    public boolean updateList(List<UserCategoryEntity> userCategoryList) {

        boolean flag = false;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {

            for (UserCategoryEntity uc : userCategoryList
                    ) {
                session.update(uc);
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
}
