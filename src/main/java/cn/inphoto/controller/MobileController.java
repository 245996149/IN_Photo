package cn.inphoto.controller;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.weChatEntity.JsapiTicket;
import cn.inphoto.weChatUtil.Sha1;
import cn.inphoto.weChatUtil.WeChatWebUtil;
import cn.inphoto.weibo.WeiboService;
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

import static cn.inphoto.util.ResultMapUtil.createResult;

/**
 * 移动端h5页面控制器
 * Created by kaxia on 2017/6/13.
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {

    private Logger logger = Logger.getLogger(MobileController.class);

    @Value("#{properties['weixin_appid']}")
    String appid;

    @Value("#{properties['weibo_appKey']}")
    String appKey;

    @Value("#{properties['AliyunOSSPath']}")
    String OSSPath;

    @Resource
    private UserDao userDao;

    @Resource
    private UserCategoryDao userCategoryDao;

    @Resource
    private WebinfoDao webinfoDao;

    @Resource
    private MediaCodeDao mediaCodeDao;

    @Resource
    private MediaDataDao mediaDataDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private UtilDao utilDao;

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

    private static final String MOBILE_VIEW_VIDEO = "mobile/view_video";

    private static final String MOBILE_VIEW_VIDEO_DEFAULT = "mobile/view_video_default";

    @RequestMapping("/test.do")
    public String test() {
        return "mobile/test";
    }

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
                user.getUserId(), category_id, UserCategory.UserState.NORMAL);

        // 判断用户套餐系统是否存在
        if (userCategory == null) return MOBILE_404;

        CodeWebInfo.CodeState codeState = CodeWebInfo.CodeState.NORMAL;

        if (test) {
            codeState = CodeWebInfo.CodeState.PREVIEW;
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

            if (mediaData == null || MediaData.MediaState.Recycle == mediaData.getMediaState()
                    || MediaData.MediaState.Delete == mediaData.getMediaState()) {
                return createResult(false, "数据已经过期！");
            }

            StringBuffer url = request.getRequestURL();

            url = url.delete(url.length() - request.getRequestURI().length(), url.length());

            String page_url = url + request.getContextPath() +
                    "/mobile/toPage.do?user_id=" + user_id + "&category_id=" + category_id +
                    "&media_id=" + mediaCode.getMediaId();

            String image_url = OSSPath + "/" + mediaData.getMediaKey();

            result.put("success", true);
            result.put("page_url", page_url);
            result.put("image_url", image_url);

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
                    user.getUserId(), category_id, UserCategory.UserState.NORMAL);

            // 判断用户套餐系统是否存在
            if (userCategory == null) return MOBILE_404;

            PicWebInfo.PicState picState = PicWebInfo.PicState.NORMAL;

            Long videoPicMediaId = null;

            // 判断是否在测试模式，在测试模式将media_id设置为0
            if (test) {
                model.addAttribute("media", null);
                picState = PicWebInfo.PicState.PREVIEW;
            } else {
                // 查询媒体数据，并判断媒体数据是否在正常状态内
                MediaData mediaData = mediaDataDao.findByMedia_id(media_id);
                if (mediaData == null ||
                        (MediaData.MediaState.Normal != mediaData.getMediaState() &&
                                MediaData.MediaState.WillDelete != mediaData.getMediaState()))
                    return MOBILE_404;
                model.addAttribute("media", mediaData);
                videoPicMediaId = mediaData.getVideoPicMedia();
            }

            // 查询用户展示页面设置
            PicWebInfo picWebInfo = webinfoDao.findPicByUser_idAndCategory_id(
                    user.getUserId(), userCategory.getCategoryId(), picState);

            ShareInfo shareInfo = webinfoDao.findShareByUser_idAndCategory(user_id, category_id);

            Category category = categoryDao.findByCategory_id(userCategory.getCategoryId());

            model.addAttribute("shareInfo", shareInfo);
            model.addAttribute("url", "http://" + request.getServerName());
            model.addAttribute("test", test);
            model.addAttribute("category", category);
            model.addAttribute("user_id", user.getUserId());

            // 输出日志
            MDC.put("user_id", user_id);
            MDC.put("category_id", category_id);
            logger.info("用户打开了了user_id=" + user_id + "，category_id=" + category_id + ",media_id=" + media_id + "的展示页面");

            if (category.getIsVideo() == 1) {
                if (videoPicMediaId != null) {
                    MediaData videoPicMedia = mediaDataDao.findByMedia_id(videoPicMediaId);
                    if (videoPicMedia != null) model.addAttribute("video_pic_key", videoPicMedia.getMediaKey());
                }
            }

            // 判断展示页面设置是否有效，无效打开默认页面
            if (picWebInfo == null) {
                if (category.getIsVideo() == 1) {
                    return MOBILE_VIEW_VIDEO_DEFAULT;
                } else {
                    return MOBILE_VIEW_DEFAULT;
                }
            }

            model.addAttribute("picWebInfo", picWebInfo);

            if (category.getIsVideo() == 1) {
                return MOBILE_VIEW_VIDEO;
            } else {
                return MOBILE_VIEW;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return MOBILE_404;
        }

    }

    /**
     * 生成获取微信jssdk所需参数反馈给客户端
     *
     * @param response 发送
     * @param request  请求
     * @param url      当前页面url
     * @return 微信jssdk所需参数
     */
    @RequestMapping("/getWeChatInfo.do")
    @ResponseBody
    public Map<String, String> weChat(HttpServletResponse response, HttpServletRequest request, String url) {

        return WXConfig(request, response, url);

    }

    /**
     * 生成获取微信jssdk所需参数反馈给客户端
     *
     * @param response 发送
     * @param request  请求
     * @param url      当前页面url
     * @return 微信jssdk所需参数
     */
    @RequestMapping("/getWeiBoInfo.do")
    @ResponseBody
    public Map<String, String> weiBo(HttpServletResponse response, HttpServletRequest request, String url) {

        return WBConfig(request, response, url);

    }

    private HashMap<String, String> WBConfig(HttpServletRequest request, HttpServletResponse response, String url) {
        HashMap<String, String> res = new HashMap<>();

        String time = null;

        String jsapi_ticket = null;

        String signature = "";

        String nonce_str = "";

        try {
            cn.inphoto.weibo.entity.JsapiTicket jsapiTicket = WeiboService.judgeWeiboJsapiTicketOvertime(request, response);
            // 获取随机数
            nonce_str = UUID.randomUUID().toString();
            // 获取jsapi_ticket
            jsapi_ticket = jsapiTicket.getJsTicket();
            // 获取系统时间
            time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
            // 合成字符串用于签名
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + time + "&url=" + url;

            signature = Sha1.getSha1(str);

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info(appKey);
        res.put("appKey", appKey);
        res.put("url", url);
        res.put("jsapi_ticket", jsapi_ticket);
        res.put("nonceStr", nonce_str);
        res.put("timestamp", time);
        res.put("signature", signature);

        return res;
    }

    /**
     * 获取微信jssdk所需参数
     *
     * @param response 发送
     * @param request  请求
     * @return 微信jssdk所需参数
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

        } catch (Exception e) {

            e.printStackTrace();

        }

        res.put("appid", appid);
        res.put("url", url);
        res.put("jsapi_ticket", jsapi_ticket);
        res.put("nonceStr", nonce_str);
        res.put("timestamp", time);
        res.put("signature", signature);

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

    @ResponseBody
    @RequestMapping("/collectInformation.do")
    public Map<String, Object> collectInformation(ShareClickData shareClickData) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(shareClickData.toString());
        utilDao.save(shareClickData);
        result.put("data", shareClickData);
        return result;
    }

}
