package cn.inphoto.daoimpl;

import cn.inphoto.dao.RoleDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.RoleInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setRoleId(role_id);
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
}
