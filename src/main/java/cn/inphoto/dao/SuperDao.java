package cn.inphoto.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * sessionFactory父类
 * Created by kaxia on 2017/6/5.
 */
@Component
public class SuperDao {

    @Resource
    public SessionFactory sessionFactory;

}
