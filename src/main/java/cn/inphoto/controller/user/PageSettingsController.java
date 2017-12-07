package cn.inphoto.controller.user;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.UtilDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.log.UserLogLevel;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static cn.inphoto.util.DirUtil.createSettingsPic;

/**
 * Created by kaxia on 2017/6/14.
 */
@RequestMapping("/user/setting")
@Controller
public class PageSettingsController {

    private Logger logger = Logger.getLogger(PageSettingsController.class);

    @Resource
    private UtilDao utilDao;

    @Resource
    private WebinfoDao webinfoDao;

    @RequestMapping("/toPageSettings.do")
    public String toPageSettings(int category_id, Model model, HttpSession session) {

        User user = (User) session.getAttribute("loginUser");

        PicWebInfo picWebInfo = webinfoDao.findPicByUser_idAndCategory_id(user.getUserId(), category_id, PicWebInfo.PicState.NORMAL);

        CodeWebInfo codeWebInfo = webinfoDao.findCodeByUser_idAndCategory_id(user.getUserId(), category_id, CodeWebInfo.CodeState.NORMAL);

        ShareInfo shareInfo = webinfoDao.findShareByUser_idAndCategory(user.getUserId(), category_id);

        model.addAttribute("category_id", category_id);
        model.addAttribute("picWebInfo", picWebInfo);
        model.addAttribute("codeWebInfo", codeWebInfo);
        model.addAttribute("shareInfo", shareInfo);

        session.setAttribute("nav_code", UserController.PAGESETTINGS_CODE);

        return "user/page_settings";
    }

