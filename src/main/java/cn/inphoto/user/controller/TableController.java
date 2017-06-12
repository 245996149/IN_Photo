package cn.inphoto.user.controller;

import cn.inphoto.user.dao.MediaDataDao;
import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.ShareDataEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import cn.inphoto.user.dbentity.page.TablePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaxia on 2017/6/12.
 */
@Controller
@RequestMapping("/table")
public class TableController {

    @Resource
    MediaDataDao mediaDataDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    ShareDataDao shareDataDao;

    @RequestMapping("/toTable.do")
    public String toTable(Model model, HttpSession session, TablePage tablePage) {
        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        List<MediaDataEntity> mediaDatas = mediaDataDao.findByPage(tablePage);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("MM/dd");
        Calendar calendar_begin = Calendar.getInstance();
        calendar_begin.setTime(new Date());
        calendar_begin.set(Calendar.HOUR_OF_DAY, 0);
        calendar_begin.set(Calendar.MINUTE, 0);
        calendar_begin.set(Calendar.SECOND, 0);
        Calendar calendar_end = calendar_begin;
        calendar_end.add(Calendar.DATE, 1);

        Map<String,Integer> calendarMap = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            calendarMap.put(format2.format(calendar_begin),
                    shareDataDao.countByTime(
                            format1.format(calendar_begin), format1.format(calendar_end), ShareDataEntity.SHARE_TYPE_WEB_CLICK));

            calendar_end = calendar_begin;
            calendar_begin.add(Calendar.DATE, -1);
        }


        model.addAttribute("mediaDatas", mediaDatas);

        return "user/table";
    }

}
