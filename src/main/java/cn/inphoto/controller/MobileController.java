package cn.inphoto.controller;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.weChatEntity.JsapiTicket;
import cn.inphoto.weChatUtil.Sha1;
import cn.inphoto.weChatUtil.WeChatWebUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Value;
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

import static cn.inphoto.util.DirUtil.getErrorInfoFromException;
import static cn.inphoto.util.ResultMapUtil.createResult;

/**
 * 移动端h5页面控制器
 * Created by kaxia on 2017/6/13.
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {

    private Logger logger = Logger.getLogger(MobileController.class);

    @Value("#{properties['appid']}")
    String appid;

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

    @Resource
    UtilDao utilDao;

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

    /**
     * 前面提取页面
     *
     * @param user_id     用户id
     * @param category_id 套餐id
     * @param model       页面数据存储对象
     * @param test        是否为测试状态
     * @return 页面地址
     */
    @RequestMapping("/toCode.do")
    public String toCode(Long user_id, Integer category_id, Model model, boolean test) {

        // 判断接收到的参数是否为空
        if (user_id == null || category_id == null) return MOBILE_404;

        // 查询user_id对应的用户
        User user = userDao.findByUser_id(user_id);

        // 判断查询到的用户是否存在
        if (user == null) return MOBILE_404;

        // 根据user_id、category_id查询状态正常的用户套餐系统
        UserCategory userCategory = userCategoryDao.findByUser_idAndCategory_idAndState(
                user.getUserId(), category_id, UserCategory.USER_CATEGORY_STATE_NORMAL);

        // 判断用户套餐系统是否存在
        if (userCategory == null) return MOBILE_404;

        String codeState = CodeWebInfo.CODE_WEB_INFO_STATE_NORMAL;

        if (test) {
            codeState = CodeWebInfo.CODE_WEB_INFO_STATE_PREVIEW;
        }

        // 查询用户的提取页面设置
        CodeWebInfo codeWebinfo = webinfoDao.findCodeByUser_idAndCategory_id(
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

    /**
     * 校验提取码是否正确
     *
     * @param user_id     用户id
     * @param category_id 套餐id
     * @param code        验证码
     * @param request     Request对象
     * @return 校验结果
     */
    @RequestMapping("checkCode.do")
    @ResponseBody
    public Map<String, Object> checkCode(Long user_id, Integer category_id, String code, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        // 查询验证码
        MediaCode mediaCode = mediaCodeDao.findByUser_idAndCategory_idAndMedia_code(
                user_id, category_id, code);

        // 判断验证码是否有效
        if (mediaCode == null) {

            result.put("success", false);
            result.put("message", "未找到对应的验证码，请确认验证码是否正确");

        } else {

            MediaData mediaData = mediaDataDao.findByMedia_id(mediaCode.getMediaId());

            if (mediaData == null || MediaData.MEDIA_STATE_RECYCLE.equals(mediaData.getMediaState()) || MediaData.MEDIA_STATE_DELETE.equals(mediaData.getMediaState())) {
                return createResult(false, "数据已经过期！");
            }

            StringBuffer url = request.getRequestURL();

            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length())
                    .append(request.getContextPath()).append("/mobile/toPage.do?user_id=")
                    .append(user_id).append("&category_id=").append(category_id)
                    .append("&media_id=").append(mediaCode.getMediaId()).toString();

            result.put("success", true);
            result.put("url", tempContextUrl);

        }

        // 输出日志
        MDC.put("user_id", user_id);
        MDC.put("category_id", category_id);
        logger.info("用户验证了user_id=" + user_id + "，category_id=" + category_id + ",code=" + code + "的验证码，验证返回信息为：" + result.toString());

        return result;

    }

    /**
     * 前往展示页面
     *
     * @param user_id     用户id
     * @param category_id 套餐id
     * @param media_id    媒体数据id
     * @param model       页面数据存储对象
     * @param test        是否是测试状态
     * @param request     Request对象
     * @return 页面地址
     */
    @RequestMapping("/toPage.do")
    public String toPage(Long user_id, Integer category_id, Long media_id, Model model, boolean test
            , HttpServletRequest request) {

        // 判断是否在测试模式
        if (test) {
            if (user_id == null || category_id == null) return MOBILE_404;
        } else {
            // 判断接收到的参数是否为空
            if (user_id == null || category_id == null || media_id == null) return MOBILE_404;
        }

        try {

            // 查询user_id对应的用户
            User user = userDao.findByUser_id(user_id);

            // 判断查询到的用户是否存在
            if (user == null) return MOBILE_404;

            // 根据user_id、category_id查询状态正常的用户套餐系统
            UserCategory userCategory = userCategoryDao.findByUser_idAndCategory_idAndState(
                    user.getUserId(), category_id, UserCategory.USER_CATEGORY_STATE_NORMAL);

            // 判断用户套餐系统是否存在
            if (userCategory == null) return MOBILE_404;

            String picState = PicWebinfo.PIC_WEB_INFO_STATE_NORMAL;

            // 判断是否在测试模式，在测试模式将media_id设置为0
            if (test) {
                model.addAttribute("media_id", 0);
                picState = PicWebinfo.PIC_WEB_INFO_STATE_PREVIEW;
            } else {
                // 查询媒体数据，并判断媒体数据是否在正常状态内
                MediaData mediaData = mediaDataDao.findByMedia_id(media_id);
                if (mediaData == null || !MediaData.MEDIA_STATE_NORMAL.equals(mediaData.getMediaState()))
                    return MOBILE_404;

                // 获取图片尾缀
                String tempFileName[] = mediaData.getFilePath().split("\\.");

                model.addAttribute("image_type", tempFileName[1]);
                model.addAttribute("media_id", mediaData.getMediaId());
            }

            // 查询用户展示页面设置
            PicWebinfo picWebinfo = webinfoDao.findPicByUser_idAndCategory_id(
                    user.getUserId(), userCategory.getCategoryId(), picState);

            ShareInfo shareInfo = webinfoDao.findShareByUser_idAndCategory(user_id, category_id);

            model.addAttribute("shareInfoEntity", shareInfo);
            model.addAttribute("url", "http://" + request.getServerName());
            model.addAttribute("test", test);
            model.addAttribute("category_id", userCategory.getCategoryId());
            model.addAttribute("user_id", user.getUserId());

            // 输出日志
            MDC.put("user_id", user_id);
            MDC.put("category_id", category_id);
            logger.info("用户打开了了user_id=" + user_id + "，category_id=" + category_id + ",media_id=" + media_id + "的展示页面");

            // 判断展示页面设置是否有效，无效打开默认页面
            if (picWebinfo == null) return MOBILE_VIEW_DEFAULT;

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

//        logger.info("接收到生成获取微信jssdk的请求---------->请求的url为：" + url);

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

//            logger.info("获取微信jssdk参数---------->合成的字符串为：" + str + "；签名后的字符串为：" + signature);

        } catch (Exception e) {

            e.printStackTrace();
//            logger.info("获取微信jssdk参数---------->发生未知错误，错误信息为：" + getErrorInfoFromException(e));

        }

        res.put("appid", appid);
        res.put("url", url);
        res.put("jsapi_ticket", jsapi_ticket);
        res.put("nonceStr", nonce_str);
        res.put("timestamp", time);
        res.put("signature", signature);

//        logger.info("获取微信jssdk参数---------->返回的参数为：" + res.toString());

        return res;

    }

    /**
     * 接受分享数据
     *
     * @param user_id     用户id
     * @param category_id 套餐id
     * @param media_id    媒体数据
     * @param share_type  分享数据所属类型
     * @return 接受结果
     */
    @RequestMapping("/collectingData.do")
    @ResponseBody
    public Map collectingData(Long user_id, Integer category_id, Long media_id, String share_type) {

        Map<String, Object> result = new HashMap<>();

        if (user_id == null || category_id == null || media_id == null || share_type == null) {

            result.put("success", false);
            result.put("message", "有必填项为空");
            return result;
        }

        ShareData shareData = new ShareData();

        shareData.setCategoryId(category_id);
        shareData.setMediaId(media_id);
        shareData.setUserId(user_id);
        shareData.setShareType(share_type);

        if (!utilDao.save(shareData)) {

            result.put("success", false);
            result.put("message", "将数据保存到数据库时发生了错误");

        } else {

            result.put("success", true);
            result.put("message", "保存成功");

        }

        return result;

    }

}
