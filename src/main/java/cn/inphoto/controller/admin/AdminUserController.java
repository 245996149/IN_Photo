package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.CategoryDao;
import cn.inphoto.dao.RoleDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.dbentity.page.AdminPage;
import cn.inphoto.dbentity.user.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.MD5Util.getMD5;
import static cn.inphoto.util.MailUtil.sendMail;
import static cn.inphoto.util.PasswordUtil.getRandomPassword;
import static cn.inphoto.util.ResultMapUtil.createResult;

@RequestMapping("/admin/adminManage")
@Controller
@SessionAttributes("adminPage")
public class AdminUserController {

    @Resource
    private AdminDao adminDao;

    @Resource
    private UtilDao utilDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private RoleDao roleDao;

    /*
   注册邮件中的IN Photo管理中心地址
    */
    @Value("#{properties['AdminEmailManageAdd']}")
    String emailManageAdd;

    @Value("#{properties['sendEmail']}")
    boolean sendEmail;

    /**
     * 前往用户管理页面
     *
     * @param adminPage 页面分页对象
     * @param model     页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toAdmin.do")
    public String toAdmin(AdminPage adminPage, Model model) {

        adminPage.setRows(adminDao.countByPage(adminPage));

        List<AdminInfo> adminInfoList = adminDao.findByPage(adminPage);

        model.addAttribute("adminList", adminInfoList);
        model.addAttribute("adminPage", adminPage);

        return "admin/admin/admin_list";
    }

    /**
     * 更新管理员账户状态
     *
     * @param admin_id 管理员id
     * @return 是否成功
     */
    @RequestMapping("/changeAdminState.do")
    @ResponseBody
    public Map changeAdminState(Integer admin_id, String admin_state) {

        // 判断传入的是否是管理员
        if (admin_id == 1) {
            return createResult(false, "该管理员不能操作");
        }

        // 查找对象
        AdminInfo adminInfo = adminDao.findByAdmin_id(admin_id);

        if (adminInfo == null) {
            return createResult(false, "传入的参数不正确");
        }

        // 更新数据
        if (AdminInfo.ADMIN_STATE_NORMAL.equals(admin_state)) {
            adminInfo.setAdminStatu(AdminInfo.ADMIN_STATE_NORMAL);
        } else if (AdminInfo.ADMIN_STATE_STOP.equals(admin_state)) {
            adminInfo.setAdminStatu(AdminInfo.ADMIN_STATE_STOP);
        } else {
            return createResult(false, "传入的参数不正确");
        }

        // 写入数据
        if (!utilDao.update(adminInfo)) {
            return createResult(true, "在更新数据时发生了错误，请稍候重试");
        }

        return createResult(true, "操作成功");
    }

    /**
     * 前往添加管理员页面
     *
     * @param adminInfo  对象
     * @param user_state 状态
     * @return 页面
     */
    @RequestMapping("/toAddAdmin.do")
    public String toAddAdmin(AdminInfo adminInfo, boolean user_state) {

        // 设置管理员状态
        if (user_state) {
            adminInfo.setAdminStatu(AdminInfo.ADMIN_STATE_NORMAL);
        } else {
            adminInfo.setAdminStatu(AdminInfo.ADMIN_STATE_STOP);
        }

        // 生成10位数随机密码
        String pwd = getRandomPassword(10);

        adminInfo.setPassword(getMD5(pwd));

        if (utilDao.save(adminInfo)) {

            // 发送邮件
            if (sendEmail) {
                try {
                    sendMail(adminInfo.getEmail(), "IN Photo注册邮件",
                            "<div>尊敬的" + adminInfo.getEmail() + "您好！ 感谢您成为IN Photo管理员中的一员。</div>" +
                                    "<div><includetail><p>我们将为您提供最贴心的服务，祝您使用愉快！</p>" +
                                    "<p>您在IN Photo管理中心的登录帐号：</p><p>帐号：" + adminInfo.getEmail() + "</p>" +
                                    "<p>密码：" + pwd + "</p><p>请您及时登录系统填写用户名，更改密码。</p>" +
                                    "<p><a href='" + emailManageAdd + "'>点击前往IN Photo管理员系统</a></p>" +
                                    "<p>此邮件为系统自动发送，请勿直接回复该邮件</p></includetail></div>");
                } catch (Exception e) {
                    e.printStackTrace();
                    // 保存失败，跳转到错误页面
                    return "redirect:/admin/login/error.do";
                }
            }

        } else {

            // 保存失败，跳转到错误页面
            return "redirect:/admin/login/error.do";

        }

        return "redirect:toUpdateAdmin.do?admin_id=" + adminInfo.getAdminId();
    }

    /**
     * 前往更新管理员页面
     *
     * @param admin_id 管理员id
     * @param model    页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toUpdateAdmin.do")
    public String toUpdateAdmin(Integer admin_id, Model model) {

        AdminInfo adminInfo = adminDao.findByAdmin_id(admin_id);

        List<Category> categoryList = categoryDao.findAll();

        List<RoleInfo> roleInfoList = roleDao.findAllRole();

        model.addAttribute("admin", adminInfo);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("roleList", roleInfoList);

        return "admin/admin/admin_update";
    }

    /**
     * 更新管理员数据
     *
     * @param adminInfo 管理员对象
     * @return 成功与否页面
     */
    @RequestMapping("updateAdmin.do")
    public String updateAdmin(AdminInfo adminInfo) {

        // 初始账户，不能更改
        if (adminInfo.getAdminId() == 1) {
            return "no_power";
        }

        // 查找对象
        AdminInfo a = adminDao.findByAdmin_id(adminInfo.getAdminId());

        if (a == null) {
            return "redirect:/admin/login/error.do";
        }

        // 更新数据
        a.setCategorySet(new HashSet<>(categoryDao.findByCategoryIds(adminInfo.getCategoryIds())));
        a.setRoleInfoSet(new HashSet<>(roleDao.findByRoleIds(adminInfo.getRoleIds())));

        // 写入数据
        if (adminDao.updateAdmin(a)) {
            return "redirect:toAdmin.do";
        } else {
            return "redirect:/admin/login/error.do";
        }
    }

}
