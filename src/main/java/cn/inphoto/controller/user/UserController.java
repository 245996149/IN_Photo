package cn.inphoto.controller.user;

import cn.inphoto.dbentity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by kaxia on 2017/6/6.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /*首页简码*/
    public static int INDEX_CODE = 1;
    /*套餐管理简码*/
    public static int CATEGORY_CODE = 2;
    /*数据管理简码*/
    public static int TABLE_CODE = 3;
    /*页面设置简码*/
    public static int PAGESETTINGS_CODE = 4;
    /*用户选项简码*/
    public static int USER_CODE = 0;


    @RequestMapping("/index.do")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("usersEntity", user);
        session.setAttribute("nav_code", UserController.INDEX_CODE);
        return "user/index";
    }

    @RequestMapping("/toTable.do")
    public String toTable(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("usersEntity", user);
        return "user/table";
    }

    @RequestMapping("/toCategory.do")
    public String toCategory() {
        return "user/category";
    }

    @RequestMapping("/toPageSettings.do")
    public String toPageSettings() {
        return "user/page_settings";
    }


}
