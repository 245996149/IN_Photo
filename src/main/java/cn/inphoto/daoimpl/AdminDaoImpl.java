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
    public List<ModuleInfo> findModulesByAdmin(int admin_id) {
        try (Session session = sessionFactory.openSession()) {

//            String hql = "select * from module_info where module_id in (" +
//                    "select rm.module_id " +
//                    " from admin_role ar" +
//                    " inner join role_info ri on ri.role_id=ar.role_id " +
//                    " inner join role_module rm on rm.role_id=ri.role_id" +
//                    " where ar.admin_id=#{adminId}" +
//                    ") order by module_id";

            Query query = session.createQuery("from ModuleInfo where moduleId in " +
                    "(select rm.moduleId from AdminRole ar " +
                    "inner join RoleInfo re on re.roleId = ar.roleId " +
                    "inner join RoleModule rm on rm.roleId = re.roleId " +
                    "where ar.adminId = :admin_id) order by moduleId");

            query.setParameter("admin_id", admin_id);

            return query.list();

        }
    }

    @Override
    public List<Category> findCategoryByAdmin(int admin_id) {

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Category where categoryId in " +
                    "(select ac.categoryId from AdminCategory ac where " +
                    "adminId = :admin_id) order by categoryId");

            query.setParameter("admin_id", admin_id);

            return query.list();

        }

    }

}
