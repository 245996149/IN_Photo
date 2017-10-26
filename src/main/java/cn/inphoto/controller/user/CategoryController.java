package cn.inphoto.controller.user;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.UserCategory;
import cn.inphoto.dbentity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by kaxia on 2017/6/21.
 */
@Controller
@RequestMapping("/user/category")
public class CategoryController {

    @Resource
    private UserCategoryDao userCategoryDao;

    @Resource
    private MediaDataDao mediaDataDao;

    /**
     * 打开套餐管理页面
     *
     * @param session 服务器缓存
     * @param model 页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toCategory.do")
    public String toCategory(HttpSession session, Model model) {

        // 获取用户信息
        User user = (User) session.getAttribute("loginUser");

        // 找到所有用户套餐
        List<UserCategory> userCategoryList = userCategoryDao.findByUser_id(user.getUserId());

        // 从session中找到所有生效的用户套餐
        List<UserCategory> myUserCategoryList = (List<UserCategory>) session.getAttribute("allUserCategory");

        Map<Long, Integer> tempMap = new HashMap<>();

        // 遍历生效的套餐
        for (UserCategory uc : myUserCategoryList
                ) {

            // 查询套餐对应的正常状态的媒体总数
            int a = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                    user.getUserId(), uc.getCategoryId(), Collections.singletonList(MediaData.MediaState.Normal));

            // 将数据写入临时Map中以供页面调用
            tempMap.put(uc.getUserCategoryId(), (a * 100 / uc.getMediaNumber()));

        }

        model.addAttribute("tempMap", tempMap);
        model.addAttribute("userCategoryList", userCategoryList);

        session.setAttribute("nav_code", UserController.CATEGORY_CODE);

        return "user/category";
    }


}
