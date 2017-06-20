package cn.inphoto.user.controller;

import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.UserCategoryEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.user.util.DBUtil.judgeCategory;
import static cn.inphoto.user.util.DBUtil.selectTodayData;

/**
 * 登陆控制器
 * Created by kaxia on 2017/6/5.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserDao userDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "user/sign_in";
    }

    @RequestMapping("/checkUser.do")
    @ResponseBody
    public Map checkUser(String user_name, String password, HttpSession session, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        if (user_name == null || password == null || "".equals(user_name) || "".equals(password)) {
            result.put("success", false);
            result.put("message", "账号、密码不能为空");
            return result;
        }

        UsersEntity usersEntity = userDao.findByUser_name(user_name);

        if (!password.equals(usersEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            return result;
        }

        // 查询该用户所有的用户套餐系统
        List<UserCategoryEntity> userCategoryList = userCategoryDao.findByUser_idAndState(usersEntity.getUserId(), UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

        //查询所有的套餐系统
        judgeCategory(request);

        // 查询今日数据并写入session
        selectTodayData(session, usersEntity);

        // 将所有的用户套餐系统写入session
        session.setAttribute("allUserCategory", userCategoryList);

        result.put("success", true);
        result.put("message", "登陆成功");

        session.setAttribute("loginUser", usersEntity);

        return result;

    }


}
