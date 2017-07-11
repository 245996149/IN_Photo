package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.MediaCodeDao;
import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.dbentity.MediaCodeEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class MediaCodeDaoImpl extends SuperDao implements MediaCodeDao {


    @Override
    public MediaCodeEntity findByUser_idAndCategory_idAndMedia_code(Long user_id, Integer category_id, String media_code) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaCodeEntity where userId = :user_id and categoryId = :category_id and mediaCode = :media_code");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("media_code", media_code);

            return (MediaCodeEntity) query.uniqueResult();
        }
    }

    @Override
    public List<MediaCodeEntity> findByUser_idAndCategory_id(Long user_id, Integer category_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from MediaCodeEntity where userId = :user_id and categoryId = :category_id ");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);

            return query.list();
        }

    }
}
