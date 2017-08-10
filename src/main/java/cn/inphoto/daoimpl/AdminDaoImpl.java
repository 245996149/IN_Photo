package cn.inphoto.daoimpl;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.AdminPage;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 17-7-11.
 */
@Repository
public class AdminDaoImpl extends SuperDao implements AdminDao {

    @Override
    public AdminInfo findByAdmin_id(int admin_id) {
        try (Session session = sessionFactory.openSession()) {

            return session.get(AdminInfo.class, admin_id);

        }
    }

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

    @Override
    public List<AdminInfo> findByPage(AdminPage adminPage) {

        try (Session session = sessionFactory.openSession()) {

            Criteria c = DetachedCriteria.forClass(AdminInfo.class)
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                    .getExecutableCriteria(session)
                    .addOrder(Order.asc("adminId"))
                    .setFetchMode("categorySet", FetchMode.SELECT)
                    .setFetchMode("roleInfoSet", FetchMode.SELECT);

            if (adminPage.getAdminId() != 0) {
                c.add(Restrictions.eq("adminId", adminPage.getAdminId()));
            }

            if (adminPage.getAdminName() != null && "".equals(adminPage.getAdminName())) {
                c.add(Restrictions.eq("adminName", adminPage.getAdminName()));
            }

            if (adminPage.getEmail() != null && "".equals(adminPage.getEmail())) {
                c.add(Restrictions.eq("email", adminPage.getEmail()));
            }

            if (adminPage.getPhone() != null && "".equals(adminPage.getPhone())) {
                c.add(Restrictions.eq("phone", adminPage.getPhone()));
            }

            if (adminPage.getAdminStatu() != null && "".equals(adminPage.getAdminStatu())) {
                c.add(Restrictions.eq("adminState", adminPage.getAdminStatu()));
            }

            c.setFirstResult(adminPage.getBegin());
            c.setMaxResults(adminPage.getPageSize());

            return c.list();
        }
    }

    @Override
    public int countByPage(AdminPage adminPage) {
        try (Session session = sessionFactory.openSession()) {

            Criteria c = DetachedCriteria.forClass(AdminInfo.class)
                    .addOrder(Order.asc("adminId"))
                    .setProjection(Projections.rowCount())
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                    .getExecutableCriteria(session);

            if (adminPage.getAdminId() != 0) {
                c.add(Restrictions.eq("adminId", adminPage.getAdminId()));
            }

            if (adminPage.getAdminName() != null && "".equals(adminPage.getAdminName())) {
                c.add(Restrictions.eq("adminName", adminPage.getAdminName()));
            }

            if (adminPage.getEmail() != null && "".equals(adminPage.getEmail())) {
                c.add(Restrictions.eq("email", adminPage.getEmail()));
            }

            if (adminPage.getPhone() != null && "".equals(adminPage.getPhone())) {
                c.add(Restrictions.eq("phone", adminPage.getPhone()));
            }

            if (adminPage.getAdminStatu() != null && "".equals(adminPage.getAdminStatu())) {
                c.add(Restrictions.eq("adminState", adminPage.getAdminStatu()));
            }

            return ((Long) c.uniqueResult()).intValue();
        }
    }

}
