package cn.inphoto.user.controller;

import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dbentity.ShareDataEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaxia on 2017/6/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    ShareDataDao shareDataDao;

    @RequestMapping("/toCharsTest.do")
    public String toCharsTest() {

        return "user/charsTest";
    }

    @RequestMapping("/getCharsData.do")
    @ResponseBody
    public Map[] getCharsData() {

        Map[] maps = new HashMap[7];

        //ArrayList<Map> infos = new ArrayList<>();

        // 创建日期编码对象
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");

        // 创建日历对象
        Calendar calendar = Calendar.getInstance();

        // 给日历对象设置时间
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        // 创建每天的结束时间对象
        Date end = calendar.getTime();
        // 日历天数减1
        calendar.add(Calendar.DATE, -1);
        // 创建每天的开始时间对象
        Date begin = calendar.getTime();

        // 判断type对应的类型

        // 循环查询七天内的数据
        for (int i = 7; i > 0; i--) {
            // 获取数据
            int a = shareDataDao.countByTime(
                    1L, 3, begin, end, ShareDataEntity.SHARE_TYPE_WEB_CLICK);

            // 创建返回的Map对象
            Map<String, Object> result = new HashMap<>();

            // 将数据添加到返回的map中
            result.put("name", format.format(begin));
            result.put("num", a);

            // 将map对象添加到队列中
            // infos.add(result);
            maps[i - 1] = result;

            // 日期减少一天
            end = begin;
            calendar.add(Calendar.DATE, -1);
            begin = calendar.getTime();
        }

        return maps;

    }


}
