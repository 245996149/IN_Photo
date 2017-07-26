package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.ClientDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.Category;
import cn.inphoto.dbentity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static cn.inphoto.util.MD5Util.getMD5;
import static cn.inphoto.util.MailUtil.sendMail;

@Controller
@RequestMapping("/admin/clientManage")
@SessionAttributes("userPage")
public class ClientController {

    @Resource
    ClientDao clientDao;

    @Resource
    UserDao userDao;

    @Resource
    UtilDao utilDao;

    @Resource
    AdminDao adminDao;

    static char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 前往客户管理页
     *
     * @param model
     * @param session
     * @param userPage
     * @return 页面
     */
    @RequestMapping("/toClient.do")
    public String toClient(Model model, HttpSession session, UserPage userPage) {
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        List<RoleInfo> roleInfos = clientDao.findRoleByAdminId(adminInfo.getAdminId());

        /*
        判断是否有管理员权限
         */
        boolean flag = false;

        for (RoleInfo r : roleInfos
                ) {
            if (r.getRoleId() == 1) {
                flag = true;
                break;
            }
        }

        if (flag) {
            userPage.setAdminId(0);
        } else {
            userPage.setAdminId(adminInfo.getAdminId());
        }

        userPage.setRows(userDao.countByPage(userPage));

        List<User> users = userDao.findByPage(userPage);

        for (User u : users
                ) {
            System.out.println(u.toString());
        }

        model.addAttribute("userList", users);
        model.addAttribute("userPage", userPage);

        return "admin/client_list";
    }

    /**
     * 验证数据库中是否存在该邮箱地址的客户
     *
     * @param email 邮箱地址
     * @return 是否存在
     */
    @RequestMapping("checkEmail.do")
    @ResponseBody
    public Map checkEmail(String email) {
        Map<String, Object> result = new HashMap<>();

        User user = userDao.findByEmail(email);

        if (user != null) {
            result.put("success", false);
            result.put("message", "已经存在该email");
            return result;
        }

        result.put("success", true);
        result.put("message", "通过email验证");
        return result;
    }

    /**
     * 前往添加客户页
     *
     * @param model
     * @param session
     * @param user    客户
     * @return 页面
     */
    @RequestMapping("/toAddClient.do")
    public String toAddClient(Model model, HttpSession session, User user, boolean category) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        System.out.println(user.toString());
        System.out.println(category);

        final int maxNum = 62;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 10) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        user.setPassword(getMD5(pwd.toString()));
        user.setAdminId(adminInfo.getAdminId());
        user.setUserState("0");

        System.out.println(pwd.toString());
        System.out.println("加密后密码：" + getMD5(pwd.toString()));

        // 发送邮件
//        try {
//            sendMail(user.getEmail(), "IN Photo注册邮件",
//                    "<div>尊敬的" + user.getEmail() + "您好！ 感谢您成功注册IN Photo的会员。</div>" +
//                            "<div><includetail><p>我们将为您提供最贴心的服务，祝您使用愉快！</p>" +
//                            "<p>您在IN Photo管理中心的登录帐号：</p><p>帐号：" + user.getEmail() + "</p>" +
//                            "<p>密码：" + pwd.toString() + "</p><p>请您及时登录系统更改密码。</p>" +
//                            "<p><a href='http://www.baidu.com'>点击前往IN Photo管理中心</a></p>" +
//                            "<p>此邮件为系统自动发送，请勿直接回复该邮件</p></includetail></div>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        utilDao.save(user);

        System.out.println(user.toString());

        if (category) {
            return "redirect:toUpdateCategory.do?user_id=" + user.getUserId();
        } else
            return "redirect:toClient.do";
    }

    @RequestMapping("/toUpdateCategory.do")
    @ResponseBody
    public String toUpdateCategory(Long user_id, Model model, HttpSession session) {
//        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        User user = userDao.findByUser_id(user_id);

        List<Category> categoryList = adminDao.findCategoryByAdmin(user.getAdminId());

        model.addAttribute("user", user);
        model.addAttribute("categoryList", categoryList);

        for (Category c: categoryList
             ) {
            System.out.println(c.toString());
        }

        return "admin/client_update_category";
    }

}
