package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.WebinfoDao;
import cn.inphoto.user.dbentity.CodeWebinfoEntity;
import cn.inphoto.user.dbentity.PicWebinfoEntity;
import cn.inphoto.user.dbentity.ShareInfoEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kaxia on 2017/6/13.
 */
@Repository
public class WebinfoDaoImpl extends SuperDao implements WebinfoDao {

    @Override
    public CodeWebinfoEntity findCodeByUser_idAndCategory_id(Long user_id, Integer category_id, String code_web_info_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CodeWebinfoEntity where userId = :user_id and categoryId = :category_id and " +
                    "codeWebinfoState = :code_web_info_state");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("code_web_info_state", code_web_info_state);

            return (CodeWebinfoEntity) query.uniqueResult();
        }
    }

    @Override
    public PicWebinfoEntity findPicByUser_idAndCategory_id(Long user_id, Integer category_id, String pic_web_info_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from PicWebinfoEntity where userId = :user_id and categoryId = :category_id and" +
                    " picWebinfoState = :pic_web_info_state");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("pic_web_info_state", pic_web_info_state);

            return (PicWebinfoEntity) query.uniqueResult();
        }
    }

    @Override
    public PicWebinfoEntity findPicByPic_id(Long pic_web_info_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from PicWebinfoEntity where picWebinfoId = :pic_web_info_id ");

            query.setParameter("pic_web_info_id", pic_web_info_id);

            return (PicWebinfoEntity) query.uniqueResult();
        }
    }

    @Override
    public CodeWebinfoEntity findCodeByCode_id(Long code_web_info_id) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CodeWebinfoEntity where codeWebinfoId= :code_web_info_id ");

            query.setParameter("code_web_info_id", code_web_info_id);

            return (CodeWebinfoEntity) query.uniqueResult();
        }
    }

    @Override
    public ShareInfoEntity findShareByShare_id(Long share_info_id) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from ShareInfoEntity where shareInfoId = :share_info_id ");

            query.setParameter("share_info_id", share_info_id);

            return (ShareInfoEntity) query.uniqueResult();
        }
    }
}
