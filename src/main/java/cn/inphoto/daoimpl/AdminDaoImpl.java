package cn.inphoto.daoimpl;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.dbentity.page.AdminPage;
import cn.inphoto.dbentity.user.Category;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 17-7-11.
 */
@Repository
public class AdminDaoImpl extends SuperDao implements AdminDao {

    @Override
    public List<AdminInfo> findAll() {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from AdminInfo");

            return query.list();
        }
    }

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

    @Override
    public boolean updateAdmin(AdminInfo adminInfo) {
        boolean flag;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {

            // 加载session中的RoleInfo
            AdminInfo adminInfo1 = session.get(AdminInfo.class, adminInfo.getAdminId());

            adminInfo1.setAdminStatu(adminInfo.getAdminStatu());

            Set<Category> categorySet = adminInfo.getCategorySet();

            List<Category> categoryList = new ArrayList<>();

            for (Category c : categorySet
                    ) {
                // 加载session中的ModuleInfo
                c = session.get(Category.class, c.getCategoryId());
                categoryList.add(c);
            }

            // 将session中的ModuleInfo赋予session中的RoleInfo
            adminInfo1.setCategorySet(new HashSet<>(categoryList));

            Set<RoleInfo> roleInfoSet = adminInfo.getRoleInfoSet();

            List<RoleInfo> roleInfoList = new ArrayList<>();

            for (RoleInfo r : roleInfoSet
                    ) {
                // 加载session中的ModuleInfo
                r = session.get(RoleInfo.class, r.getRoleId());
                roleInfoList.add(r);
            }

            // 将session中的ModuleInfo赋予session中的RoleInfo
            adminInfo1.setRoleInfoSet(new HashSet<>(roleInfoList));

            session.merge(adminInfo1);
            transaction.commit();
            flag = true;

        } catch (Exception e) {

            e.printStackTrace();
            flag = false;
//            logger.info(getErrorInfoFromException(e));
            transaction.rollback();

        } finally {

            session.close();

        }

        return flag;
    }

}
