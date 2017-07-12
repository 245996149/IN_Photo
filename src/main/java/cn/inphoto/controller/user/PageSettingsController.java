package cn.inphoto.controller.user;

import cn.inphoto.dao.UtilDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.user.CodeWebinfoEntity;
import cn.inphoto.dbentity.user.PicWebinfoEntity;
import cn.inphoto.dbentity.user.ShareInfoEntity;
import cn.inphoto.dbentity.user.UsersEntity;
import cn.inphoto.log.UserLog;
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
    UtilDao utilDao;

    @Resource
    WebinfoDao webinfoDao;

    @RequestMapping("/toPageSettings.do")
    public String toPageSettings(int category_id, Model model, HttpSession session) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        model.addAttribute("category_id", category_id);

        session.setAttribute("nav_code", UserController.PAGESETTINGS_CODE);

        return "user/page_settings";
    }

    /**
     * view预览提交
     *
     * @param request
     * @param session
     * @param show_pic_bg      背景图片
     * @param picWebinfoEntity view信息对象
     * @return 是否成功
     */
    @RequestMapping("/perView.do")
    @ResponseBody
    public Map perView(HttpServletRequest request, HttpSession session, @RequestParam MultipartFile show_pic_bg,
                       PicWebinfoEntity picWebinfoEntity) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 查找数据库中是否有曾经预览的数据
        PicWebinfoEntity picWebinfoDB = webinfoDao.findPicByUser_idAndCategory_id(
                usersEntity.getUserId(), picWebinfoEntity.getCategoryId(), PicWebinfoEntity.PIC_WEB_INFO_STATE_PREVIEW);

        MDC.put("user_id", usersEntity.getUserId());
        MDC.put("category_id", picWebinfoEntity.getCategoryId());

        try {

            // 获取上传图片的路径信息
            String filePath = createSettingsPic(show_pic_bg, usersEntity);

            // 判断数据库中曾经预览的信息是否有效
            if (picWebinfoDB == null) {

                /*无效*/
                // 设置上传图片的路径信息
                picWebinfoEntity.setBackground(filePath);
                picWebinfoEntity.setUserId(usersEntity.getUserId());
                picWebinfoEntity.setPicWebinfoState(PicWebinfoEntity.PIC_WEB_INFO_STATE_PREVIEW);

                // 保存数据到数据库中
                if (!utilDao.save(picWebinfoEntity)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                            + picWebinfoEntity.getCategoryId() + " 套餐系统view页面，保存时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            } else {

                /*有效*/
                // 更新查询到的预览的信息
                picWebinfoDB.setBackground(filePath);
                picWebinfoDB.setPictureBottom(picWebinfoEntity.getPictureBottom());
                picWebinfoDB.setPictureLeft(picWebinfoEntity.getPictureLeft());
                picWebinfoDB.setPictureRight(picWebinfoEntity.getPictureRight());
                picWebinfoDB.setPictureTop(picWebinfoEntity.getPictureTop());
                picWebinfoDB.setPageTitle(picWebinfoEntity.getPageTitle());

                // 更新数据库中的预览信息
                if (!utilDao.update(picWebinfoDB)) {

                    //更新失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                            + picWebinfoEntity.getCategoryId() + " 套餐系统view页面，更新时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            }

            result.put("success", true);
            result.put("message", "更新成功");
            result.put("url", URLEncoder.encode("http://" + request.getServerName() + "/" + request.getContextPath() + "/mobile/toPage.do?user_id="
                    + usersEntity.getUserId() + "&category_id=" + picWebinfoEntity.getCategoryId() + "&test=true", "utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");
        }

        logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                + picWebinfoEntity.getCategoryId() + " 套餐系统view页面，返回结果为：" + result.toString());
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

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        MDC.put("user_id", usersEntity.getUserId());
        MDC.put("category_id", category_id);

        Map<String, Object> result = new HashMap<>();

        try {

            // 查找用户生效状态的view信息
            PicWebinfoEntity norPic = webinfoDao.findPicByUser_idAndCategory_id(
                    usersEntity.getUserId(), category_id, PicWebinfoEntity.PIC_WEB_INFO_STATE_NORMAL);

            // 查找用户预览状态的view信息
            PicWebinfoEntity prePic = webinfoDao.findPicByUser_idAndCategory_id(
                    usersEntity.getUserId(), category_id, PicWebinfoEntity.PIC_WEB_INFO_STATE_PREVIEW);

            // 设置状态为生效
            prePic.setPicWebinfoState(PicWebinfoEntity.PIC_WEB_INFO_STATE_NORMAL);

            // 判断生效状态的信息是否有限
            if (norPic == null) {

                prePic.setPicWebinfoId(0);

                // 无效，新增一个生效状态
                if (!utilDao.save(prePic)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
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
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
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

        logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                + category_id + " 套餐系统为生效状态，返回结果为：" + result.toString());
        return result;
    }

    /**
     * code预览
     *
     * @param request
     * @param session
     * @param button_pic        按钮
     * @param code_bg           背景
     * @param codeWebinfoEntity code信息对象
     * @return 是否提交成功
     */
    @RequestMapping("/perCode.do")
    @ResponseBody
    public Map perCode(HttpServletRequest request, HttpSession session, @RequestParam MultipartFile button_pic,
                       @RequestParam MultipartFile code_bg, CodeWebinfoEntity codeWebinfoEntity) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 查找数据库中是否有曾经预览的数据
        CodeWebinfoEntity codeWebinfoDB = webinfoDao.findCodeByUser_idAndCategory_id(
                usersEntity.getUserId(), codeWebinfoEntity.getCategoryId(), CodeWebinfoEntity.CODE_WEB_INFO_STATE_PREVIEW);

        MDC.put("user_id", usersEntity.getUserId());
        MDC.put("category_id", codeWebinfoEntity.getCategoryId());

        try {

            // 获取上传图片的路径信息
            String bgFilePath = createSettingsPic(code_bg, usersEntity);
            String buttonFilePath = createSettingsPic(button_pic, usersEntity);

            codeWebinfoEntity.setUserId(usersEntity.getUserId());
            codeWebinfoEntity.setCodeWebinfoState(CodeWebinfoEntity.CODE_WEB_INFO_STATE_PREVIEW);
            codeWebinfoEntity.setBackground(bgFilePath);
            codeWebinfoEntity.setButtonPic(buttonFilePath);

            if (codeWebinfoDB == null) {

                if (!utilDao.save(codeWebinfoEntity)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                            + codeWebinfoEntity.getCategoryId() + " 套餐系统提取页面，保存时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            } else {

                codeWebinfoEntity.setCodeWebinfoId(codeWebinfoDB.getCodeWebinfoId());

                if (!utilDao.update(codeWebinfoEntity)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                            + codeWebinfoEntity.getCategoryId() + " 套餐系统提取页面，更新时发生错误，返回结果为：" + result.toString());
                    return result;

                }

            }

            result.put("success", true);
            result.put("message", "更新成功");
            result.put("url", URLEncoder.encode("http://" + request.getServerName() + "/" + request.getContextPath() + "/mobile/toCode.do?user_id="
                    + usersEntity.getUserId() + "&category_id=" + codeWebinfoEntity.getCategoryId() + "&test=true", "utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "发生未知错误，请稍后再试。");
        }

        logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                + codeWebinfoEntity.getCategoryId() + " 套餐系统提取页面，返回结果为：" + result.toString());
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

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        MDC.put("user_id", usersEntity.getUserId());
        MDC.put("category_id", category_id);

        Map<String, Object> result = new HashMap<>();

        try {

            // 查找用户生效状态的view信息
            CodeWebinfoEntity norCode = webinfoDao.findCodeByUser_idAndCategory_id(
                    usersEntity.getUserId(), category_id, CodeWebinfoEntity.CODE_WEB_INFO_STATE_NORMAL);

            // 查找用户预览状态的view信息
            CodeWebinfoEntity preCode = webinfoDao.findCodeByUser_idAndCategory_id(
                    usersEntity.getUserId(), category_id, CodeWebinfoEntity.CODE_WEB_INFO_STATE_PREVIEW);

            // 设置状态为生效
            preCode.setCodeWebinfoState(CodeWebinfoEntity.CODE_WEB_INFO_STATE_NORMAL);

            // 判断生效状态的信息是否有限
            if (norCode == null) {

                preCode.setCodeWebinfoId(0);

                // 无效，新增一个生效状态
                if (!utilDao.save(preCode)) {

                    //保存失败
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
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
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
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

        logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                + category_id + " 套餐系统为生效状态，返回结果为：" + result.toString());
        return result;
    }

    @RequestMapping("/setShareInfo.do")
    @ResponseBody
    public Map setShareInfo(HttpSession session, ShareInfoEntity shareInfoEntity,
                            @RequestParam MultipartFile moments_icon, @RequestParam MultipartFile chats_icon) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        MDC.put("user_id", usersEntity.getUserId());
        MDC.put("category_id", shareInfoEntity.getCategoryId());

        Map<String, Object> result = new HashMap<>();

        try {

            // 查询数据库中的分享信息
            ShareInfoEntity shareInfoDB = webinfoDao.findShareByUser_idAndCategory(usersEntity.getUserId(), shareInfoEntity.getCategoryId());

            // 获取上传图片的路径信息
            String momentsFilePath = createSettingsPic(moments_icon, usersEntity);
            String chatsFilePath = createSettingsPic(chats_icon, usersEntity);

            // 设置分享信息的一些属性
            shareInfoEntity.setUserId(usersEntity.getUserId());
            shareInfoEntity.setShareMomentsIcon(momentsFilePath);
            shareInfoEntity.setShareChatsIcon(chatsFilePath);

            // 判断从数据库中找到的对象是否有效
            if (shareInfoDB == null) {

                // 无效，新增一个对象
                if (!utilDao.save(shareInfoEntity)) {
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新分享信息category_id="
                            + shareInfoEntity.getCategoryId() + " 套餐系统，返回结果为：" + result.toString());
                    return result;
                }

            } else {

                //有效，设置id为数据库中对象的id
                shareInfoEntity.setShareInfoId(shareInfoDB.getShareInfoId());

                // 更新数据库对象
                if (!utilDao.update(shareInfoEntity)) {
                    result.put("success", false);
                    result.put("message", "向数据库中写入数据库时发生错误，请稍后再试");
                    logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新分享信息category_id="
                            + shareInfoEntity.getCategoryId() + " 套餐系统，返回结果为：" + result.toString());
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

        logger.log(UserLog.USER, "用户user_id=" + usersEntity.getUserId() + " 更新预览category_id="
                + shareInfoEntity.getCategoryId() + " 套餐系统为生效状态，返回结果为：" + result.toString());
        return result;

    }

}
