package cn.inphoto.daoimpl;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.user.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 17-7-11.
 */
@Repository
public class AdminDaoImpl extends SuperDao implements AdminDao {

    @Override
    public AdminInfo findByAdmin_name(String admin_name) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from AdminInfo where adminName= :admin_name");

            query.setParameter("admin_name", admin_name);

            return (AdminInfo) query.uniqueResult();
        }
    }

    @Override
    public AdminInfo findByPhone(String Phone) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from AdminInfo where phone= :Phone");

            query.setParameter("Phone", Phone);

            return (AdminInfo) query.uniqueResult();
        }
    }

    @Override
    public AdminInfo findByEmail(String Email) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from AdminInfo where email= :Email");

            query.setParameter("Email", Email);

            return (AdminInfo) query.uniqueResult();
        }
    }

}
