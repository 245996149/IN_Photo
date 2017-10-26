package cn.inphoto.controller.admin;

import cn.inphoto.dao.CategoryDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dbentity.user.Category;
import org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/categoryManage")
public class AdminCategoryController {


    @Resource
    private CategoryDao categoryDao;

    @Resource
    private UtilDao utilDao;

    /**
     * 前往套餐管理页
     *
     * @param model 页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toCategory.do")
    public String toClient(Model model) {

        List<Category> categoryList = categoryDao.findAll();

        model.addAttribute("categoryList", categoryList);

        return "admin/category/category_list";
    }

    /**
     * 新增一个套餐
     *
     * @param name     套餐名称
     * @param code     套餐简码
     * @param note     套餐说明
     * @param makeGIF  是否生成GIF
     * @param gif_tran GIF是否透明
     * @return 是否成功
     */
    @RequestMapping("/addCategory.do")
    @ResponseBody
    public Map addCategory(String name, String code, String note, Boolean makeGIF, Boolean gif_tran, Boolean isVideo) {
        Map<String, Object> result = new HashMap<>();

        // 判断套餐简码是否已经存在，存在返回错误
        Category category = categoryDao.findByCategory_code(code);

        if (category != null) {

            result.put("success", false);
            result.put("message", "您输入的套餐简码已经存在套餐，请更换套餐简码");
            return result;

        }

        category = new Category();
        category.setCategoryName(name);
        category.setCategoryCode(code);
        category.setCategoryNote(note);
        category.setMadeGif((byte) 0);
        category.setGifTransparency((byte) 0);
        category.setIsVideo((byte) 1);
        if (!isVideo) {
            if (makeGIF) {
                category.setMadeGif((byte) 1);
                if (gif_tran) {
                    category.setGifTransparency((byte) 1);
                }
            }
        }

        if (utilDao.save(category)) {

            result.put("success", true);
            result.put("message", "添加成功");

        } else {

            result.put("success", false);
            result.put("message", "在新增数据时发生了小错误。");

        }

        return result;
    }

    /**
     * 前往套餐更新页面
     *
     * @param category_id 套餐id
     * @param model       页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toUpdateCategory.do")
    public String toUpdateCategory(Integer category_id, Model model) {

        Category category = categoryDao.findByCategory_id(category_id);

        model.addAttribute("category", category);

        return "admin/category/category_update";
    }

    /**
     * 更新套餐信息
     *
     * @param category 套餐对象
     * @return 跳转页面
     */
    @RequestMapping("/updateCategory.do")
    public String updateCategory(Category category) {

        if (utilDao.update(category)) {
            return "redirect:toCategory.do";
        }

        return "error";
    }

}