    /**
     * view预览提交
     *
     * @param request
     * @param session
     * @param show_pic_bg 背景图片
     * @param picWebInfo  view信息对象
     * @return 是否成功
     */
    @RequestMapping("/perView.do")
    @ResponseBody
    public Map perView(HttpServletRequest request, HttpSession session, @RequestParam MultipartFile show_pic_bg,
                       PicWebInfo picWebInfo) {

        User user = (User) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 查找数据库中是否有曾经预览的数据
        PicWebInfo picWebInfoDB = webinfoDao.findPicByUser_idAndCategory_id(
                user.getUserId(), picWebInfo.getCategoryId(), PicWebInfo.PicState.PREVIEW);

        MDC.put("user_info", "user_id=" + user.getUserId() + ";category_id=" + picWebInfo.getCategoryId());

        try {

            MediaData backgroundMedia = createSettingsPic(show_pic_bg, user, picWebInfo.getCategoryId());

            /*有效*/
            // 判断数据库中曾经预览的信息是否有效
            if (picWebInfoDB == null) {
                // 获取上传图片的路径信息

                utilDao.save(backgroundMedia);
                // 设置上传图片的路径信息
                picWebInfo.setBackgroundMedia(backgroundMedia);
                picWebInfo.setUserId(user.getUserId());
                picWebInfo.setPicWebinfoState(PicWebInfo.PicState.PREVIEW);

                // 保存数据到数据库中
                if (!utilDao.save(picWebInfo)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + picWebInfo.getCategoryId() + " 套餐系统view页面，保存时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            } else {

                utilDao.save(backgroundMedia);

                /*有效*/
                // 更新查询到的预览的信息
                picWebInfoDB.setBackgroundMedia(backgroundMedia);
                picWebInfoDB.setPictureBottom(picWebInfo.getPictureBottom());
                picWebInfoDB.setPictureLeft(picWebInfo.getPictureLeft());
                picWebInfoDB.setPictureRight(picWebInfo.getPictureRight());
                picWebInfoDB.setPictureTop(picWebInfo.getPictureTop());
                picWebInfoDB.setPageTitle(picWebInfo.getPageTitle());

                // 更新数据库中的预览信息
                if (!utilDao.update(picWebInfoDB)) {

                    //更新失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + picWebInfo.getCategoryId() + " 套餐系统view页面，更新时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            }

            result.put("success", true);
            result.put("message", "更新成功");
            result.put("url", URLEncoder.encode("http://" + request.getServerName() + "/" + request.getContextPath() + "/mobile/toPage.do?user_id="
                    + user.getUserId() + "&category_id=" + picWebInfo.getCategoryId() + "&test=true", "utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");
        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                + picWebInfo.getCategoryId() + " 套餐系统view页面，返回结果为：" + result.toString());
        return result;

    }

    /**
     * 更新category_id套餐系统预览信息为生效状态
     *
     * @param session
     * @param category_id
     * @return
     */
    @RequestMapping("/changeViewPreToNormal.do")
    @ResponseBody
    public Map changeViewPreToNormal(HttpSession session, Integer category_id) {

        User user = (User) session.getAttribute("loginUser");

        MDC.put("user_info", "user_id=" + user.getUserId() + ";category_id=" + category_id);

        Map<String, Object> result = new HashMap<>();

        try {

            // 查找用户生效状态的view信息
            PicWebInfo norPic = webinfoDao.findPicByUser_idAndCategory_id(
                    user.getUserId(), category_id, PicWebInfo.PicState.NORMAL);

            // 查找用户预览状态的view信息
            PicWebInfo prePic = webinfoDao.findPicByUser_idAndCategory_id(
                    user.getUserId(), category_id, PicWebInfo.PicState.PREVIEW);

            // 设置状态为生效
            prePic.setPicWebinfoState(PicWebInfo.PicState.NORMAL);

            // 判断生效状态的信息是否有限
            if (norPic == null) {

                prePic.setPicWebinfoId(0);

                // 无效，新增一个生效状态
                if (!utilDao.save(prePic)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + category_id + " 套餐系统view页面为生效状态，保存时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            } else {

                // 有效，设置预览状态的id为生效状态的id
                prePic.setPicWebinfoId(norPic.getPicWebinfoId());
                // 更新数据库
                if (!utilDao.update(prePic)) {

                    //更新失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + category_id + " 套餐系统view页面为生效状态，更新时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            }

            result.put("success", true);
            result.put("message", "更新成功");

        } catch (Exception e) {

            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");

        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                + category_id + " 套餐系统为生效状态，返回结果为：" + result.toString());
        return result;
    }

    /**
     * code预览
     *
     * @param request
     * @param session
     * @param button_pic  按钮
     * @param code_bg     背景
     * @param codeWebInfo code信息对象
     * @return 是否提交成功
     */
    @RequestMapping("/perCode.do")
    @ResponseBody
    public Map perCode(HttpServletRequest request, HttpSession session, @RequestParam MultipartFile button_pic,
                       @RequestParam MultipartFile code_bg, CodeWebInfo codeWebInfo) {

        User user = (User) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 查找数据库中是否有曾经预览的数据
        CodeWebInfo codeWebinfoDB = webinfoDao.findCodeByUser_idAndCategory_id(
                user.getUserId(), codeWebInfo.getCategoryId(), CodeWebInfo.CodeState.PREVIEW);

        MDC.put("user_info", "user_id=" + user.getUserId() + ";category_id=" + codeWebInfo.getCategoryId());

        try {

            // 获取上传图片的路径信息
            MediaData bgMedia = createSettingsPic(code_bg, user, codeWebInfo.getCategoryId());
            MediaData buttonMedia = createSettingsPic(button_pic, user, codeWebInfo.getCategoryId());

            utilDao.save(bgMedia);
            utilDao.save(buttonMedia);

            codeWebInfo.setUserId(user.getUserId());
            codeWebInfo.setCodeWebinfoState(CodeWebInfo.CodeState.PREVIEW);
            codeWebInfo.setBackgroundMedia(bgMedia);
            codeWebInfo.setButtonPicMedia(buttonMedia);

            if (codeWebinfoDB == null) {

                if (!utilDao.save(codeWebInfo)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + codeWebInfo.getCategoryId() + " 套餐系统提取页面，保存时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            } else {

                codeWebInfo.setCodeWebinfoId(codeWebinfoDB.getCodeWebinfoId());

                if (!utilDao.update(codeWebInfo)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + codeWebInfo.getCategoryId() + " 套餐系统提取页面，更新时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            }

            result.put("success", true);
            result.put("message", "更新成功");
            result.put("url", URLEncoder.encode("http://" + request.getServerName() + "/" + request.getContextPath() + "/mobile/toCode.do?user_id="
                    + user.getUserId() + "&category_id=" + codeWebInfo.getCategoryId() + "&test=true", "utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");
        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                + codeWebInfo.getCategoryId() + " 套餐系统提取页面，返回结果为：" + result.toString());
        return result;
    }


    /**
     * 更新category_id套餐系统code预览信息为生效状态
     *
     * @param session
     * @param category_id
     * @return
     */
    @RequestMapping("/changeCodePreToNormal.do")
    @ResponseBody
    public Map changeCodePreToNormal(HttpSession session, Integer category_id) {

        User user = (User) session.getAttribute("loginUser");

        MDC.put("user_info", "user_id=" + user.getUserId() + ";category_id=" + category_id);

        Map<String, Object> result = new HashMap<>();

        try {

            // 查找用户生效状态的view信息
            CodeWebInfo norCode = webinfoDao.findCodeByUser_idAndCategory_id(
                    user.getUserId(), category_id, CodeWebInfo.CodeState.NORMAL);

            // 查找用户预览状态的view信息
            CodeWebInfo preCode = webinfoDao.findCodeByUser_idAndCategory_id(
                    user.getUserId(), category_id, CodeWebInfo.CodeState.PREVIEW);

            // 设置状态为生效
            preCode.setCodeWebinfoState(CodeWebInfo.CodeState.NORMAL);

            // 判断生效状态的信息是否有限
            if (norCode == null) {

                preCode.setCodeWebinfoId(0);

                // 无效，新增一个生效状态
                if (!utilDao.save(preCode)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + category_id + " 套餐系统提取页面为生效状态，保存时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            } else {

                // 有效，设置预览状态的id为生效状态的id
                preCode.setCodeWebinfoId(norCode.getCodeWebinfoId());
                // 更新数据库
                if (!utilDao.update(preCode)) {

                    //更新失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                            + category_id + " 套餐系统提取页面为生效状态，更新时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            }

            result.put("success", true);
            result.put("message", "更新成功");

        } catch (Exception e) {

            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");

        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                + category_id + " 套餐系统为生效状态，返回结果为：" + result.toString());
        return result;
    }

    @RequestMapping("/setShareInfo.do")
    @ResponseBody
    public Map setShareInfo(HttpSession session, ShareInfo shareInfo,
                            @RequestParam MultipartFile moments_icon, @RequestParam MultipartFile chats_icon) {

        User user = (User) session.getAttribute("loginUser");

        MDC.put("user_info", "user_id=" + user.getUserId() + ";category_id=" + shareInfo.getCategoryId());

        Map<String, Object> result = new HashMap<>();

        try {

            // 查询数据库中的分享信息
            ShareInfo shareInfoDB = webinfoDao.findShareByUser_idAndCategory(user.getUserId(), shareInfo.getCategoryId());

            // 获取上传图片的路径信息
            MediaData momentsMedia = createSettingsPic(moments_icon, user, shareInfo.getCategoryId());
            MediaData chatsMedia = createSettingsPic(chats_icon, user, shareInfo.getCategoryId());

            // 设置分享信息的一些属性
            shareInfo.setUserId(user.getUserId());

            // 判断从数据库中找到的对象是否有效
            if (shareInfoDB == null) {

                utilDao.save(momentsMedia);
                utilDao.save(chatsMedia);

                shareInfo.setMomentsIconMedia(momentsMedia);
                shareInfo.setChatsIconMedia(chatsMedia);

                // 无效，新增一个对象
                if (!utilDao.save(shareInfo)) {
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新分享信息category_id="
                            + shareInfo.getCategoryId() + " 套餐系统，返回结果为：" + result.toString());
                    return result;
                }

            } else {

                MediaData dbMomentsMedia = shareInfoDB.getMomentsIconMedia();
                MediaData dbChatsMedia = shareInfoDB.getChatsIconMedia();
                dbChatsMedia.setMediaKey(momentsMedia.getMediaKey());
                dbMomentsMedia.setMediaKey(momentsMedia.getMediaKey());
                utilDao.save(dbMomentsMedia);
                utilDao.save(dbChatsMedia);

                //有效，设置id为数据库中对象的id
                shareInfo.setShareInfoId(shareInfoDB.getShareInfoId());
                shareInfo.setMomentsIconMedia(dbMomentsMedia);
                shareInfo.setChatsIconMedia(dbChatsMedia);

                // 更新数据库对象
                if (!utilDao.update(shareInfo)) {
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新分享信息category_id="
                            + shareInfo.getCategoryId() + " 套餐系统，返回结果为：" + result.toString());
                    return result;
                }

            }

            result.put("success", true);
            result.put("message", "更新成功");

        } catch (Exception e) {

            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");

        }

        logger.log(UserLogLevel.USER, "用户user_id=" + user.getUserId() + " 更新预览category_id="
                + shareInfo.getCategoryId() + " 套餐系统为生效状态，返回结果为：" + result.toString());
        return result;

    }

}
