package cn.inphoto.daoimpl;

import cn.inphoto.dao.SuperDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.user.UsersEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class UserDaoImpl extends SuperDao implements UserDao {

    Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public UsersEntity findByUser_name(String user_name) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from UsersEntity where userName= :user_name");

            query.setParameter("user_name", user_name);

            return (UsersEntity) query.uniqueResult();
        }
    }

    @Override
    public UsersEntity findByUser_id(Long user_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from UsersEntity where userId= :user_id");

            query.setParameter("user_id", user_id);

            return (UsersEntity) query.uniqueResult();
        }
    }

    @Override
    public List<UsersEntity> findAll() {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from UsersEntity");

            return query.list();
        }

    }


}
