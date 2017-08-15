package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static cn.inphoto.util.ResultMapUtil.createResult;
import static cn.inphoto.util.SMSUtil.sendSMS;

/**
 * Created by root on 17-7-11.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminDao adminDao;

    @Resource
    private UtilDao utilDao;

    @RequestMapping("/index.do")
    public String index() {
        return "admin/index";
    }

    /**
     * 前往无权限页面
     *
     * @return 无权限页面
     */
    @RequestMapping("/noPower.do")
    public String noPower() {
        return "no_power";
    }

    /**
     * 校验邮箱地址在数据库中是否可以使用
     *
     * @param email 邮箱地址
     * @return 是否存在
     */
    @ResponseBody
    @RequestMapping("/checkEmail.do")
    public Map checkEmail(String email) {

        AdminInfo adminInfo = adminDao.findByEmail(email);

        if (adminInfo != null) {
            return createResult(false, "已经存在该email");
        }

        return createResult(true, "通过email验证");

    }

    /**
     * 添加管理员账户名
     *
     * @param session    服务器缓存
     * @param admin_name 管理员账户名
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping("/addAdminName.do")
    public Map addAdminName(HttpSession session, String admin_name) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        System.out.println(adminInfo.getAdminName());

        // 判断账户中是否有账户名
        if (adminInfo.getAdminName() != null) {
            return createResult(false, "该管理员账户已经存在账户名");
        }

        // 判断数据库中是否有该账户名的账户
        AdminInfo a = adminDao.findByAdmin_name(admin_name);

        if (a != null) {
            return createResult(false, "该账户名已被使用");
        }

        // 从数据库中获取账户
        a = adminDao.findByAdmin_id(adminInfo.getAdminId());

        // 更新数据
        a.setAdminName(admin_name);

        // 写入数据
        if (!utilDao.update(a)) {
            return createResult(false, "写入数据时发生了错误，请稍后再试");
        }

        // 更新session中的数据
        session.setAttribute("adminUser", a);

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
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        // 判断账户中是否有手机号
        if (adminInfo.getPhone() != null) {
            return createResult(false, "该管理员账户已经存在手机号");
        }

        // 判断数据库中是否有该手机号的账户
        AdminInfo a = adminDao.findByPhone(phone);

        if (a != null) {
            return createResult(false, "该手机号已被使用");
        }

        // 从session中取出发送次数
        Map<String, Integer> phoneNum = (Map<String, Integer>) session.getAttribute("addPhoneNum");

        // 判断发送次数
        if (phoneNum != null) {

            Integer num = phoneNum.get(phone);

            if (num == null) {
                num = 1;
            } else {
                num++;
            }

            if (num > 3) {
                return createResult(false, "今天已经超过发送限制了哦！");
            }
            phoneNum.put(phone, num);
        } else {
            phoneNum = new HashMap<>();
            phoneNum.put(phone, 1);
        }

        // 创建6位验证码字符串对象
        StringBuilder codeTemp = new StringBuilder();
        Random random = new Random();

        // 生成6位随机码
        for (int i = 0; i < 6; i++) {
            codeTemp.append(random.nextInt(9));
        }

        Map<String, String> codeMap = new HashMap<>();

        codeMap.put(phone, codeTemp.toString());

        session.setAttribute("addPhoneCode", codeMap);

        session.setAttribute("addPhoneNum", phoneNum);

        System.out.println(codeMap.toString());

        // TODO 添加发送短信逻辑
//        if (!sendSMS(phone, codeTemp.toString(), "IN PHOTO管理员系统绑定手机号", "SMS_61155105")) {
//
//            return createResult(false, "发送失败，请联系管理员查看短信服务器状态");
//        }

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

        Map<String, String> codeMap = (Map<String, String>) session.getAttribute("addPhoneCode");

        String sessionCode = codeMap.get(phone);

        if ("".equals(sessionCode) || sessionCode == null) {
            return createResult(false, "未找到对应的验证码!");
        }

        if (!code.equals(sessionCode)) {
            return createResult(false, "验证码不正确!");
        }

        // 更新数据
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        adminInfo.setPhone(phone);

        // 写入数据
        if (!utilDao.update(adminInfo)) {
            return createResult(false, "写入数据是发生了错误，请稍候再试");
        }

        return createResult(true, "绑定手机号成功");
    }

}
