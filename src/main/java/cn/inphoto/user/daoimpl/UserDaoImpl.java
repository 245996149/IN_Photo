package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class UserDaoImpl extends SuperDao implements UserDao {

    @Override
    public UsersEntity searchByUser_name(String user_name) {

        Session session = sessionFactory.openSession();

        Query query = session.createQuery("from UsersEntity where userName=?");

        query.setParameter(0, user_name);

        UsersEntity usersEntity = (UsersEntity) query.uniqueResult();

        session.close();

        return usersEntity;
    }

    @Override
    public UsersEntity searchByUser_id(int user_id) {
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("from UsersEntity where userId=?");

        query.setParameter(0, user_id);

        UsersEntity usersEntity = (UsersEntity) query.uniqueResult();

        session.close();

        return usersEntity;
    }

    @Override
    public boolean addUser(UsersEntity usersEntity) {

        boolean flag = false;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(usersEntity);
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return flag;
    }

}
