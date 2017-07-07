package cn.inphoto.user.controller;

import cn.inphoto.user.dao.CategoryDao;
import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dao.UserDao;
import cn.inphoto.user.dbentity.CategoryEntity;
import cn.inphoto.user.dbentity.UserCategoryEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import cn.inphoto.user.log.UserLog;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Value;
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

import static cn.inphoto.user.util.DBUtil.judgeCategory;
import static cn.inphoto.user.util.DBUtil.selectTodayData;

/**
 * 登陆控制器
 * Created by kaxia on 2017/6/5.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Value("#{properties['admin_user']}")
    String admin_user;

    @Value("#{properties['admin_password']}")
    String admin_password;

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

    @RequestMapping("/toAdminLogin.do")
    public String toAdminLogin() {
        return "admin/sign_in";
    }

    /**
     * 管理员登陆验证
     *
     * @param user_name 管理员用户名
     * @param password  管理员密码
     * @return 验证是否成功
     */
    @RequestMapping("/checkAdminUser.do")
    @ResponseBody
    public Map checkAdminUser(String user_name, String password, HttpServletRequest request, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (user_name == null || password == null || "".equals(user_name) || "".equals(password)) {
            result.put("success", false);
            result.put("message", "账号、密码不能为空");
            return result;
        }

        if (!user_name.equals(admin_user) || !password.equals(admin_password)) {
            result.put("success", false);
            result.put("message", "账号、密码错误");
            return result;
        }

        session.setAttribute("adminUser", 1);

        result.put("success", true);
        result.put("url", request.getContextPath() + "/admin/index.do");
        return result;
    }

    @RequestMapping("/adminSignOut.do")
    public String adminSignOut(HttpSession session) {
        session.removeAttribute("adminUser");
        return "redirect:toAdminLogin.do";
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

        UsersEntity usersEntity = userDao.findByUser_name(user_name);

        if (!password.equals(usersEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());
            return result;
        }

        MDC.put("user_id", usersEntity.getUserId());

        result.put("success", true);
        result.put("message", "验证成功");
        logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());

        return result;

    }

    @RequestMapping("/login.do")
    public String login(String user_name, String password, HttpSession session, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        UsersEntity usersEntity = userDao.findByUser_name(user_name);

        if (!password.equals(usersEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());
            return "redirect:toLogin.do";
        }

        MDC.put("user_id", usersEntity.getUserId());

        // 查询该用户所有的用户套餐系统
        List<UserCategoryEntity> userCategoryList = userCategoryDao.findByUser_idAndState(
                usersEntity.getUserId(), UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

        //查询所有的套餐系统
        judgeCategory(categoryDao, request);

        // 查询今日数据并写入session
        selectTodayData(shareDataDao, session, usersEntity);

        // 将所有的用户套餐系统写入session
        session.setAttribute("allUserCategory", userCategoryList);

        result.put("success", true);
        result.put("message", "登陆成功");
        logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());

        session.setAttribute("loginUser", usersEntity);

        return "redirect:/user/index.do";
    }

    @RequestMapping("/signOut.do")
    public String signOut(HttpSession session) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        MDC.put("user_id", usersEntity.getUserId());

        // 查找到所有session中的参数
        Enumeration em = session.getAttributeNames();

        // 循环移除所有参数
        while (em.hasMoreElements()) {

            session.removeAttribute(em.nextElement().toString());

        }

        logger.log(UserLog.USER, "用户user_name=" + usersEntity.getUserName() + " 的用户退出登陆");

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

        UsersEntity user = userDao.findByUser_name(user_name);

        if (user == null) {

            result.put("success", false);
            result.put("message", "未找到该用户名所对应的用户!");
            logger.info(result);
            return result;

        }

        // 比较查询到的用户的password与传入的password，正确判断用户是否有有效的类别，错误返回错误信息
        if (password.equals(user.getPassword())) {

            CategoryEntity category = categoryDao.findByCategory_code(type);

            if (category == null) {

                result.put("success", false);
                result.put("message", "未找到type对应的套餐系统");
                logger.info(result);
                return result;

            }

            UserCategoryEntity userCategory = userCategoryDao.findByUser_idAndCategory_id(
                    user.getUserId(), category.getCategoryId(), UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

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
