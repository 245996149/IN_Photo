package cn.inphoto.controller.admin;

import cn.inphoto.dao.ModuleDao;
import cn.inphoto.dao.RoleDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.admin.AdminInfo;
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

    /**
     * 前往角色管理页面
     *
     * @param model 页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toRole.do")
    public String toRole(Model model) {

        List<RoleInfo> roleInfos = roleDao.findAllRole();

        model.addAttribute("roleList", roleInfos);

        return "admin/role/role_list";
    }

    /**
     * 删除角色
     *
     * @param role_id 角色id
     * @return 是否成功
     */
    @RequestMapping("/deleteRole.do")
    @ResponseBody
    public Map deleteRole(Integer role_id) {

        Map<String, Object> result = new HashMap<>();

        if (role_id == 1) {
            result.put("success", false);
            result.put("message", "该角色为系统初始管理员角色，无法删除");
            return result;
        }

        // 判断用户是否存在该角色
        List<AdminInfo> adminInfoList = roleDao.findAdminByRole_id(role_id);

        if (!adminInfoList.isEmpty()) {
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < adminInfoList.size(); i++) {
                AdminInfo a = adminInfoList.get(i);
                if (i == adminInfoList.size() - 1) {
                    temp.append(a.getAdminName());
                } else {
                    temp.append(a.getAdminName()).append("、");
                }
            }
            result.put("success", false);
            result.put("message", "用户：" + temp + " 角色列表中存在该角色，请清除后再删除该角色");
            return result;
        }

        // 删除角色
        if (!roleDao.deleteRole(role_id)) {
            result.put("success", false);
            result.put("message", "在修改数据时发生了错误，请稍后再试");
        } else {
            result.put("success", true);
            result.put("message", "删除成功");
        }

        return result;
    }

    /**
     * 前往添加角色页面
     *
     * @param model 页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toAddRole.do")
    public String toAddRole(Model model) {

        List<ModuleInfo> moduleInfoList = moduleDao.findAll();

        model.addAttribute("moduleList", moduleInfoList);

        return "admin/role/role_add";
    }

    /**
     * 添加角色
     *
     * @param roleInfo 角色对象
     * @return 跳转
     */
    @RequestMapping("/addRole.do")
    public String addRole(RoleInfo roleInfo) {

        roleInfo.setModuleInfoSet(new HashSet<>(moduleDao.findByModuleIds(roleInfo.getModuleIds())));

        if (utilDao.save(roleInfo)) {
            return "redirect:toRole.do";
        } else {
            return "redirect:/admin/login/error.do";
        }

    }

    /**
     * 前往更新页面
     *
     * @param role_id 角色id
     * @param model   页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toUpdateRole.do")
    public String toUpdateRole(Integer role_id, Model model) {

        RoleInfo roleInfo = roleDao.findByRole_id(role_id);

        List<ModuleInfo> moduleInfoList = moduleDao.findAll();

        model.addAttribute("role", roleInfo);
        model.addAttribute("moduleList", moduleInfoList);

        return "admin/role/role_update";
    }

    /**
     * 更新角色信息
     *
     * @param roleInfo 角色对象
     * @return 跳转
     */
    @RequestMapping("/updateRole.do")
    public String updateRole(RoleInfo roleInfo) {

        roleInfo.setModuleInfoSet(new HashSet<>(moduleDao.findByModuleIds(roleInfo.getModuleIds())));

        if (roleDao.updateRole(roleInfo)) {
            return "redirect:toRole.do";
        } else {
            return "redirect:/admin/login/error.do";
        }
    }

}
