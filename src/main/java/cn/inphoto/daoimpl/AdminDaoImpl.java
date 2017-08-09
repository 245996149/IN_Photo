package cn.inphoto.daoimpl;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.AdminPage;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

    @Override
    public List<AdminInfo> findByPage(AdminPage adminPage) {

        try (Session session = sessionFactory.openSession()) {

            AdminInfo adminInfo = new AdminInfo();

            adminInfo.setAdminId(adminPage.getAdminId());
            adminInfo.setAdminName(adminPage.getAdminName());
            adminInfo.setEmail(adminPage.getEmail());
            adminInfo.setPhone(adminPage.getPhone());
            adminInfo.setAdminStatu(adminPage.getAdminStatu());

            Criteria c = DetachedCriteria.forClass(AdminInfo.class)
                    .add(Example.create(adminInfo))
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                    .getExecutableCriteria(session)
                    .addOrder(Order.asc("adminId"))
                    .setFetchMode("categorySet", FetchMode.SELECT)
                    .setFetchMode("roleInfoSet",FetchMode.SELECT);

            c.setFirstResult(adminPage.getBegin());
            c.setMaxResults(adminPage.getPageSize());

            return c.list();
        }
    }

    @Override
    public int countByPage(AdminPage adminPage) {
        try (Session session = sessionFactory.openSession()) {

            AdminInfo adminInfo = new AdminInfo();

            adminInfo.setAdminId(adminPage.getAdminId());
            adminInfo.setAdminName(adminPage.getAdminName());
            adminInfo.setEmail(adminPage.getEmail());
            adminInfo.setPhone(adminPage.getPhone());
            adminInfo.setAdminStatu(adminPage.getAdminStatu());

            Criteria c = DetachedCriteria.forClass(AdminInfo.class)
                    .add(Example.create(adminInfo))
                    .addOrder(Order.asc("adminId"))
                    .setProjection(Projections.rowCount())
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                    .getExecutableCriteria(session);

            return ((Long) c.uniqueResult()).intValue();
        }
    }

}
