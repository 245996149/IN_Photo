package cn.inphoto.controller.user;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.user.Category;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.dbentity.user.UserCategory;
import cn.inphoto.log.UserLogLevel;
import cn.inphoto.log.UserLogLevel;
import cn.inphoto.util.ImageUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import static cn.inphoto.util.DBUtil.judgeCategory;
import static cn.inphoto.util.DBUtil.selectTodayData;
import static cn.inphoto.util.MD5Util.getMD5;
import static cn.inphoto.util.MailUtil.sendMail;
import static cn.inphoto.util.ResultMapUtil.createResult;
import static cn.inphoto.util.ResultMapUtil.getSuccess;
import static cn.inphoto.util.SMSUtil.sendSMSLimit;

/**
 * 登陆控制器
 * Created by kaxia on 2017/6/5.
 */
@Controller
@RequestMapping("/user/login")
public class UserLoginController {

    private static Logger logger = Logger.getLogger(UserLoginController.class);

    @Value("#{properties['sendEmail']}")
    boolean sendEmail;

    @Resource
    private UtilDao utilDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserCategoryDao userCategoryDao;

    @Resource
    private CategoryDao categoryDao;

    /**
     * 前往登录页面
     *
     * @param request 请求
     * @param model   页面缓存数据
     * @return 登录页面
     * @throws IOException 抛出IO错误
     */
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
                if ("inphoto_user_input_text".equals(c.getName())) {
                    input_text = new String(decoder.decodeBuffer(
                            URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                } else if ("inphoto_user_password_name".equals(c.getName())) {
                    password = new String(decoder.decodeBuffer(
                            URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                } else if ("inphoto_user_rem_login".equals(c.getName())) {
                    remLogin = new String(decoder.decodeBuffer(
                            URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                } else if ("inphoto_user_login_type".equals(c.getName())) {
                    login_type = new String(decoder.decodeBuffer(
                            URLDecoder.decode(c.getValue(), "utf-8")), "utf-8");
                }
            }
        }
        model.addAttribute("input_text", input_text);
        model.addAttribute("password", password);
        model.addAttribute("remLogin", remLogin);
        model.addAttribute("login_type", login_type);
        return "user/sign_in";
    }

    /**
     * 校验登录账号
     *
     * @param login_type 登录类型
     * @param input_text 输入的文本
     * @param password   密码
     * @param remLogin   是否记住登录
     * @param response   返回
     * @param session    服务器缓存
     * @param request    请求
     * @return 是否成功
     * @throws UnsupportedEncodingException 抛出编码异常错误
     */
    @RequestMapping(value = "/checkUser.do", method = RequestMethod.POST)
    @ResponseBody
    public Map checkUser(Integer login_type, String input_text, String password, boolean remLogin,
                         HttpServletResponse response, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {

        if (input_text == null || password == null || "".equals(input_text) || "".equals(password)) {
            return createResult(false, "账号、密码不能为空");
        }

        System.out.println(login_type + ";" + input_text + ";" + password + ";" + remLogin);

        User user = null;
        String check_type = null;

        // 判断登录类型
        switch (login_type) {
            case User.LOGIN_USER_NAME:
                user = userDao.findByUser_name(input_text);
                check_type = "用户名登录";
                break;
            case User.LOGIN_PHONE:
                user = userDao.findByPhone(input_text);
                check_type = "手机号登录";
                break;
            case User.LOGIN_EMAIL:
                user = userDao.findByEmail(input_text);
                check_type = "邮箱登录";
                break;
            default:
                break;
        }

        if (user == null) {
            return createResult(false, "未找到对应的用户");
        }

        if (!User.USER_STATE_NORMAL.equals(user.getUserState())) {
            return createResult(false, "该用户现在处于停用状态，请联系管理员");
        }

        MDC.put("user_id", user.getUserId());

        if (!getMD5(password).equals(user.getPassword())) {
            logger.log(UserLogLevel.USER, "登录验证：用户user_id=" + user.getUserId() +
                    " 尝试 " + check_type + " 验证，登陆结果为：密码错误");
            return createResult(false, "密码错误，请重新输入密码!");
        }

        // 查询该用户所有的用户套餐系统
        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndState(
                user.getUserId(), UserCategory.UserState.NORMAL);

        //查询所有的套餐系统
        judgeCategory(request);

        // 查询今日数据并写入session
        selectTodayData(session, user);

        // 将所有的用户套餐系统写入session
        session.setAttribute("allUserCategory", userCategoryList);

        if (remLogin) {
            // 记住登录状态，添加cookie
            response.addCookie(createCookie("inphoto_user_login_type", Integer.toString(login_type)));
            response.addCookie(createCookie("inphoto_user_input_text", input_text));
            response.addCookie(createCookie("inphoto_user_password_name", password));
            response.addCookie(createCookie("inphoto_user_rem_login", "0"));
        } else {
            // 不记住登录状态，清除cookie，并把inphoto_admin_rem_login改为1
            response.addCookie(cleanCookie("inphoto_user_login_type"));
            response.addCookie(cleanCookie("inphoto_user_input_text"));
            response.addCookie(cleanCookie("inphoto_user_password_name"));
            response.addCookie(createCookie("inphoto_user_rem_login", "1"));
        }

        session.setAttribute("loginUser", user);

        return createResult(true, "验证成功");

    }

    /**
     * 登出
     *
     * @param session 服务器缓存
     * @return 登录页面
     */
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

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                ";user_name=" + user.getUserName() + " 的用户退出登陆");

        return "redirect:toLogin.do";

    }

    /**
     * 验证客户端用户名密码
     *
     * @param response      返回
     * @param login_type    登录类型
     * @param input_text    输入的文本
     * @param password      密码
     * @param category_code 套餐简码
     * @return 是否成功
     */
    @RequestMapping(value = "/checkClientUser.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkClientUser(HttpServletResponse response,
                                               Integer login_type, String input_text, String password, String category_code) {

        response.setCharacterEncoding("utf-8");

        // 如果用户名或者密码为空，返回错误信息
        if (login_type == null || input_text == null || password == null || category_code == null) {

            return createResult(false, "用户名、密码、类型不能为空");

        }

        User user = null;
        String check_type = null;

        // 判断登录类型
        switch (login_type) {
            case User.LOGIN_USER_NAME:
                user = userDao.findByUser_name(input_text);
                check_type = "用户名登录";
                break;
            case User.LOGIN_PHONE:
                user = userDao.findByPhone(input_text);
                check_type = "手机号登录";
                break;
            case User.LOGIN_EMAIL:
                user = userDao.findByEmail(input_text);
                check_type = "邮箱登录";
                break;
            default:
                break;
        }

        if (user == null) {

            return createResult(false, "未找到该用户名所对应的用户");

        }

        // 比较查询到的用户的password与传入的password，正确判断用户是否有有效的类别，错误返回错误信息
        if (!getMD5(password).equals(user.getPassword())) {
            return createResult(false, "密码错误");
        }

        Category category = categoryDao.findByCategory_code(category_code);

        if (category == null) {

            return createResult(false, "未找到category_code对应的套餐系统");

        }

        UserCategory userCategory = userCategoryDao.findByUser_idAndCategory_idAndState(
                user.getUserId(), category.getCategoryId(), UserCategory.UserState.NORMAL);

        if (userCategory == null) {

            return createResult(false, "该用户没有有效的套餐。请联系我们购买套餐噢！");

        }

        // 设置返回的Map
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("user_id", user.getUserId());
        result.put("category_id", category.getCategoryId());
        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                " 的用户使用" + check_type + "请求了客户端访问接口，请求成功，返回信息为：" + result.toString());
        return result;

    }

    /**
     * 忘记密码，跳转到密码重置
     *
     * @return 跳转页
     */
    @RequestMapping("/forgotPassword.do")
    public String forgotPassword() {

        return "user/forgot_password";

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

        User user = null;

        switch (type) {
            case User.LOGIN_EMAIL:
                user = userDao.findByEmail(input_text);
                break;
            case User.LOGIN_PHONE:
                user = userDao.findByPhone(input_text);
                break;
            default:
                break;
        }

        if (user == null) {
            return createResult(false, "未找到该邮箱/手机号对应的用户");
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
        session.setAttribute("forgot_password_user", user);

        MDC.put("user_info", "user_id=" + user.getUserId());

        switch (type) {
            case AdminInfo.LOGIN_EMAIL:
                // 发送邮件
                if (sendEmail) {
                    sendMail(user.getEmail(), "INPHOTO管理系统验证",
                            "<div>尊敬的" + user.getEmail() + "您好！ 以下是您的验证码：</div>" +
                                    "<div><includetail><p><strong style='color:red'>" + codeTemp + "</strong></p>" +
                                    "<p>我们收到了来自您的重置密码请求，请使用上面的验证码验证您的账号</p>" +
                                    "<p><strong>请注意：</strong>验证码将在10分钟内过期，请尽快验证</p>" +
                                    "<p>上海赢秀多媒体科技有限公司</p></includetail></div>");
                }
                break;
            case AdminInfo.LOGIN_PHONE:
                if (!getSuccess(sendSMSLimit(user.getPhone(), codeTemp.toString(), "INPHOTO管理系统验证", "SMS_61155105", "forgotAdminPassword", session))) {
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                            " 的用户请求重置密码，提交的信息为：" + input_text + ";" + type + "，发送短信失败，请检查短信服务器状态");
                    return createResult(false, "发送失败，请联系管理员查看短信服务器状态");
                }
                break;
            default:
                break;
        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                " 的用户请求重置密码，提交的信息为：" + input_text + ";" + type + "，发送验证信息成功，验证信息为" + codeTemp);

        return createResult(true, "验证码已经成功发送，请查看验证码");

    }


    /**
     * 前往重置密码页
     *
     * @return 页面
     */
    @RequestMapping("/toResetPassword.do")
    public String toResetPassword() {
        return "user/reset_password";
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

        User user = (User) session.getAttribute("forgot_password_user");

        user = userDao.findByUser_id(user.getUserId());

        if (user == null) {
            return createResult(false, "未在数据库中找到用户");
        }

        MDC.put("user_info", "user_id=" + user.getUserId());

        // 更新数据
        user.setPassword(getMD5(password));

        // 写入数据
        if (!utilDao.update(user)) {
            logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                    " 的用户请求重置密码，重置失败，原因是将数据写入数据库时发生了错误");
            return createResult(false, "将数据写入数据库时发生了错误，请稍候重试");
        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                " 的用户请求重置密码，重置成功");
        return createResult(true, "重置成功");
    }

}
