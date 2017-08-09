package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.ClientDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.log.UserLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.CookieUtil.cleanCookie;
import static cn.inphoto.util.CookieUtil.createCookie;
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

    @Resource
    ClientDao clientDao;

    @RequestMapping("/toLogin.do")
    public String toLogin(HttpServletRequest request, Model model) throws IOException {
        Cookie[] cookies = request.getCookies();
        BASE64Decoder decoder = new BASE64Decoder();
        String login_type = "";
        String input_text = "";
        String password = "";
        String remLogin = "";
        if (cookies != null) {
            for (Cookie c : cookies
                    ) {
                if ("inphoto_admin_input_text".equals(c.getName())) {
                    input_text = new String(decoder.decodeBuffer(URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                } else if ("inphoto_admin_password_name".equals(c.getName())) {
                    password = new String(decoder.decodeBuffer(URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                } else if ("inphoto_admin_rem_login".equals(c.getName())) {
                    remLogin = new String(decoder.decodeBuffer(URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                } else if ("inphoto_admin_login_type".equals(c.getName())) {
                    login_type = new String(decoder.decodeBuffer(URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                }
            }
        }
        // System.out.println(username + "," + password + "," + remLogin);
        model.addAttribute("input_text", input_text);
        model.addAttribute("password", password);
        model.addAttribute("remLogin", remLogin);
        model.addAttribute("login_type", login_type);

        return "admin/sign_in";
    }

    @RequestMapping("/checkAdmin.do")
    @ResponseBody
    public Map checkAdmin(Integer login_type, String input_text, String password, boolean remLogin,
                          HttpServletResponse response, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {

//        System.out.println(login_type + "   " + input_text + "   " + password + "   " + remLogin);

        Map<String, Object> result = new HashMap<>();

        if (input_text == null || password == null || "".equals(input_text) || "".equals(password)) {
            result.put("success", false);
            result.put("message", "账号、密码不能为空");
            return result;
        }

        AdminInfo adminEntity = null;
        String check_type = null;

        // 判断登录类型
        switch (login_type) {
            case AdminInfo.LOGIN_ADMIN_NAME:
                adminEntity = adminDao.findByAdmin_name(input_text);
                check_type = "用户名登录";
                break;
            case AdminInfo.LOGIN_PHONE:
                adminEntity = adminDao.findByPhone(input_text);
                check_type = "手机号登录";
                break;
            case AdminInfo.LOGIN_EMAIL:
                adminEntity = adminDao.findByEmail(input_text);
                check_type = "邮箱登录";
                break;
            default:
                break;
        }

        if (adminEntity == null) {
            result.put("success", false);
            result.put("message", "未找到对应的用户");
            return result;
        }

        if (!getMD5(password).equals(adminEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLog.ADMIN, "登录验证：用户admin_id=" + adminEntity.getAdminId() +
                    " 尝试 " + check_type + " 验证，登陆结果为：" + result.toString());
            return result;
        } else {
            result.put("success", true);
            result.put("message", "验证成功");
            result.put("url", request.getContextPath() + "/admin/index.do");
        }

        /*获取该用户的角色信息*/
        List<RoleInfo> roleInfos = new ArrayList<>(adminEntity.getRoleInfoSet());

        /*获取该用户所有可访问模块信息*/
        List<ModuleInfo> moduleList = new ArrayList<>();

        for (RoleInfo r : roleInfos
                ) {
            List<ModuleInfo> moduleInfos = new ArrayList<>(r.getModuleInfoSet());
            if (moduleList.isEmpty()) {
                moduleList = moduleInfos;
            } else {
                moduleList.removeAll(moduleInfos);
                moduleList.addAll(moduleInfos);
            }
        }

        /*
        判断是否有管理员权限
         */
        boolean isAdmin = false;

        for (RoleInfo r : roleInfos
                ) {
            if (r.getRoleId() == 1) {
                isAdmin = true;
                break;
            }
        }

        if (remLogin) {
            // 记住登录状态，添加cookie
            response.addCookie(createCookie("inphoto_admin_login_type", Integer.toString(login_type)));
            response.addCookie(createCookie("inphoto_admin_input_text", input_text));
            response.addCookie(createCookie("inphoto_admin_password_name", password));
            response.addCookie(createCookie("inphoto_admin_rem_login", "0"));
        } else {
            // 不记住登录状态，清除cookie，并把inphoto_admin_rem_login改为1
            response.addCookie(cleanCookie("inphoto_admin_login_type"));
            response.addCookie(cleanCookie("inphoto_admin_input_text"));
            response.addCookie(cleanCookie("inphoto_admin_password_name"));
            response.addCookie(createCookie("inphoto_admin_rem_login", "1"));
        }

        session.setAttribute("roleInfoList", roleInfos);
        session.setAttribute("isAdmin", isAdmin);
        session.setAttribute("adminUser", adminEntity);
        session.setAttribute("allModules", moduleList);

        logger.log(UserLog.ADMIN, "登录验证：用户admin_id=" + adminEntity.getAdminId() +
                " 尝试 " + check_type + " 验证，登陆结果为：" + result.toString());
        return result;

    }

    /**
     * 前往错误页
     *
     * @return 页面
     */
    @RequestMapping("/error.do")
    public String errorPage() {
        return "error";
    }

}
