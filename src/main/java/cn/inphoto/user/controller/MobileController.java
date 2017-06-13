package cn.inphoto.user.controller;

import cn.inphoto.user.dao.*;
import cn.inphoto.user.dbentity.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static cn.inphoto.user.util.DirUtil.getErrorInfoFromException;

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
    public String toCode(Long user_id, Integer category_id,Model model) {

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

        model.addAttribute("userCategory", userCategory);

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
    public String toPage(Long user_id, Integer category_id, Long media_id, Model model) {

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

        model.addAttribute("media_id", mediaData.getMediaId());
        model.addAttribute("category_id", mediaData.getCategoryId());
        model.addAttribute("user_id", mediaData.getUserId());

        return MOBILE_VIEW;

    }

    /**
     * 输出媒体、套餐系统图片数据，
     *
     * @param response
     * @param request
     * @param id       媒体、套餐系统id
     * @param type     具体类型
     * @throws IOException
     */
    @RequestMapping("openMedia.do")
    public void openMedia(HttpServletResponse response, HttpServletRequest request, Long id, int type) throws IOException {

        String file_path = null;

        FileInputStream fis = null;

        OutputStream outputStream = null;

        try {

            switch (type) {
                case 1://type=1 media_data

                    MediaDataEntity mediaDataEntity = mediaDataDao.findByMedia_id(id);
                    if (mediaDataEntity != null) {
                        file_path = mediaDataEntity.getFilePath();
                    }
                    break;

                case 2://type=2 pic_bg

                    PicWebinfoEntity picWebinfoEntity = webinfoDao.findPicByPic_id(id);
                    if (picWebinfoEntity != null) {
                        file_path = picWebinfoEntity.getBackground();
                    }
                    break;

                case 3://type=3 code_bg

                    CodeWebinfoEntity codeWebinfo1 = webinfoDao.findCodeByCode_id(id);
                    if (codeWebinfo1 != null) {
                        file_path = codeWebinfo1.getBackground();
                    }
                    break;
                case 4://type=4 code_con

                    CodeWebinfoEntity codeWebinfo2 = webinfoDao.findCodeByCode_id(id);
                    if (codeWebinfo2 != null) {
                        file_path = codeWebinfo2.getButtonPic();
                    }
                    break;
                case 5://type=5 share_moments_icon

                    ShareInfoEntity shareInfo1 = webinfoDao.findShareByShare_id(id);
                    if (shareInfo1 != null) {
                        file_path = shareInfo1.getShareMomentsIcon();
                    }
                    break;
                case 6://type=6 share_chats_icon

                    ShareInfoEntity shareInfo2 = webinfoDao.findShareByShare_id(id);
                    if (shareInfo2 != null) {
                        file_path = shareInfo2.getShareChatsIcon();
                    }
                    break;
                default:
                    break;
            }

            if (file_path == null || "".equals(file_path)) {
                file_path = request.getSession().getServletContext().getRealPath("/images/21bg.png");
            }

            logger.info("接收到读取图片文件请求，请求id为：" + id + "，type为：" + type + "的图片文件路径为：" + file_path);

            // 将图片文件写入输入流
            fis = new FileInputStream(file_path);

            // 创建一个输入流大小的byte数组
            byte[] content = new byte[fis.available()];

            // 将输入流写入到数组中
            fis.read(content);

            // 创建输出流
            outputStream = response.getOutputStream();

            // 将数组写出到输出流中
            outputStream.write(content);

        } catch (Exception e) {

            e.printStackTrace();
            logger.info("id为：" + id + "的media读取错误，错误信息为：" + getErrorInfoFromException(e));

        } finally {
            if (fis != null) {
                fis.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

}
