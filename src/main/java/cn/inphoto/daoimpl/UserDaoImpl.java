package cn.inphoto.daoimpl;

import cn.inphoto.dao.SuperDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.User;
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
    public User findByUser_name(String user_name) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from User where userName= :user_name");

            query.setParameter("user_name", user_name);

            return (User) query.uniqueResult();
        }
    }

    @Override
    public User findByEmail(String email) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from User where email= :email");

            query.setParameter("email", email);

            return (User) query.uniqueResult();
        }

    }

    @Override
    public User findByUser_id(Long user_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from User where userId= :user_id");

            query.setParameter("user_id", user_id);

            return (User) query.uniqueResult();
        }
    }

    @Override
    public List<User> findAll() {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from User");

            return query.list();
        }

    }

    @Override
    public List<User> findByPage(UserPage userPage) {

        try (Session session = sessionFactory.openSession()) {

            Query query;

            String hql = "from User ";

            if (userPage.getAdminId() != 0) {
                hql = hql + " where adminId = " + userPage.getAdminId();
            } else {
                hql = hql + " where adminId != " + userPage.getAdminId();
            }

            if (userPage.getUser_id() != null && userPage.getUser_id() != 0) {
                hql = hql + " and userId = " + userPage.getUser_id();
            }

            if (userPage.getEmail() != null) {
                hql = hql + " and email = \'" + userPage.getEmail() + "\'";
            }

            if (userPage.getPhone() != null) {
                hql = hql + " and phone = \'" + userPage.getPhone() + "\'";
            }

            if (userPage.getUserName() != null) {
                hql = hql + " and userName = \'" + userPage.getUserName() + "\'";
            }

            if (userPage.getUserState() != null) {
                hql = hql + " and userState = \'" + userPage.getUserState() + "\'";
            }

            hql = hql + " order by userId";

            System.out.println(hql);

            query = session.createQuery(hql);

            query.setFirstResult(userPage.getBegin());
            query.setMaxResults(userPage.getPageSize());

            return query.list();
        }

    }

    @Override
    public int countByPage(UserPage userPage) {

        try (Session session = sessionFactory.openSession()) {

            Query query;

            String hql = "select count(*) from User";

            if (userPage.getAdminId() == 0) {
                hql = hql + " where adminId != " + userPage.getAdminId();
            } else {
                hql = hql + " where adminId = " + userPage.getAdminId();
            }

            if (userPage.getUser_id() != null) {
                hql = hql + " and userId = " + userPage.getUser_id();
            }

            if (userPage.getEmail() != null) {
                hql = hql + " and email = \'" + userPage.getEmail() + "\'";
            }

            if (userPage.getPhone() != null) {
                hql = hql + " and phone = \'" + userPage.getPhone() + "\'";
            }

            if (userPage.getUserName() != null) {
                hql = hql + " and userName = \'" + userPage.getUserName() + "\'";
            }

            if (userPage.getUserState() != null) {
                hql = hql + " and userState = \'" + userPage.getUserState() + "\'";
            }

            hql = hql + " order by userId";

            System.out.println(hql);

            query = session.createQuery(hql);

            return ((Long) query.uniqueResult()).intValue();
        }

    }
}
