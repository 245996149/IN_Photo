package cn.inphoto.user.controller;

import cn.inphoto.user.dao.MediaDataDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dbentity.MediaDataEntity;
import cn.inphoto.user.dbentity.UserCategoryEntity;
import cn.inphoto.user.dbentity.UsersEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaxia on 2017/6/21.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    MediaDataDao mediaDataDao;

    @RequestMapping("/toCategory.do")
    public String toCategory(HttpSession session, Model model) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        List<UserCategoryEntity> userCategoryList = userCategoryDao.findByUser_id(user.getUserId());

        List<UserCategoryEntity> myUserCategoryList = (List<UserCategoryEntity>) session.getAttribute("allUserCategory");

        Map<Long, Integer> tempMap = new HashMap<>();

        for (UserCategoryEntity uc : myUserCategoryList
                ) {

            int a = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                    user.getUserId(), uc.getCategoryId(), MediaDataEntity.MEDIA_STATE_NORMAL);
            // System.out.println(a + "   " + uc.getMediaNumber());
            tempMap.put(uc.getUserCategoryId(), (a * 100 / uc.getMediaNumber()));
            //System.out.println(tempMap.get(uc.getUserCategoryId()));

        }

        model.addAttribute("tempMap", tempMap);
        model.addAttribute("userCategoryList", userCategoryList);

        session.setAttribute("nav_code", UserController.CATEGORY_CODE);

        return "user/category";
    }


}
