package cn.inphoto.user.controller;

import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaxia on 2017/6/5.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserDao userDao;

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "user/sign_in";
    }

    @RequestMapping("/checkUser.do")
    @ResponseBody
    public Map checkUser(String user_name, String password, HttpSession  session) {
        Map<String, Object> result = new HashMap<>();

        if (user_name == null || password == null || "".equals(user_name) || "".equals(password)) {
            result.put("success", false);
            result.put("message", "账号、密码不能为空");
            return result;
        }

        UsersEntity usersEntity = userDao.searchByUser_name(user_name);

        if (!password.equals(usersEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            return result;
        }

        result.put("success", true);
        result.put("message", "登陆成功");

        session.setAttribute("loginUser", usersEntity);

        return result;

    }


}
