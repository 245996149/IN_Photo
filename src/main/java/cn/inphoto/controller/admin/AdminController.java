package cn.inphoto.controller.admin;

import cn.inphoto.dao.AdminDao;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-7-11.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    AdminDao adminDao;

    @RequestMapping("/index.do")
    public String index() {
        return "admin/index";
    }

    @RequestMapping("/noPower.do")
    public String noPower() {
        return "no_power";
    }

    @ResponseBody
    @RequestMapping("/checkEmail.do")
    public Map checkEmail(String email) {

        Map<String, Object> result = new HashMap<>();

        AdminInfo adminInfo = adminDao.findByEmail(email);

        if (adminInfo != null) {
            result.put("success", false);
            result.put("message", "已经存在该email");
            return result;
        }

        result.put("success", true);
        result.put("message", "通过email验证");
        return result;

    }

}
