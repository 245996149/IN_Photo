package cn.inphoto.user.daoimpl;

import cn.inphoto.user.dao.SuperDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dbentity.UserCategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

/**
 * Created by kaxia on 2017/6/12.
 */
@Repository
public class UserCategoryDaoImpl extends SuperDao implements UserCategoryDao {

    Logger logger = Logger.getLogger(UserCategoryDaoImpl.class);

    @Override
    public UserCategoryEntity findByUser_idAndCategory_id(Long user_id, Integer category_id, String user_category_state) {
        Session session = sessionFactory.openSession();

        Query query = session.createQuery(" from UserCategoryEntity where userId = ? and categoryId = ?  and userCategoryState = ?");

        query.setParameter(0, user_id);
        query.setParameter(1, category_id);
        query.setParameter(2, user_category_state);

        return (UserCategoryEntity) query.uniqueResult();
    }


}
