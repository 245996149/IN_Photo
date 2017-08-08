package cn.inphoto.controller.admin;

import cn.inphoto.dao.ModuleDao;
import cn.inphoto.dao.RoleDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.ModuleInfo;
import cn.inphoto.dbentity.admin.RoleInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/admin/roleManage")
@Controller
public class RoleController {

    @Resource
    private RoleDao roleDao;

    @Resource
    private ModuleDao moduleDao;

    @Resource
    private UtilDao utilDao;

    @RequestMapping("/toRole.do")
    public String toRole(Model model) {

        List<RoleInfo> roleInfos = roleDao.findAllRole();

        model.addAttribute("roleList", roleInfos);

        return "admin/role/role_list";
    }

    @RequestMapping("/deleteRole.do")
    @ResponseBody
    public Map deleteRole(Integer role_id) {

        Map<String, Object> result = new HashMap<>();

        if (role_id == 1) {
            result.put("success", false);
            result.put("message", "该角色为系统初始管理员角色，无法删除");
            return result;
        }


        return result;
    }

    @RequestMapping("/toAddRole.do")
    public String toAddRole(Model model) {

        List<ModuleInfo> moduleInfoList = moduleDao.findAll();

        model.addAttribute("moduleList", moduleInfoList);

        return "admin/role/role_add";
    }

    @RequestMapping("/addRole.do")
    public String addRole(RoleInfo roleInfo) {

        System.out.println(utilDao.save(roleInfo));

        return "redirect:toRole.do";
    }

}
