package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.CategoryDao;
import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dbentity.CategoryEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class CategoryDaoImpl extends SuperDao implements CategoryDao {

    @Override
    public CategoryEntity findByCategory_code(String category_code) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CategoryEntity where categoryCode = ?");

            query.setParameter(0, category_code);

            return (CategoryEntity) query.uniqueResult();
        }
    }
}
