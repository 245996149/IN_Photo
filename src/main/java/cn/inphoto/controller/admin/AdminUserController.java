package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.AdminPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/admin/userManage")
@Controller
@SessionAttributes("adminPage")
public class AdminUserController {

    @Resource
    AdminDao adminDao;

    @RequestMapping("/toUser.do")
    public String toUser(AdminPage adminPage, Model model) {

        adminPage.setRows(adminDao.countByPage(adminPage));

        List<AdminInfo> adminInfoList = adminDao.findByPage(adminPage);

        model.addAttribute("adminList", adminInfoList);
        model.addAttribute("adminPage", adminPage);

        return "admin/user/user_list";
    }

}
