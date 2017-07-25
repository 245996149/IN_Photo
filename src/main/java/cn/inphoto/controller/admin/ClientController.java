package cn.inphoto.controller.admin;

import cn.inphoto.dao.ClientDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/clientManage")
@SessionAttributes("userPage")
public class ClientController {

    @Resource
    ClientDao clientDao;

    @Resource
    UserDao userDao;


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

    @RequestMapping("/toAddClient.do")
    public String toAddClient(Model model, HttpSession session) {

        return "admin/client_add";
    }

}
