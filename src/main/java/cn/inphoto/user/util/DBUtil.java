package cn.inphoto.user.util;

import cn.inphoto.user.dao.MediaCodeDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.MediaCodeEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kaxia on 2017/6/12.
 */
public class DBUtil {

    public static boolean judgeMediaCode(MediaCodeEntity mediaCodeEntity) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        MediaCodeDao mediaCodeDao = ctx.getBean(MediaCodeDao.class);
        MediaCodeEntity mediaCode = mediaCodeDao.findByUser_idAndCategory_idAndMedia_code(
                mediaCodeEntity.getUserId(), mediaCodeEntity.getCategoryId(), mediaCodeEntity.getMediaCode());

        if (mediaCode == null) {
            return mediaCodeDao.saveMediaCode(mediaCodeEntity);
        } else {
            mediaCode.setMediaId(mediaCodeEntity.getMediaId());
            System.out.println(mediaCode.toString());
            return mediaCodeDao.updateMediaCode(mediaCode);
        }

    }

}
