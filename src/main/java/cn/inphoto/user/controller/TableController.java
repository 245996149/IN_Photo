package cn.inphoto.user.controller;

import cn.inphoto.user.dao.*;
import cn.inphoto.user.dbentity.*;
import cn.inphoto.user.dbentity.page.TablePage;
import cn.inphoto.user.log.UserLog;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaxia on 2017/6/12.
 */
@Controller
@RequestMapping("/table")
public class TableController {

    private static Logger logger = Logger.getLogger(TableController.class);

    @Resource
    MediaDataDao mediaDataDao;

    @Resource
    MediaCodeDao mediaCodeDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    ShareDataDao shareDataDao;

    @Resource
    UtilDao utilDao;

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

        tablePage.setMedia_state(MediaDataEntity.MEDIA_STATE_NORMAL);

        List<MediaDataEntity> mediaDataList = mediaDataDao.findByPage(tablePage);

        List<MediaCodeEntity> mediaCodeList = mediaCodeDao.findByUser_idAndCategory_id(usersEntity.getUserId(), tablePage.getCategory_id());

        model.addAttribute("mediaDataList", mediaDataList);
        model.addAttribute("mediaCodeList", mediaCodeList);
        model.addAttribute("tablePage", tablePage);

        session.setAttribute("nav_code", UserController.TABLE_CODE);

