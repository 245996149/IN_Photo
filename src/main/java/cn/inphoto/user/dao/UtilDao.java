package cn.inphoto.user.dao;

/**
 * 数据库通用操作Dao接口
 * Created by kaxia on 2017/6/13.
 */
public interface UtilDao {

    /**
     * 向数据库中新增一个对象
     *
     * @param obj 对象
     * @return 返回是否成功
     */
    boolean save(Object obj);

    /**
     * 向数据库中更新一个对象
     *
     * @param obj 对象
     * @return 返回是否成功
     */
    boolean update(Object obj);

}
