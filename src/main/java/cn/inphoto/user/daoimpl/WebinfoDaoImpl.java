package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.WebinfoDao;
import cn.inphoto.user.dbentity.CodeWebinfoEntity;
import cn.inphoto.user.dbentity.PicWebinfoEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kaxia on 2017/6/13.
 */
@Repository
public class WebinfoDaoImpl extends SuperDao implements WebinfoDao {

    @Override
    public CodeWebinfoEntity findCodeByUser_idAndCategory_id(Long user_id, Integer category_id,String code_web_info_state) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from CodeWebinfoEntity where userId = ? and categoryId = ? and codeWebinfoState = ?");

        query.setParameter(0, user_id);
        query.setParameter(1, category_id);
        query.setParameter(2, code_web_info_state);

        return (CodeWebinfoEntity) query.uniqueResult();
    }

    @Override
    public PicWebinfoEntity findPicByUser_idAndCategory_id(Long user_id, Integer category_id, String pic_web_info_state) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from PicWebinfoEntity where userId = ? and categoryId = ? and picWebinfoState = ?");

        query.setParameter(0, user_id);
        query.setParameter(1, category_id);
        query.setParameter(2, pic_web_info_state);

        return (PicWebinfoEntity) query.uniqueResult();
    }
}
