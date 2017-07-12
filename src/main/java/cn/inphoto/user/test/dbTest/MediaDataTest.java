package cn.inphoto.user.test.dbTest;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dbentity.user.MediaDataEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaxia on 2017/6/12.
 */
public class MediaDataTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    MediaDataDao mediaDataDao = ctx.getBean(MediaDataDao.class);

    @Test
    public void addMediaData() {

        List<Long> media_ids = new ArrayList<>();

        media_ids.add(1L);
        media_ids.add(2L);

        List<MediaDataEntity> mediaDataEntities = mediaDataDao.findByMedia_ids(media_ids);

        for (MediaDataEntity m : mediaDataEntities
                ) {
            System.out.println(m.toString());
        }

    }

}
