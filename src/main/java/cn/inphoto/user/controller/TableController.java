package cn.inphoto.user.controller;

import cn.inphoto.user.dao.MediaCodeDao;
import cn.inphoto.user.dao.MediaDataDao;
import cn.inphoto.user.dao.ShareDataDao;
import cn.inphoto.user.dao.UserCategoryDao;
import cn.inphoto.user.dbentity.*;
import cn.inphoto.user.dbentity.page.TablePage;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.inphoto.user.util.DBUtil.selectTodayData;

/**
 * Created by kaxia on 2017/6/12.
 */
@Controller
@RequestMapping("/table")
public class TableController {

    @Resource
    MediaDataDao mediaDataDao;

    @Resource
    MediaCodeDao mediaCodeDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    ShareDataDao shareDataDao;

    /**
     * @param model
     * @param session
     * @param tablePage
     * @return
     */
    @RequestMapping("/toTable.do")
    public String toTable(Model model, HttpSession session, TablePage tablePage) {
        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        tablePage.setUser_id(usersEntity.getUserId());

        tablePage.setRows(mediaDataDao.countByUser_idAndCategory_idAndMedia_state(usersEntity.getUserId(), tablePage.getCategory_id(), MediaDataEntity.MEDIA_STATE_NORMAL));

        List<MediaDataEntity> mediaDataList = mediaDataDao.findByPage(tablePage);

        List<MediaCodeEntity> mediaCodeList = mediaCodeDao.findByUser_idAndCategory_id(usersEntity.getUserId(), tablePage.getCategory_id());

        model.addAttribute("mediaDataList", mediaDataList);
        model.addAttribute("mediaCodeList", mediaCodeList);
        model.addAttribute("tablePage", tablePage);

        // 查询今日数据并写入session
        selectTodayData(session, usersEntity);

        return "user/table";
    }

    /**
     * 根据媒体对象id，查询媒体对象并将其缩放到100px
     *
     * @param response 返回
     * @param media_id 媒体数据id号
     * @throws IOException
     */
    @RequestMapping("/getThumbnail.do")
    public void getThumbnail(HttpServletResponse response, Long media_id) throws IOException {

        MediaDataEntity mediaData = mediaDataDao.findByMedia_id(media_id);

        Thumbnails.of(new File(mediaData.getFilePath())).size(100, 100).toOutputStream(response.getOutputStream());

    }

    /**
     * 根据user_id、category_id、type获取七天内type类型对应的数据
     *
     * @param session     session
     * @param category_id 套餐系统id
     * @param type        类型码
     * @return 七天内的统计数据
     */
    @RequestMapping("/getShareData.do")
    @ResponseBody
    public Map[] getShareData(HttpSession session, int category_id, int type) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        // 创建返回的数组
        Map[] maps = new HashMap[7];

        // 创建日期编码对象
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");

        // 创建日历对象
        Calendar calendar = Calendar.getInstance();

        // 给日历对象设置时间
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        // 创建每天的结束时间对象
        Date end = calendar.getTime();
        // 日历天数减1
        calendar.add(Calendar.DATE, -1);
        // 创建每天的开始时间对象
        Date begin = calendar.getTime();

        // 判断type对应的类型
        switch (type) {
            case 1:
                // 循环查询七天内的点击量
                for (int i = 7; i > 0; i--) {
                    // 获取数据
                    int a = shareDataDao.countByTime(
                            user.getUserId(), category_id, begin, end, ShareDataEntity.SHARE_TYPE_WEB_CLICK);

                    // 创建返回的Map对象
                    Map<String, Object> result = new HashMap<>();

                    // 将数据添加到返回的map中
                    result.put("name", format.format(begin));
                    result.put("num", a);

                    // 将map对象添加到队列中
                    // infos.add(result);
                    maps[i - 1] = result;

                    // 日期减少一天
                    end = begin;
                    calendar.add(Calendar.DATE, -1);
                    begin = calendar.getTime();
                }
                break;

            case 2:
                // 循环查询七天内的分享给好友量、分享到朋友圈
                for (int i = 7; i > 0; i--) {
                    int chats_num = shareDataDao.countByTime(
                            user.getUserId(), category_id, begin, end, ShareDataEntity.SHARE_TYPE_WECHAT_SHARE_CHATS);
                    int moments_num = shareDataDao.countByTime(
                            user.getUserId(), category_id, begin, end, ShareDataEntity.SHARE_TYPE_WECHAT_SHARE_MOMENTS);
                    // 创建返回的Map对象
                    Map<String, Object> result = new HashMap<>();

                    // 将数据添加到返回的map中
                    result.put("name", format.format(begin));
                    result.put("chats_num", chats_num);
                    result.put("moments_num", moments_num);

                    // 将map对象添加到队列中
                    // infos.add(result);
                    maps[i - 1] = result;

                    // 日期减少一天
                    end = begin;
                    calendar.add(Calendar.DATE, -1);
                    begin = calendar.getTime();
                }
                break;

            default:
                break;

        }

        return maps;

    }

    /**
     * 计算套餐系统已使用、剩余数量返回给前台表单
     *
     * @param session     session
     * @param category_id 套餐系统id
     * @return 含有数据的map
     */
    @RequestMapping("/getSystemInfo.do")
    @ResponseBody
    public Map getSystemInfo(HttpSession session, int category_id) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        // 创建返回的数组
        Map<String, Object> result = new HashMap<>();

        // 查找用户的套餐系统信息
        UserCategoryEntity userCategory = userCategoryDao.findByUser_idAndCategory_id(user.getUserId(), category_id, UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);
        // 计算用户该套餐系统内状态为张昌的媒体数据的数量
        int media_num = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(user.getUserId(), category_id, MediaDataEntity.MEDIA_STATE_NORMAL);

        System.out.println(userCategory);

        result.put("use", media_num);
        result.put("remaining", (userCategory.getMediaNumber() - media_num));

        System.out.println(result.get("remaining"));

        return result;

    }

    /**
     * 获取回收站媒体数据过期情况信息
     *
     * @param session     session
     * @param category_id 套餐系统id
     * @return 回收站媒体数据过期情况信息
     */
    @RequestMapping("/getRecycleInfo.do")
    @ResponseBody
    public Map getRecycleInfo(HttpSession session, int category_id) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        // 创建返回的数组
        Map<String, Object> result = new HashMap<>();

        // 创建日历对象
        Calendar calendar = Calendar.getInstance();

        // 给日历对象设置时间
        calendar.setTime(new Date());

        // 从日历中创建Data对象
        Date begin = calendar.getTime();

        // 将日历设置都七天之后
        calendar.add(Calendar.DATE, 7);

        // 从日历中创建Data对象
        Date end = calendar.getTime();

        // 查询回收站中该套餐系统的总数
        int recycle_total = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                user.getUserId(), category_id, MediaDataEntity.MEDIA_STATE_RECYCLE);

        // 查询回收站中该套餐系统的7天内过期的媒体数
        int recycle_7 = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                user.getUserId(), category_id, begin, end, MediaDataEntity.MEDIA_STATE_RECYCLE);

        // 将日历设置都15天之后
        calendar.add(Calendar.DATE, 8);

        // end对象重新设置为15天之后
        end = calendar.getTime();

        // 查询回收站中该套餐系统的15天内过期的媒体数
        int recycle_15 = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                user.getUserId(), category_id, begin, end, MediaDataEntity.MEDIA_STATE_RECYCLE);

        result.put("recycle_total", recycle_total);
        result.put("recycle_7", recycle_7);
        result.put("recycle_15", recycle_15);

        return result;

    }

}
