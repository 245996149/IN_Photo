package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.CategoryDao;
import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dbentity.CategoryEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class CategoryDaoImpl extends SuperDao implements CategoryDao {

    @Override
    public CategoryEntity findByCategory_code(String category_code) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CategoryEntity where categoryCode = :category_code");

            query.setParameter("category_code", category_code);

            return (CategoryEntity) query.uniqueResult();
        }
    }

    @Override
    public List<CategoryEntity> findAll() {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CategoryEntity");

            return query.list();
        }

    }
}
