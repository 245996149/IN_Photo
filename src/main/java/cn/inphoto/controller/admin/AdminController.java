package cn.inphoto.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 17-7-11.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index.do")
    public String index() {
        return "admin/index";
    }

}
