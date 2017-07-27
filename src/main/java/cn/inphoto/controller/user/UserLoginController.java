package cn.inphoto.controller.user;

import cn.inphoto.dao.CategoryDao;
import cn.inphoto.dao.ShareDataDao;
import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.user.Category;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.dbentity.user.UserCategory;
import cn.inphoto.log.UserLog;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.DBUtil.judgeCategory;
import static cn.inphoto.util.DBUtil.selectTodayData;
import static cn.inphoto.util.MD5Util.getMD5;

/**
 * 登陆控制器
 * Created by kaxia on 2017/6/5.
 */
@Controller
@RequestMapping("/user/login")
public class UserLoginController {

    private static Logger logger = Logger.getLogger(UserLoginController.class);

    @Resource
    UserDao userDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    CategoryDao categoryDao;

    @Resource
    ShareDataDao shareDataDao;

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "user/sign_in";
    }

    @RequestMapping("/checkUser.do")
    @ResponseBody
    public Map checkUser(String user_name, String password) {

        Map<String, Object> result = new HashMap<>();

        if (user_name == null || password == null || "".equals(user_name) || "".equals(password)) {
            result.put("success", false);
            result.put("message", "账号、密码不能为空");
            return result;
        }

        User user = userDao.findByUser_name(user_name);

        if (!getMD5(password).equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());
            return result;
        }

        MDC.put("user_id", user.getUserId());

        result.put("success", true);
        result.put("message", "验证成功");
        logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());

        return result;

    }

    @RequestMapping("/login.do")
    public String login(String user_name, String password, HttpSession session, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        User user = userDao.findByUser_name(user_name);

        if (!getMD5(password).equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());
            return "redirect:toLogin.do";
        }

        MDC.put("user_id", user.getUserId());

        // 查询该用户所有的用户套餐系统
        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndState(
                user.getUserId(), UserCategory.USER_CATEGORY_STATE_NORMAL);

        //查询所有的套餐系统
        judgeCategory(categoryDao, request);

        // 查询今日数据并写入session
        selectTodayData(shareDataDao, session, user);

        // 将所有的用户套餐系统写入session
        session.setAttribute("allUserCategory", userCategoryList);

        result.put("success", true);
        result.put("message", "登陆成功");
        logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());

        session.setAttribute("loginUser", user);

        return "redirect:/user/index.do";
    }

    @RequestMapping("/signOut.do")
    public String signOut(HttpSession session) {

        User user = (User) session.getAttribute("loginUser");

        MDC.put("user_id", user.getUserId());

        // 查找到所有session中的参数
        Enumeration em = session.getAttributeNames();

        // 循环移除所有参数
        while (em.hasMoreElements()) {

            session.removeAttribute(em.nextElement().toString());

        }

        logger.log(UserLog.USER, "用户user_name=" + user.getUserName() + " 的用户退出登陆");

        return "redirect:toLogin.do";

    }


    @RequestMapping("/checkClientUser.do")
    @ResponseBody
    public Map<String, Object> checkClientUser(HttpServletResponse response, String user_name, String password, String type) {

        response.setCharacterEncoding("utf-8");

        logger.info("接收到的参数为：userName=" + user_name + ";password=" + password + ";type=" + type);

        // 设置返回的Map
        Map<String, Object> result = new HashMap<>();

        // 如果用户名或者密码为空，返回错误信息
        if (user_name == null || password == null || type == null) {

            result.put("code", "error");
            result.put("message", "用户名、密码、类型不能为空");
            logger.info(result);
            return result;

        }

        User user = userDao.findByUser_name(user_name);

        if (user == null) {

            result.put("success", false);
            result.put("message", "未找到该用户名所对应的用户!");
            logger.info(result);
            return result;

        }

        // 比较查询到的用户的password与传入的password，正确判断用户是否有有效的类别，错误返回错误信息
        if (getMD5(password).equals(user.getPassword())) {

            Category category = categoryDao.findByCategory_code(type);

            if (category == null) {

                result.put("success", false);
                result.put("message", "未找到type对应的套餐系统");
                logger.info(result);
                return result;

            }

            UserCategory userCategory = userCategoryDao.findByUser_idAndCategory_id(
                    user.getUserId(), category.getCategoryId(), UserCategory.USER_CATEGORY_STATE_NORMAL);

            if (userCategory == null) {

                result.put("success", false);
                result.put("message", "该userName对应的用户没有有效的套餐。请联系我们购买套餐噢！");
                logger.info(result);
                return result;

            }

            result.put("success", true);
            result.put("user_id", user.getUserId());
            result.put("category_id", category.getCategoryId());
            logger.info(result);
            return result;

        } else {

            result.put("success", false);
            result.put("message", "密码错误！");
            logger.info(result);
            return result;

        }

    }

}
