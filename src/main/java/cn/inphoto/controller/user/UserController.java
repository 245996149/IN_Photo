package cn.inphoto.controller.user;

import cn.inphoto.dao.UserDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.log.UserLogLevel;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static cn.inphoto.util.ResultMapUtil.createResult;
import static cn.inphoto.util.ResultMapUtil.getSuccess;
import static cn.inphoto.util.SMSUtil.sendSMSLimit;

/**
 * Created by kaxia on 2017/6/6.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

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

    @Resource
    UserDao userDao;

    @Resource
    UtilDao utilDao;

    @RequestMapping("/index.do")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("usersEntity", user);
        session.setAttribute("nav_code", UserController.INDEX_CODE);
        return "user/index";
    }

    /**
     * 添加用户名
     *
     * @param session   服务器缓存
     * @param user_name 用户名
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping("/addUserName.do")
    public Map addUserName(HttpSession session, String user_name) {

        User user = (User) session.getAttribute("loginUser");

        // 判断账户中是否有账户名
        if (user.getUserName() != null) {
            return createResult(false, "该用户已经存在用户名");
        }

        // 判断数据库中是否有该账户名的账户
        User u = userDao.findByUser_name(user_name);

        if (u != null) {
            return createResult(false, "该用户名已被使用");
        }

        // 从数据库中获取账户
        u = userDao.findByUser_id(user.getUserId());

        // 更新数据
        u.setUserName(user_name);

        // 写入数据
        if (!utilDao.update(u)) {
            return createResult(false, "写入数据时发生了错误，请稍后再试");
        }

        // 更新session中的数据
        session.setAttribute("loginUser", u);

        return createResult(true, "写入成功");

    }

    /**
     * 发送验证码短信
     *
     * @param phone   手机号
     * @param session 服务器缓存
     * @return 是否发送成功
     */
    @ResponseBody
    @RequestMapping("/sendPhoneCode.do")
    public Map sendPhoneCode(String phone, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        // 判断账户中是否有手机号
        if (user.getPhone() != null) {
            return createResult(false, "该用户已经存在手机号");
        }

        // 判断数据库中是否有该手机号的账户
        User u = userDao.findByPhone(phone);

        if (u != null) {
            return createResult(false, "该手机号已被使用");
        }

        MDC.put("user_id", user.getUserId());

        // 创建6位验证码字符串对象
        StringBuilder codeTemp = new StringBuilder();
        Random random = new Random();

        // 生成6位随机码
        for (int i = 0; i < 6; i++) {
            codeTemp.append(random.nextInt(9));
        }

        Map<String, String> codeMap = new HashMap<>();

        codeMap.put(phone, codeTemp.toString());

        session.setAttribute("addUserPhoneCode", codeMap);

        if (!getSuccess(sendSMSLimit(
                phone, codeTemp.toString(), "IN PHOTO管理系统绑定手机号",
                "SMS_61155105", "addUserPhone", session))) {
            logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                    " 的用户请求绑定手机号，提交的信息为：" + phone + "，发送短信失败，请检查短信服务器状态");
            return createResult(false, "发送失败，请联系管理员查看短信服务器状态");
        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                " 的用户请求绑定手机号，提交的信息为：" + phone + "，发送验证信息成功，验证信息为" + codeTemp);

        return createResult(true, "发送成功");
    }

    /**
     * 验证手机号验证码是否对应，对应添加手机号
     *
     * @param phone   手机号
     * @param code    验证码
     * @param session 服务器缓存
     * @return 是否对应
     */
    @RequestMapping("/checkPhoneCode.do")
    @ResponseBody
    public Map checkPhoneCode(String phone, String code, HttpSession session) {

        Map<String, String> codeMap = (Map<String, String>) session.getAttribute("addUserPhoneCode");

        String sessionCode = codeMap.get(phone);

        if ("".equals(sessionCode) || sessionCode == null) {
            return createResult(false, "未找到对应的验证码!");
        }

        if (!code.equals(sessionCode)) {
            return createResult(false, "验证码不正确!");
        }

        // 更新数据
        User user = (User) session.getAttribute("loginUser");

        user.setPhone(phone);

        MDC.put("user_id", user.getUserId());

        // 写入数据
        if (!utilDao.update(user)) {
            logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                    " 的用户请求绑定手机号，已经验证验证码，提交的信息为：" + phone + "，写入数据是发生了错误");
            return createResult(false, "写入数据是发生了错误，请稍候再试");
        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() +
                " 的用户请求绑定手机号，已经验证验证码，提交的信息为：" + phone + "，绑定手机号成功");
        return createResult(true, "绑定手机号成功");
    }


}
