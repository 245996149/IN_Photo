package cn.inphoto.daoimpl;

import cn.inphoto.dao.SuperDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

            return session.get(User.class, user_id);
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

            User user = new User();

            user.setAdminId(userPage.getAdminId());
            user.setUserId(userPage.getUser_id());
            user.setUserName(userPage.getUserName());
            user.setPhone(userPage.getPhone());
            user.setEmail(userPage.getEmail());
            user.setUserState(userPage.getUserState());

            Example example = Example.create(user);

            DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                    .add(example)
                    .addOrder(Order.asc("userId"))
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

            Criteria cri = criteria.getExecutableCriteria(session);

            cri.setFirstResult(userPage.getBegin());
            cri.setMaxResults(userPage.getPageSize());

            return cri.list();
        }

    }

    @Override
    public int countByPage(UserPage userPage) {

        try (Session session = sessionFactory.openSession()) {

            User user = new User();

            user.setAdminId(userPage.getAdminId());
            user.setUserId(userPage.getUser_id());
            user.setUserName(userPage.getUserName());
            user.setPhone(userPage.getPhone());
            user.setEmail(userPage.getEmail());
            user.setUserState(userPage.getUserState());

            Example example = Example.create(user);

            DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                    .add(example)
                    .addOrder(Order.asc("userId"))
                    .setProjection(Projections.rowCount())
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

            return ((Long) criteria.getExecutableCriteria(session).uniqueResult()).intValue();
        }

    }
}
