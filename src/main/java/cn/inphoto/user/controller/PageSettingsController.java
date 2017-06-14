package cn.inphoto.user.controller;

import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static cn.inphoto.user.util.DBUtil.selectTodayData;

/**
 * Created by kaxia on 2017/6/14.
 */
@RequestMapping("/setting")
@Controller
public class PageSettingsController {

    @Resource
    ShareDataDao shareDataDao;

    @RequestMapping("/toPageSettings.do")
    public String toPageSettings(int category_id, Model model, HttpSession session) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        model.addAttribute("category_id", category_id);

        // 查询今日数据并写入session
        selectTodayData(session, usersEntity);

        return "user/page_settings";
    }

}
