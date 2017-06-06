package cn.inphoto.user.controller;

import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by kaxia on 2017/6/6.
 */
@Controller
@RequestMapping("/user")
public class IndexController {

    @RequestMapping("/index.do")
    public String index(Model model, HttpSession session) {
        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");
        model.addAttribute("usersEntity", usersEntity);
        return "user/index";
    }

    @RequestMapping("/toTable.do")
    public String toTable(Model model, HttpSession session) {
        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");
        model.addAttribute("usersEntity", usersEntity);
        return "user/table";
    }


}
