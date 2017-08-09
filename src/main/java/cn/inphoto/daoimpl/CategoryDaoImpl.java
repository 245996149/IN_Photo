package cn.inphoto.daoimpl;

import cn.inphoto.dao.CategoryDao;
import cn.inphoto.dao.SuperDao;
import cn.inphoto.dbentity.user.Category;
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
    public Category findByCategory_code(String category_code) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from Category where categoryCode = :category_code");

            query.setParameter("category_code", category_code);

            return (Category) query.uniqueResult();
        }
    }

    @Override
    public Category findByCategory_id(int category_id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(Category.class, category_id);
        }

    }

    @Override
    public List<Category> findAll() {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from Category");

            return query.list();
        }

    }
}
