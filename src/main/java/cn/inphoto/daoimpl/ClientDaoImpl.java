package cn.inphoto.daoimpl;

import cn.inphoto.dao.ClientDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.RoleInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDaoImpl extends SuperDao implements ClientDao {


    @Override
    public List<RoleInfo> findRoleByAdminId(int admin_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from RoleInfo where roleId in " +
                    "(select ar.roleId from AdminRole ar where ar.adminId = :admin_id)");

            query.setParameter("admin_id", admin_id);

            return query.list();

        }

    }
}
