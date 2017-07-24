package cn.inphoto.controller.admin;

import cn.inphoto.dbentity.admin.AdminInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/clientManage")
public class ClientController {

    @RequestMapping("/toClient.do")
    public String toClient(HttpSession session) {
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        return "admin/client";
    }

}
