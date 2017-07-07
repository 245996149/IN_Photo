package cn.inphoto.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by kaxia on 2017/7/6.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /*首页简码*/
    public static int INDEX_CODE = 1;
    /*套餐管理简码*/
    public static int CATEGORY_CODE = 2;
    /*用户管理简码*/
    public static int USER_CODE = 3;


    @RequestMapping("/index.do")
    public String index(HttpSession session) {
        session.setAttribute("admin_nav_code", INDEX_CODE);
        return "admin/index";
    }

    @RequestMapping("/toCategory.do")
    public String toCategory(HttpSession session) {
        session.setAttribute("admin_nav_code", CATEGORY_CODE);
        return "admin/category";
    }

    @RequestMapping("/toUser.do")
    public String toUser(HttpSession session) {
        session.setAttribute("admin_nav_code", USER_CODE);
        return "admin/table";
    }

}
