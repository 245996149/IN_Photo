package cn.inphoto.daoimpl;

import cn.inphoto.dao.SuperDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.user.CodeWebInfo;
import cn.inphoto.dbentity.user.PicWebinfo;
import cn.inphoto.dbentity.user.ShareInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kaxia on 2017/6/13.
 */
@Repository
public class WebinfoDaoImpl extends SuperDao implements WebinfoDao {

    @Override
    public CodeWebInfo findCodeByUser_idAndCategory_id(Long user_id, Integer category_id, String code_web_info_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CodeWebInfo where userId = :user_id and categoryId = :category_id and " +
                    "codeWebinfoState = :code_web_info_state");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("code_web_info_state", code_web_info_state);

            return (CodeWebInfo) query.uniqueResult();
        }
    }

    @Override
    public PicWebinfo findPicByUser_idAndCategory_id(Long user_id, Integer category_id, String pic_web_info_state) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from PicWebinfo where userId = :user_id and categoryId = :category_id and" +
                    " picWebinfoState = :pic_web_info_state");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);
            query.setParameter("pic_web_info_state", pic_web_info_state);

            return (PicWebinfo) query.uniqueResult();
        }
    }

    @Override
    public PicWebinfo findPicByPic_id(Long pic_web_info_id) {

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from PicWebinfo where picWebinfoId = :pic_web_info_id ");

            query.setParameter("pic_web_info_id", pic_web_info_id);

            return (PicWebinfo) query.uniqueResult();
        }
    }

    @Override
    public CodeWebInfo findCodeByCode_id(Long code_web_info_id) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from CodeWebInfo where codeWebinfoId= :code_web_info_id ");

            query.setParameter("code_web_info_id", code_web_info_id);

            return (CodeWebInfo) query.uniqueResult();
        }
    }

    @Override
    public ShareInfo findShareByShare_id(Long share_info_id) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from ShareInfo where shareInfoId = :share_info_id ");

            query.setParameter("share_info_id", share_info_id);

            return (ShareInfo) query.uniqueResult();
        }
    }

    @Override
    public ShareInfo findShareByUser_idAndCategory(Long user_id, Integer category_id) {
        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("from ShareInfo where userId = :user_id and categoryId = :category_id");

            query.setParameter("user_id", user_id);
            query.setParameter("category_id", category_id);

            return (ShareInfo) query.uniqueResult();
        }
    }
}
