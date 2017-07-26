package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.log.UserLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.MD5Util.getMD5;

/**
 * 管理员登录控制器
 * Created by root on 17-7-11.
 */
@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {

    private static Logger logger = Logger.getLogger(AdminLoginController.class);

    @Resource
    AdminDao adminDao;

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "admin/sign_in";
    }

    @RequestMapping("/checkAdmin.do")
    @ResponseBody
    public Map checkAdmin(String admin_name, String password) {

        Map<String, Object> result = new HashMap<>();

        if (admin_name == null || password == null || "".equals(admin_name) || "".equals(password)) {
            result.put("success", false);
            result.put("message", "账号、密码不能为空");
            return result;
        }

        AdminInfo adminEntity = adminDao.findByAdmin_name(admin_name);

        if (!getMD5(password).equals(adminEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLog.USER, "用户user_name=" + admin_name + " 的用户尝试登陆，登陆结果为：" + result.toString());
            return result;
        }

        result.put("success", true);
        result.put("message", "验证成功");
//        logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());

        return result;

    }


    @RequestMapping("/login.do")
    public String login(String admin_name, String password, HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        AdminInfo adminEntity = adminDao.findByAdmin_name(admin_name);

        if (!getMD5(password).equals(adminEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            //logger.log(UserLog.USER, "用户user_name=" + user_name + " 的用户尝试登陆，登陆结果为：" + result.toString());
            return "redirect:toLogin.do";
        }
        List<ModuleInfo> moduleList = adminDao.findModulesByAdmin(adminEntity.getAdminId());

        session.setAttribute("adminUser", adminEntity);
        session.setAttribute("allModules", moduleList);

        return "redirect:/admin/index.do";
    }


}
