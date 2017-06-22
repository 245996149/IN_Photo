package cn.inphoto.user.controller;

import cn.inphoto.user.dao.*;
import cn.inphoto.user.dbentity.*;
import cn.inphoto.user.weChatEntity.JsapiTicket;
import cn.inphoto.user.weChatUtil.Sha1;
import cn.inphoto.user.weChatUtil.WeChatWebUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static cn.inphoto.user.util.DirUtil.getConfigInfo;
import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

/**
 * 移动端控制器
 * Created by kaxia on 2017/6/13.
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {

    private Logger logger = Logger.getLogger(MobileController.class);

    String appid = getConfigInfo("appid");

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
    public String toCode(Long user_id, Integer category_id, Model model, boolean test) {

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

        String codeState = CodeWebinfoEntity.CODE_WEB_INFO_STATE_NORMAL;

        if (test) {
            codeState = CodeWebinfoEntity.CODE_WEB_INFO_STATE_PREVIEW;
        }

        // 查询用户的提取页面设置
        CodeWebinfoEntity codeWebinfo = webinfoDao.findCodeByUser_idAndCategory_id(
                user.getUserId(), userCategory.getCategoryId(), codeState);

        // 输出日志
        MDC.put("user_id", user_id);
        MDC.put("category_id", category_id);
        logger.info("用户打开了user_id=" + user_id + "，category_id=" + category_id + "的提取页面");

        model.addAttribute("userCategory", userCategory);
        model.addAttribute("test", test);

        // 判断用户提取页面是否有效，无效跳转到默认页面
        if (codeWebinfo == null) return MOBILE_CODE_DEFAULT;

        model.addAttribute("codeWebinfo", codeWebinfo);

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
    public String toPage(Long user_id, Integer category_id, Long media_id, Model model, boolean test) {

        // 判断是否在测试模式
        if (test) {
            if (user_id == null || category_id == null) return MOBILE_404;
        } else {
            // 判断接收到的参数是否为空
            if (user_id == null || category_id == null || media_id == null) return MOBILE_404;
        }

        try {

            // 查询user_id对应的用户
            UsersEntity user = userDao.findByUser_id(user_id);

            // 判断查询到的用户是否存在
            if (user == null) return MOBILE_404;

            // 根据user_id、category_id查询状态正常的用户套餐系统
            UserCategoryEntity userCategory = userCategoryDao.findByUser_idAndCategory_id(
                    user.getUserId(), category_id, UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

            // 判断用户套餐系统是否存在
            if (userCategory == null) return MOBILE_404;

            String picState = PicWebinfoEntity.PIC_WEB_INFO_STATE_NORMAL;

            // 判断是否在测试模式，在测试模式将media_id设置为0
            if (test) {
                model.addAttribute("media_id", 0);
                picState = PicWebinfoEntity.PIC_WEB_INFO_STATE_PREVIEW;
            } else {
                // 查询媒体数据，并判断媒体数据是否在正常状态内
                MediaDataEntity mediaData = mediaDataDao.findByMedia_id(media_id);
                if (mediaData == null || !MediaDataEntity.MEDIA_STATE_NORMAL.equals(mediaData.getMediaState()))
                    return MOBILE_404;

                model.addAttribute("media_id", mediaData.getMediaId());
            }

            // 查询用户展示页面设置
            PicWebinfoEntity picWebinfo = webinfoDao.findPicByUser_idAndCategory_id(
                    user.getUserId(), userCategory.getCategoryId(), picState);

            ShareInfoEntity shareInfoEntity = webinfoDao.findShareByUser_idAndCategory(user_id, category_id);

            model.addAttribute("shareInfoEntity", shareInfoEntity);
            model.addAttribute("url", getConfigInfo("url"));
            model.addAttribute("test", test);

            // 输出日志
            MDC.put("user_id", user_id);
            MDC.put("category_id", category_id);
            logger.info("用户打开了了user_id=" + user_id + "，category_id=" + category_id + ",media_id=" + media_id + "的展示页面");

            // 判断展示页面设置是否有效，无效打开默认页面
            if (picWebinfo == null) return MOBILE_VIEW_DEFAULT;

            model.addAttribute("category_id", userCategory.getCategoryId());
            model.addAttribute("user_id", user.getUserId());
            model.addAttribute("picWebinfo", picWebinfo);

        } catch (Exception e) {
            e.printStackTrace();
            return MOBILE_404;
        }

        return MOBILE_VIEW;

    }

    /**
     * 生成获取微信jssdk所需参数反馈给客户端
     *
     * @param response
     * @param request
     * @param url      当前页面url
     * @return
     */
    @RequestMapping("/getWeChatInfo.do")
    @ResponseBody
    public Map<String, String> weChat(HttpServletResponse response, HttpServletRequest request, String url) {

        logger.info("接收到生成获取微信jssdk的请求---------->请求的url为：" + url);

        return WXConfig(request, response, url);

    }

    /**
     * 获取微信jssdk所需参数
     *
     * @param request
     * @param response
     * @return
     */
    private HashMap<String, String> WXConfig(HttpServletRequest request, HttpServletResponse response, String url) {

        HashMap<String, String> res = new HashMap<>();

        String time = null;

        String jsapi_ticket = null;

        String signature = "";

        String nonce_str = "";

        try {

            JsapiTicket ticket = WeChatWebUtil.judgeJsapiTicketOvertime(request, response);
            // 获取随机数
            nonce_str = UUID.randomUUID().toString();
            // 获取jsapi_ticket
            jsapi_ticket = ticket.getTicket();
            // 获取系统时间
            time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
            // 合成字符串用于签名
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + time + "&url=" + url;

            signature = Sha1.getSha1(str);

            logger.info("获取微信jssdk参数---------->合成的字符串为：" + str + "；签名后的字符串为：" + signature);

        } catch (Exception e) {

            e.printStackTrace();
            logger.info("获取微信jssdk参数---------->发生未知错误，错误信息为：" + getErrorInfoFromException(e));

        }

        res.put("appid", appid);
        res.put("url", url);
        res.put("jsapi_ticket", jsapi_ticket);
        res.put("nonceStr", nonce_str);
        res.put("timestamp", time);
        res.put("signature", signature);

        logger.info("获取微信jssdk参数---------->返回的参数为：" + res.toString());

        return res;

    }


}
