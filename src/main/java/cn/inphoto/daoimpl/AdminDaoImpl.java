package cn.inphoto.daoimpl;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.admin.AdminEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 17-7-11.
 */
@Repository
public class AdminDaoImpl extends SuperDao implements AdminDao {

    @Override
    public AdminEntity findByAdmin_name(String admin_name) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from AdminEntity where adminName= :admin_name");

            query.setParameter("admin_name", admin_name);

            return (AdminEntity) query.uniqueResult();
        }
    }

}
