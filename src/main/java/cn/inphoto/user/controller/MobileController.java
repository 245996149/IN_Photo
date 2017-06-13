package cn.inphoto.user.controller;

import cn.inphoto.user.dao.*;
import cn.inphoto.user.dbentity.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 移动端控制器
 * Created by kaxia on 2017/6/13.
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {

    private Logger logger = Logger.getLogger(MobileController.class);

    @Resource
    UserDao userDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    WebinfoDao webinfoDao;

    @Resource
    MediaCodeDao mediaCodeDao;

    @Resource
    MediaDataDao mediaDataDao;

    /*定义404页面*/
    private static final String MOBILE_404 = "mobile/404";
    /*定义默认提取页面*/
    private static final String MOBILE_CODE_DEFAULT = "mobile/code_default";
    /*定义自定义提取页面*/
    private static final String MOBILE_CODE = "mobile/code";
    /*定义自定义展示页面*/
    private static final String MOBILE_VIEW = "mobile/view";
    /*定义默认展示页面*/
    private static final String MOBILE_VIEW_DEFAULT = "mobile/view_default";

    @RequestMapping("/toCode.do")
    public String toCode(Long user_id, Integer category_id) {

        // 判断接收到的参数是否为空
        if (user_id == null || category_id == null) return MOBILE_404;

        // 查询user_id对应的用户
        UsersEntity user = userDao.findByUser_id(user_id);

        // 判断查询到的用户是否存在
        if (user == null) return MOBILE_404;

        // 根据user_id、category_id查询状态正常的用户套餐系统
        UserCategoryEntity userCategory = userCategoryDao.findByUser_idAndCategory_id(
                user.getUserId(), category_id, UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

        // 判断用户套餐系统是否存在
        if (userCategory == null) return MOBILE_404;

        // 查询用户的提取页面设置
        CodeWebinfoEntity codeWebinfo = webinfoDao.findCodeByUser_idAndCategory_id(
                user.getUserId(), userCategory.getCategoryId(), CodeWebinfoEntity.CODE_WEB_INFO_STATE_NORMAL);

        // 输出日志
        MDC.put("user_id", user_id);
        MDC.put("category_id", category_id);
        logger.info("用户打开了user_id=" + user_id + "，category_id=" + category_id + "的提取页面");

        // 判断用户提取页面是否有效，无效跳转到默认页面
        if (codeWebinfo == null) return MOBILE_CODE_DEFAULT;

        return MOBILE_CODE;

    }

    @RequestMapping("checkCode.do")
    @ResponseBody
    public Map<String, Object> checkCode(Long user_id, Integer category_id, String code) {

        Map<String, Object> result = new HashMap<>();

        // 查询验证码
        MediaCodeEntity mediaCode = mediaCodeDao.findByUser_idAndCategory_idAndMedia_code(
                user_id, category_id, code);

        // 判断验证码是否有效
        if (mediaCode == null) {

            result.put("success", false);
            result.put("message", "未找到对应的验证码，请确认验证码是否正确");

        } else {

            result.put("success", true);
            result.put("media_id", mediaCode.getMediaId());

        }

        // 输出日志
        MDC.put("user_id", user_id);
        MDC.put("category_id", category_id);
        logger.info("用户验证了user_id=" + user_id + "，category_id=" + category_id + ",code=" + code + "的验证码，验证返回信息为：" + result.toString());

        return result;

    }

    @RequestMapping("/toPage.do")
    public String toPage(Long user_id, Integer category_id, Long media_id) {

        // 判断接收到的参数是否为空
        if (user_id == null || category_id == null || media_id == null) return MOBILE_404;

        // 查询user_id对应的用户
        UsersEntity user = userDao.findByUser_id(user_id);

        // 判断查询到的用户是否存在
        if (user == null) return MOBILE_404;

        // 根据user_id、category_id查询状态正常的用户套餐系统
        UserCategoryEntity userCategory = userCategoryDao.findByUser_idAndCategory_id(
                user.getUserId(), category_id, UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

        // 判断用户套餐系统是否存在
        if (userCategory == null) return MOBILE_404;


        // 查询媒体数据，并判断媒体数据是否在正常状态内
        MediaDataEntity mediaData = mediaDataDao.findByMedia_id(media_id);
        if (mediaData == null || !MediaDataEntity.MEDIA_STATE_NORMAL.equals(mediaData.getMediaState()))
            return MOBILE_404;

        // 查询用户展示页面设置
        PicWebinfoEntity picWebinfo = webinfoDao.findPicByUser_idAndCategory_id(
                user.getUserId(), userCategory.getCategoryId(), PicWebinfoEntity.PIC_WEB_INFO_STATE_NORMAL);

        // 输出日志
        MDC.put("user_id", user_id);
        MDC.put("category_id", category_id);
        logger.info("用户打开了了user_id=" + user_id + "，category_id=" + category_id + ",media_id=" + media_id + "的展示页面");

        // 判断展示页面设置是否有效，无效打开默认页面
        if (picWebinfo == null) return MOBILE_VIEW_DEFAULT;

        return MOBILE_VIEW;

    }

}
