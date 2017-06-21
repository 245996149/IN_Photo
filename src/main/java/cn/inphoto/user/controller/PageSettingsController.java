package cn.inphoto.user.controller;

import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dbentity.PicWebinfoEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static cn.inphoto.user.util.DBUtil.selectTodayData;
import static cn.inphoto.user.util.DirUtil.createSettingsPic;

/**
 * Created by kaxia on 2017/6/14.
 */
@RequestMapping("/setting")
@Controller
public class PageSettingsController {

    @Resource
    ShareDataDao shareDataDao;

    @RequestMapping("/toPageSettings.do")
    public String toPageSettings(int category_id, Model model, HttpSession session) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        model.addAttribute("category_id", category_id);

        session.setAttribute("nav_code", UserController.PAGESETTINGS_CODE);

        return "user/page_settings";
    }

    @RequestMapping("/test.do")
    @ResponseBody
    public Map method2(HttpSession session, @RequestParam MultipartFile show_pic_bg,
                       PicWebinfoEntity picWebinfoEntity) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        try {

            String filePath = createSettingsPic(show_pic_bg, usersEntity);

            picWebinfoEntity.setBackground(filePath);


            System.out.println(picWebinfoEntity.toString());


        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", true);
            result.put("message", "aaaaaaaaaaaaaaa");
            return result;
        }

        result.put("success", true);
        result.put("message", picWebinfoEntity.toString());
        return result;

    }

}
