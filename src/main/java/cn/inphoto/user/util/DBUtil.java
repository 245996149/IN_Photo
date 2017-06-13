package cn.inphoto.user.util;

import cn.inphoto.user.dao.MediaCodeDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dao.UtilDao;
import cn.inphoto.user.dbentity.MediaCodeEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kaxia on 2017/6/12.
 */
public class DBUtil {

    /**
     * 判断媒体验证码是否存在，存在则更新，不存在则创建
     *
     * @param mediaCodeEntity 媒体验证码
     * @return 更新、创建是否成功
     */
    public static boolean judgeMediaCode(MediaCodeEntity mediaCodeEntity) {

        // 读取配置
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        MediaCodeDao mediaCodeDao = ctx.getBean(MediaCodeDao.class);
        UtilDao utilDao = ctx.getBean(UtilDao.class);

        // 查询媒体验证码对象
        MediaCodeEntity mediaCode = mediaCodeDao.findByUser_idAndCategory_idAndMedia_code(
                mediaCodeEntity.getUserId(), mediaCodeEntity.getCategoryId(), mediaCodeEntity.getMediaCode());

        // 判断媒体验证码对象是否有效
        if (mediaCode == null) {
            // 无效，新增一个媒体验证码对象
            return utilDao.save(mediaCodeEntity);
        } else {
            // 有效，给媒体验证码对象赋予新的值，并更新数据库
            mediaCode.setMediaId(mediaCodeEntity.getMediaId());
            return utilDao.update(mediaCode);
        }

    }

}
