package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.MediaDataDao;
import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.page.TablePage;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

/**
 * Created by kaxia on 2017/6/5.
 */
@Repository
public class MediaDataDaoImpl extends SuperDao implements MediaDataDao {

    Logger logger = Logger.getLogger(MediaDataDaoImpl.class);

    @Override
    public List<MediaDataEntity> findByPage(TablePage tablePage) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(" from MediaDataEntity where userId = ? and categoryId = ? order by mediaId desc");

        query.setParameter(0, tablePage.getUser_id());
        query.setParameter(1, tablePage.getCategory_id());
        query.setFirstResult(tablePage.getBegin());
        query.setMaxResults(tablePage.getPageSize());

        return query.list();
    }


    @Override
    public long countByUser_idAndCategory_idAndMedia_state(Long user_id, int category_id, String media_state) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(
                "select count(*) from MediaDataEntity where userId = ? and categoryId = ? and mediaState = ?");

        query.setParameter(0, user_id);
        query.setParameter(1, category_id);
        query.setParameter(2, media_state);

        return (Long) query.uniqueResult();
    }

    @Override
    public MediaDataEntity findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(Long user_id, int category_id, String media_state) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(
                "from MediaDataEntity where userId = ? and categoryId = ? and mediaState = ? order by createTime");

        query.setParameter(0, user_id);
        query.setParameter(1, category_id);
        query.setParameter(2, media_state);
        query.setMaxResults(1);

        return (MediaDataEntity) query.uniqueResult();
    }


    @Override
    public MediaDataEntity findByMedia_id(Long media_id) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from MediaDataEntity where mediaId = ?");

        query.setParameter(0, media_id);

        return (MediaDataEntity) query.uniqueResult();
    }

}
