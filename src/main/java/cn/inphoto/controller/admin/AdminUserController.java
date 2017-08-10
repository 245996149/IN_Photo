package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.AdminPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/userManage")
@Controller
@SessionAttributes("adminPage")
public class AdminUserController {

    @Resource
    AdminDao adminDao;

    @Resource
    UtilDao utilDao;

    /**
     * 前往用户管理页面
     *
     * @param adminPage 页面分页对象
     * @param model     页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toUser.do")
    public String toUser(AdminPage adminPage, Model model) {

        adminPage.setRows(adminDao.countByPage(adminPage));

        List<AdminInfo> adminInfoList = adminDao.findByPage(adminPage);

        model.addAttribute("adminList", adminInfoList);
        model.addAttribute("adminPage", adminPage);

        return "admin/user/user_list";
    }

    /**
     * 停用用户
     *
     * @param admin_id 停用的用户id
     * @return 是否成功
     */
    @RequestMapping("/stopUser.do")
    @ResponseBody
    public Map stopUser(Integer admin_id) {
        Map<String, Object> result = new HashMap<>();

        if (admin_id == 1) {
            result.put("success", false);
            result.put("message", "该管理员不能操作");
            return result;
        }

        AdminInfo adminInfo = adminDao.findByAdmin_id(admin_id);

        adminInfo.setAdminStatu(AdminInfo.ADMIN_STATE_STOP);

        if (utilDao.update(adminInfo)) {
            result.put("success", true);
            result.put("message", "操作成功");
        } else {
            result.put("success", false);
            result.put("message", "在更新数据时发生了错误，请稍候重试");
        }

        return result;
    }



}
