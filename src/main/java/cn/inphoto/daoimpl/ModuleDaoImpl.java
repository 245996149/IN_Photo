package cn.inphoto.daoimpl;

import cn.inphoto.dao.ModuleDao;
import cn.inphoto.dao.RoleDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleDaoImpl extends SuperDao implements ModuleDao {


    @Override
    public List<ModuleInfo> findAll() {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from ModuleInfo");

            return query.list();

        }
    }

    @Override
    public List<ModuleInfo> findByModuleIds(List<Integer> moduleIds) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from ModuleInfo where moduleId in (:moduleIds)");

            query.setParameter("moduleIds", moduleIds);

            return query.list();

        }
    }
}