        return "user/table";
    }

    @RequestMapping("/toRecycle.do")
    public String toRecycle(Model model, HttpSession session, TablePage tablePage) {

        UsersEntity usersEntity = (UsersEntity) session.getAttribute("loginUser");

        tablePage.setUser_id(usersEntity.getUserId());

        tablePage.setRows(mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                usersEntity.getUserId(), null, MediaDataEntity.MEDIA_STATE_RECYCLE));

        tablePage.setMedia_state(MediaDataEntity.MEDIA_STATE_RECYCLE);

        List<MediaDataEntity> mediaDataList = mediaDataDao.findByPage(tablePage);

        Map<Long, Long> tempMap = new HashMap<>();

        Calendar calendarNow = Calendar.getInstance();

        Calendar calendarTemp = Calendar.getInstance();

        for (MediaDataEntity m : mediaDataList
                ) {
            calendarTemp.setTime(m.getOverTime());
            long diffDays = (calendarTemp.getTimeInMillis() - calendarNow.getTimeInMillis()) / (1000 * 60 * 60 * 24);
            tempMap.put(m.getMediaId(), diffDays + 1);
        }

        model.addAttribute("mediaDataList", mediaDataList);
        model.addAttribute("tablePage", tablePage);
        model.addAttribute("tempMap", tempMap);

        session.setAttribute("nav_code", UserController.TABLE_CODE);

        return "user/recycle";

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

        // System.out.println(userCategory);

        result.put("use", media_num);
        result.put("remaining", (userCategory.getMediaNumber() - media_num));

        // System.out.println(result.get("remaining"));

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

    /**
     * 获取回收站媒体数据过期情况信息
     *
     * @param session session
     * @return 回收站媒体数据过期情况信息
     */
    @RequestMapping("/getRecycleInfoTotal.do")
    @ResponseBody
    public Map getRecycleInfoTotal(HttpSession session) {

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
                user.getUserId(), null, MediaDataEntity.MEDIA_STATE_RECYCLE);

        // 查询回收站中该套餐系统的7天内过期的媒体数
        int recycle_7 = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                user.getUserId(), null, begin, end, MediaDataEntity.MEDIA_STATE_RECYCLE);

        // 将日历设置都15天之后
        calendar.add(Calendar.DATE, 8);

        // end对象重新设置为15天之后
        end = calendar.getTime();

        // 查询回收站中该套餐系统的15天内过期的媒体数
        int recycle_15 = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                user.getUserId(), null, begin, end, MediaDataEntity.MEDIA_STATE_RECYCLE);

        result.put("recycle_total", recycle_total);
        result.put("recycle_7", recycle_7);
        result.put("recycle_15", recycle_15);

        return result;

    }

    /**
     * 将媒体数据移动到回收站中
     *
     * @param media_id 媒体数据id
     * @return 是否成功将媒体数据移动到回收站中
     */
    @RequestMapping("/deleteMediaData.do")
    @ResponseBody
    public Map deleteMediaData(Long media_id, HttpSession session) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        // 创建返回的数组
        Map<String, Object> result = new HashMap<>();

        // 查找media_id对应的mediaData
        MediaDataEntity mediaData = mediaDataDao.findByMedia_id(media_id);

        Date date = new Date();

        mediaData.setMediaState(MediaDataEntity.MEDIA_STATE_RECYCLE);
        mediaData.setDeleteTime(new Timestamp((date.getTime())));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30);

        mediaData.setOverTime(new Timestamp(calendar.getTimeInMillis()));

        MDC.put("user_id", user.getUserId());
        MDC.put("category_id", mediaData.getCategoryId());

        if (utilDao.update(mediaData)) {

            result.put("success", true);
            result.put("message", "已经将媒体数据移动到回收站中，数据有30天的缓存状态，30天后将完全删除;");

        } else {

            result.put("success", false);
            result.put("message", "媒体数据删除失败，请稍后再试");

        }

        logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 对media_id=" +
                mediaData.getMediaId() + " 的媒体数据执行了移动到回收站的操作，操作结果为：" + result.toString());

        return result;

    }


    /**
     * 将多个媒体数据移动到回收站中
     *
     * @param media_id_list 媒体数据ID队列
     * @return 是否成功将媒体数据移动到回收站中
     */
    @RequestMapping("/deleteMediaDataList.do")
    @ResponseBody
    public Map deleteMediaDataList(String media_id_list, HttpSession session) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 将接收到的数组转换为JSONArray
        JSONArray jsonArray = new JSONArray(media_id_list);

        // 创建用于查询的media_ids队列
        List<Long> media_ids = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            media_ids.add(jsonArray.getLong(i));
        }

        // 加载数据库中的对象
        List<MediaDataEntity> mediaDataList = mediaDataDao.findByMedia_ids(media_ids);

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30);

        for (MediaDataEntity m : mediaDataList
                ) {
            m.setMediaState(MediaDataEntity.MEDIA_STATE_RECYCLE);
            m.setDeleteTime(new Timestamp(date.getTime()));
            m.setOverTime(new Timestamp(calendar.getTimeInMillis()));
        }

        MDC.put("user_id", user.getUserId());

        if (mediaDataDao.updateMediaList(mediaDataList)) {

            result.put("success", true);
            result.put("message", "更新成功！");

        } else {

            result.put("success", false);
            result.put("message", "更新失败，请稍后再试！");

        }

        logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 对media_id=" +
                media_id_list + " 的媒体数据执行了移动到回收站的操作，操作结果为：" + result.toString());

        return result;
    }

    /**
     * 彻底清除媒体数据
     *
     * @param media_id 媒体数据的id
     * @return 是否成功将媒体数据彻底删除
     */
    @RequestMapping("/cleanMediaData.do")
    @ResponseBody
    public Map cleanMediaData(Long media_id, HttpSession session) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 查找media_id对应的mediaData
        MediaDataEntity mediaData = mediaDataDao.findByMedia_id(media_id);

        mediaData.setMediaState(MediaDataEntity.MEDIA_STATE_DELETE);
        mediaData.setOverTime(new Timestamp(System.currentTimeMillis()));

        MDC.put("user_id", user.getUserId());
        MDC.put("category_id", mediaData.getCategoryId());

        if (utilDao.update(mediaData)) {

            result.put("success", true);
            result.put("message", "已经彻底删除该媒体数据");

        } else {

            result.put("success", false);
            result.put("message", "媒体数据删除失败，请稍后再试");

        }

        logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 对media_id=" +
                mediaData.getMediaId() + " 的媒体数据执行了彻底删除的操作，操作结果为：" + result.toString());

        return result;

    }

    /**
     * 彻底清除媒体数据
     *
     * @param media_id_list 媒体数据ID队列
     * @return 是否成功将媒体数据彻底删除
     */
    @RequestMapping("/cleanMediaDataList.do")
    @ResponseBody
    public Map cleanMediaDataList(String media_id_list, HttpSession session) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 将接收到的数组转换为JSONArray
        JSONArray jsonArray = new JSONArray(media_id_list);

        // 创建用于查询的media_ids队列
        List<Long> media_ids = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            media_ids.add(jsonArray.getLong(i));
        }

        // 加载数据库中的对象
        List<MediaDataEntity> mediaDataList = mediaDataDao.findByMedia_ids(media_ids);

        // 循环更新队列中的媒体数据的信息
        for (MediaDataEntity m : mediaDataList
                ) {
            m.setMediaState(MediaDataEntity.MEDIA_STATE_DELETE);
            m.setOverTime(new Timestamp(System.currentTimeMillis()));
        }

        MDC.put("user_id", user.getUserId());

        // 更新数据库中媒体数据的信息
        if (mediaDataDao.updateMediaList(mediaDataList)) {

            result.put("success", true);
            result.put("message", "更新成功！");

        } else {

            result.put("success", false);
            result.put("message", "更新失败，请稍后再试！");

        }

        logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 对media_id=" +
                media_id_list + " 的媒体数据执行了彻底删除的操作，操作结果为：" + result.toString());

        return result;

    }


    /**
     * 还原媒体数据
     *
     * @param media_id 媒体数据的id
     * @return 是否成功将媒体数据彻底删除
     */
    @RequestMapping("/reductionMediaData.do")
    @ResponseBody
    public Map reductionMediaData(Long media_id, HttpSession session) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 查找media_id对应的mediaData
        MediaDataEntity mediaData = mediaDataDao.findByMedia_id(media_id);

        // 查询该媒体数据对应的系统
        UserCategoryEntity userCategory = userCategoryDao.findByUser_idAndCategory_id(
                user.getUserId(), mediaData.getCategoryId(), UserCategoryEntity.USER_CATEGORY_STATE_NORMAL);

        MDC.put("user_id", user.getUserId());
        MDC.put("category_id", mediaData.getCategoryId());

        // 判断系统是否过期
        if (userCategory == null) {

            result.put("success", false);
            result.put("message", "系统已经过期失效，无法还原。请联系客服。");
            logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 尝试还原回收站中media_id为：" +
                    mediaData.getMediaId() + "的数据，返回的结果是：" + result.toString());
            return result;

        }

        // 判断数据库中该系统草滩媒体数据总量是否超过套餐总量
        if (mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                user.getUserId(), mediaData.getCategoryId(), MediaDataEntity.MEDIA_STATE_NORMAL) >= userCategory.getMediaNumber()) {

            result.put("success", false);
            result.put("message", "该媒体数据隶属于的系统已经达到最大媒体数据容量，请联系客服另行购买。");
            logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 尝试还原回收站中media_id为：" +
                    mediaData.getMediaId() + "的数据，返回的结果是：" + result.toString());
            return result;

        }

        // 给媒体数据信息赋予新的值
        mediaData.setMediaState(MediaDataEntity.MEDIA_STATE_NORMAL);
        mediaData.setOverTime(null);
        mediaData.setDeleteTime(null);

        // 更新数据库中媒体的数据
        if (!utilDao.update(mediaData)) {

            result.put("success", false);
            result.put("message", "媒体数据还原失败，请稍后再试");

        } else {

            result.put("success", true);
            result.put("message", "已经还原该媒体数据");

        }

        logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 尝试还原回收站中media_id为：" +
                mediaData.getMediaId() + "的数据，返回的结果是：" + result.toString());

        return result;

    }


    /**
     * 还原媒体数据
     *
     * @param media_id_list 媒体数据ID队列
     * @return 是否成功将媒体数据彻底删除
     */
    @RequestMapping("/reductionMediaDataList.do")
    @ResponseBody
    public Map reductionMediaDataList(String media_id_list, HttpSession session, HttpServletRequest request) {

        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        Map<String, Object> result = new HashMap<>();

        // 将接收到的数组转换为JSONArray
        JSONArray jsonArray = new JSONArray(media_id_list);

        // 创建用于查询的media_ids队列
        List<Long> media_ids = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            media_ids.add(jsonArray.getLong(i));
        }

        MDC.put("user_id", user.getUserId());

        // 加载数据库中的对象
        List<MediaDataEntity> mediaDataList = mediaDataDao.findByMedia_ids(media_ids);

        // 创建用于判断媒体数据容量的临时map对象
        Map<Integer, Integer> tempMap = new HashMap<>();

        // 循环媒体数据对象队列，
        for (MediaDataEntity m : mediaDataList
                ) {

            // 判断临时map对象中是否有对应的key，有的话加1
            if (tempMap.containsKey(m.getCategoryId())) {
                tempMap.put(m.getCategoryId(), tempMap.get(m.getCategoryId()) + 1);
            } else {
                tempMap.put(m.getCategoryId(), 1);
            }
        }

        // 查询该用户所有正常状态的
        List<UserCategoryEntity> userCategoryList = (List<UserCategoryEntity>) session.getAttribute("allUserCategory");

        boolean flag = true;

        String msg = "选择的媒体数据中，";

        // 获取application对象
        ServletContext application = request.getSession().getServletContext();

        // 从application中获取所有套餐系统对象
        List<CategoryEntity> categoryList = (List<CategoryEntity>) application.getAttribute("category");

        // 遍历用户的套餐系统
        for (UserCategoryEntity u : userCategoryList
                ) {

            // 判断临时map对象中是否有key
            if (tempMap.containsKey(u.getCategoryId())) {

                // 取出临时map对象中的值
                int tempMapNum = tempMap.get(u.getCategoryId());

                // 获取数据库中的钙系统媒体总量
                int mediaDataTotal = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                        user.getUserId(), u.getCategoryId(), MediaDataEntity.MEDIA_STATE_NORMAL);

                // 比较两个值，判断是否能还原
                if ((tempMapNum + mediaDataTotal) >= u.getMediaNumber()) {

                    // 创建套餐系统对象
                    CategoryEntity categoryEntity = null;

                    // 遍历套餐系统对象，从中取出相应的套餐系统对象
                    for (CategoryEntity c : categoryList
                            ) {

                        if (c.getCategoryId() == u.getCategoryId()) {
                            categoryEntity = c;
                        }

                    }

                    flag = false;
                    msg += categoryEntity.getCategoryName() + ",";

                }

            }

        }

        // 队列中有媒体数据因容量原因无法还原
        if (!flag) {

            result.put("success", false);
            result.put("message", msg + "系统容量不足");
            logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 尝试还原回收站中media_id为：" +
                    media_id_list + "的数据，返回的结果是：" + result.toString());
            return result;

        }

        // 循环更新队列中的媒体数据的信息
        for (MediaDataEntity m : mediaDataList
                ) {
            m.setMediaState(MediaDataEntity.MEDIA_STATE_NORMAL);
            m.setOverTime(null);
            m.setDeleteTime(null);
        }

        // 更新数据库中媒体数据的信息
        if (!mediaDataDao.updateMediaList(mediaDataList)) {

            result.put("success", false);
            result.put("message", "更新失败，请稍后再试！");

        } else {

            result.put("success", true);
            result.put("message", "更新成功！");
        }

        logger.log(UserLog.USER, "用户user_id=" + user.getUserId() + " 尝试还原回收站中media_id为：" +
                media_id_list + "的数据，返回的结果是：" + result.toString());

        return result;

    }

}
