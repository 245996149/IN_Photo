package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.UsersEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class UserDaoImpl extends SuperDao implements UserDao {

    Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public UsersEntity findByUser_name(String user_name) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from UsersEntity where userName=?");

        query.setParameter(0, user_name);

        return (UsersEntity) query.uniqueResult();
    }

    @Override
    public UsersEntity findByUser_id(Long user_id) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from UsersEntity where userId=?");

        query.setParameter(0, user_id);

        return (UsersEntity) query.uniqueResult();
    }


}
