package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.log.UserLogLevel;
import cn.inphoto.util.ImageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import static cn.inphoto.util.CookieUtil.cleanCookie;
import static cn.inphoto.util.CookieUtil.createCookie;
import static cn.inphoto.util.MD5Util.getMD5;
import static cn.inphoto.util.MailUtil.sendMail;
import static cn.inphoto.util.ResultMapUtil.createResult;
import static cn.inphoto.util.ResultMapUtil.getSuccess;
import static cn.inphoto.util.SMSUtil.sendSMSLimit;

/**
 * 管理员登录控制器
 * Created by root on 17-7-11.
 */
@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {

    private static Logger logger = Logger.getLogger(AdminLoginController.class);

    @Value("#{properties['sendEmail']}")
    boolean sendEmail;

    @Resource
    private AdminDao adminDao;

    @Resource
    private UtilDao utilDao;

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

        if (!AdminInfo.ADMIN_STATE_NORMAL.equals(adminEntity.getAdminStatu())) {
            result.put("success", false);
            result.put("message", "该用户现在处于停用状态，请联系管理员");
            return result;
        }

        if (!getMD5(password).equals(adminEntity.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误，请重新输入密码!");
            logger.log(UserLogLevel.ADMIN, "登录验证：用户admin_id=" + adminEntity.getAdminId() +
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

        logger.log(UserLogLevel.ADMIN, "登录验证：用户admin_id=" + adminEntity.getAdminId() +
                " 尝试 " + check_type + " 验证，登陆结果为：" + result.toString());
        return result;

    }

    /**
     * 登出系统
     *
     * @param session 服务器缓存
     * @return 跳转页
     */
    @RequestMapping("/logout.do")
    public String logout(HttpSession session) {

        session.removeAttribute("adminUser");

        return "redirect:toLogin.do";

    }

    /**
     * 忘记密码，跳转到密码重置
     *
     * @return 跳转页
     */
    @RequestMapping("/forgotPassword.do")
    public String forgotPassword() {

        return "admin/forgot_password";

    }

    /**
     * 创建页面验证码
     *
     * @param response 发送
     * @param session  服务器缓存
     * @throws Exception
     */
    @RequestMapping("/createImage.do")
    public void createImage(
            HttpServletResponse response, HttpSession session)
            throws Exception {
        Map<String, BufferedImage> imageMap = ImageUtil.createImage();
        String code = imageMap.keySet().iterator().next();
        session.setAttribute("imageCode", code);

        BufferedImage image = imageMap.get(code);

        response.setContentType("image/jpeg");
        OutputStream ops = response.getOutputStream();
        ImageIO.write(image, "jpeg", ops);
        ops.close();
    }

    /**
     * 发送重置密码验证信息
     *
     * @param input_text 输入内容
     * @param type       类型
     * @param code       验证码
     * @param session    服务器缓存
     * @return 是否成功
     * @throws Exception
     */
    @RequestMapping("/sendForgotPasswordCode.do")
    @ResponseBody
    public Map sendForgotPasswordCode(String input_text, Integer type, String code, HttpSession session) throws Exception {

        String imageCode = (String) session.getAttribute("imageCode");
        if (code == null
                || !code.equalsIgnoreCase(imageCode)) {
            return createResult(false, "验证码错误，请重新输入验证码");
        }

        AdminInfo adminInfo = null;

        switch (type) {
            case AdminInfo.LOGIN_EMAIL:
                adminInfo = adminDao.findByEmail(input_text);
                break;
            case AdminInfo.LOGIN_PHONE:
                adminInfo = adminDao.findByPhone(input_text);
                break;
            default:
                break;
        }

        if (adminInfo == null) {
            return createResult(false, "未找到该邮箱/手机号对应的管理员");
        }

        // 创建6位验证码字符串对象
        StringBuilder codeTemp = new StringBuilder();
        Random random = new Random();

        // 生成6位随机码
        for (int i = 0; i < 6; i++) {
            codeTemp.append(random.nextInt(9));
        }

        session.setMaxInactiveInterval(10 * 60);
        session.setAttribute("forgot_password_code", codeTemp);
        session.setAttribute("forgot_password_admin", adminInfo);

        switch (type) {
            case AdminInfo.LOGIN_EMAIL:
                // 发送邮件
                if (sendEmail) {
                    sendMail(adminInfo.getEmail(), "IN Photo管理员验证",
                            "<div>尊敬的" + adminInfo.getEmail() + "您好！ 以下是您的验证码：</div>" +
                                    "<div><includetail><p><strong style=\"color:red\">" + codeTemp + "</strong></p>" +
                                    "<p>我们收到了来自您的重置密码请求，请使用上面的验证码验证您的账号</p>" +
                                    "<p><strong>请注意：</strong>验证码将在10分钟内过期，请尽快验证</p>" +
                                    "<p>上海赢秀多媒体科技有限公司</p></includetail></div>");
                }
                break;
            case AdminInfo.LOGIN_PHONE:
                if (!getSuccess(sendSMSLimit(adminInfo.getPhone(), codeTemp.toString(), "IN PHOTO管理员系统验证", "SMS_61155105", "forgotAdminPassword", session))) {
                    return createResult(false, "发送失败，请联系管理员查看短信服务器状态");
                }
                break;
            default:
                break;
        }

        return createResult(true, "验证码已经成功发送，请查看验证码");

    }


    /**
     * 前往重置密码页
     *
     * @return 页面
     */
    @RequestMapping("/toResetPassword.do")
    public String toResetPassword() {
        return "admin/reset_password";
    }

    /**
     * 重置密码
     *
     * @return 页面
     */
    @RequestMapping("/resetPassword.do")
    @ResponseBody
    public Map resetPassword(String password, String code, HttpSession session) {

        StringBuilder session_code = (StringBuilder) session.getAttribute("forgot_password_code");

        if (session_code == null || !code.equals(session_code.toString())) {
            return createResult(false, "未找到正确的验证码");
        }

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("forgot_password_admin");

        adminInfo = adminDao.findByAdmin_id(adminInfo.getAdminId());

        if (adminInfo == null) {
            return createResult(false, "未在数据库中找到管理员");
        }

        // 更新数据
        adminInfo.setPassword(getMD5(password));

        // 写入数据
        if (!utilDao.update(adminInfo)) {
            return createResult(false, "将数据写入数据库时发生了错误，请稍候重试");
        }

        return createResult(true, "重置成功");
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
