package cn.inphoto.daoimpl;

import cn.inphoto.dao.RoleDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl extends SuperDao implements RoleDao {

    @Override
    public List<RoleInfo> findAllRole() {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from RoleInfo order by roleId");

            return query.list();

        }
    }

    @Override
    public boolean deleteRole(int role_id) {

        boolean flag;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {

            RoleInfo roleInfo = session.get(RoleInfo.class, role_id);
            session.delete(roleInfo);
            transaction.commit();
            flag = true;

        } catch (Exception e) {

            flag = false;
//            logger.info(getErrorInfoFromException(e));
            transaction.rollback();

        } finally {

            session.close();

        }

        return flag;
    }

    @Override
    public List<AdminInfo> findAdminByRole_id(int role_id) {
        try (Session session = sessionFactory.openSession()) {
            DetachedCriteria criteria = DetachedCriteria.forClass(AdminInfo.class).
                    createAlias("roleInfoSet", "r").
                    add(Restrictions.eq("r.roleId", role_id))
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
            return criteria.getExecutableCriteria(session).list();
        }
    }

    @Override
    public RoleInfo findByRole_id(int role_id) {
        try (Session session = sessionFactory.openSession()) {

            return session.get(RoleInfo.class, role_id);

        }
    }

    @Override
    public boolean updateRole(RoleInfo roleInfo) {

        boolean flag;

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {

            // 加载session中的RoleInfo
            RoleInfo roleInfo1 = session.get(RoleInfo.class, roleInfo.getRoleId());

            roleInfo1.setName(roleInfo.getName());

            Set<ModuleInfo> moduleInfoList = roleInfo.getModuleInfoSet();

            List<ModuleInfo> moduleInfoList2 = new ArrayList<>();

            for (ModuleInfo m : moduleInfoList
                    ) {
                // 加载session中的ModuleInfo
                m = session.get(ModuleInfo.class, m.getModuleId());
                moduleInfoList2.add(m);
            }

            // 将session中的ModuleInfo赋予session中的RoleInfo
            roleInfo1.setModuleInfoSet(new HashSet<>(moduleInfoList2));

            session.merge(roleInfo1);
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
