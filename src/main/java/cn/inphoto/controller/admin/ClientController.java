package cn.inphoto.controller.admin;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.TablePage;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

import static cn.inphoto.util.DateUtil.getTodayDate;
import static cn.inphoto.util.MD5Util.getMD5;
import static cn.inphoto.util.MailUtil.sendMail;

@Controller
@RequestMapping("/admin/clientManage")
@SessionAttributes("userPage")
public class ClientController {

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UtilDao utilDao;

    @Resource
    private AdminDao adminDao;

    @Resource
    private MediaDataDao mediaDataDao;

    @Resource
    private UserCategoryDao userCategoryDao;

    @Resource
    private MediaCodeDao mediaCodeDao;

    static char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /*
    注册邮件中的IN Photo管理中心地址
     */
    @Value("#{properties['EmailManageAdd']}")
    String emailManageAdd;

    @Value("#{properties['sendEmail']}")
    boolean sendEmail;

    /**
     * 前往客户管理页
     *
     * @param model    页面数据缓存
     * @param session  服务器缓存
     * @param userPage 页面分页对象
     * @return 页面
     */
    @RequestMapping("/toClient.do")
    public String toClient(Model model, HttpSession session, UserPage userPage) {
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        // 判断是否有管理员权限
        if (!isAdmin) {
            userPage.setAdminId(adminInfo.getAdminId());
        }

        userPage.setRows(userDao.countByPage(userPage));

//        System.out.println(userPage.toString());

        List<User> users = userDao.findByPage(userPage);

//        for (User u : users
//                ) {
//            System.out.println(u.toString());
//        }

        model.addAttribute("userList", users);
        model.addAttribute("userPage", userPage);

        return "admin/client/client_list";
    }

    /**
     * 验证数据库中是否存在该邮箱地址的客户
     *
     * @param email 邮箱地址
     * @return 是否存在
     */
    @RequestMapping("checkEmail.do")
    @ResponseBody
    public Map checkEmail(String email) {
        Map<String, Object> result = new HashMap<>();

        User user = userDao.findByEmail(email);

        if (user != null) {
            result.put("success", false);
            result.put("message", "已经存在该email");
            return result;
        }

        result.put("success", true);
        result.put("message", "通过email验证");
        return result;
    }

    /**
     * 前往添加客户页
     *
     * @param session 服务器缓存
     * @param user    客户
     * @return 页面
     */
    @RequestMapping("/toAddClient.do")
    public String toAddClient(HttpSession session, User user, boolean category) throws Exception {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");

        System.out.println(user.toString());
        System.out.println(category);

        final int maxNum = 62;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度

        StringBuilder pwd = new StringBuilder("");
        Random r = new Random();
        while (count < 10) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        user.setPassword(getMD5(pwd.toString()));
        user.setAdminId(adminInfo.getAdminId());
        user.setUserState("0");

        System.out.println(pwd.toString());
        System.out.println("加密后密码：" + getMD5(pwd.toString()));

        if (utilDao.save(user)) {

            //TODO 添加发送邮件逻辑
            // 发送邮件
            if (sendEmail) {
                sendMail(user.getEmail(), "IN Photo注册邮件",
                        "<div>尊敬的" + user.getEmail() + "您好！ 感谢您成功注册IN Photo的会员。</div>" +
                                "<div><includetail><p>我们将为您提供最贴心的服务，祝您使用愉快！</p>" +
                                "<p>您在IN Photo管理中心的登录帐号：</p><p>帐号：" + user.getEmail() + "</p>" +
                                "<p>密码：" + pwd.toString() + "</p><p>请您及时登录系统更改密码。</p>" +
                                "<p><a href='" + emailManageAdd + "'>点击前往IN Photo管理中心</a></p>" +
                                "<p>此邮件为系统自动发送，请勿直接回复该邮件</p></includetail></div>");
            }

        } else {

        }

        System.out.println(user.toString());

        // 判断是否现在添加套餐
        if (category) {
            return "redirect:toAddCategory.do?user_id=" + user.getUserId();
        } else {
            return "redirect:toClient.do";
        }
    }

    /**
     * 前往添加套餐页面
     *
     * @param user_id 用户id
     * @param model   页面数据缓存
     * @param session 服务器缓存
     * @return 页面
     */
    @RequestMapping("/toAddCategory.do")
    public String toAddCategory(Long user_id, Model model, HttpSession session) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        User user = userDao.findByUser_id(user_id);

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return "no_power";
            }
        }

        List<Category> categoryList = new ArrayList<>(adminInfo.getCategorySet());

        model.addAttribute("user", user);
        model.addAttribute("categoryList", categoryList);

        return "admin/client/client_category_add";

    }

    /**
     * 向客户添加一个套餐
     *
     * @param user_id     客户id
     * @param category_id 套餐id
     * @param begin_date  开始时间
     * @param end_date    结束时间
     * @param number      套餐数据量
     * @return 是否成功
     */
    @RequestMapping("/addCategory.do")
    @ResponseBody
    public Map addCategory(Long user_id, Integer category_id,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date, Integer number) {
        Map<String, Object> result = new HashMap<>();

        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndCategory_id(user_id, category_id);

        // 清除队列中已经过期的套餐
        int num = userCategoryList.size();
        for (int i = 0; i < num; i++) {

            UserCategory uc = userCategoryList.get(i);

//            System.out.println(uc.toString());
            if (UserCategory.USER_CATEGORY_STATE_OVER.equals(uc.getUserCategoryState())) {
                userCategoryList.remove(i);
                i--;
                num--;
            }
        }

        // 获取输入时间的时间戳
        long inputBeginTime = begin_date.getTime();
        long inputEndTime = end_date.getTime();

        boolean beginTimeFlag = false;
        boolean endTimeFlag = false;

        // 判断开始时间、结束时间是否在已存在套餐内
        for (UserCategory uc : userCategoryList
                ) {
            long ucBeginTime = uc.getBeginTime().getTime();
            long ucEndTime = uc.getEndTime().getTime();

            if (inputBeginTime > ucBeginTime && inputBeginTime < ucEndTime) {
                // 输入的开始时间在这个套餐的时间范围内
                beginTimeFlag = true;
                break;
            } else if (inputEndTime > ucBeginTime && inputEndTime < ucEndTime) {
                // 输入的结束时间在这个套餐的时间范围内
                endTimeFlag = true;
                break;
            }
        }

        if (beginTimeFlag || endTimeFlag) {

            result.put("success", false);
            result.put("message", "该时间段内存在相同的套餐");
            return result;

        }

        UserCategory userCategory = new UserCategory();
        userCategory.setUserId(user_id);
        userCategory.setCategoryId(category_id);
        userCategory.setBeginTime(new Timestamp(begin_date.getTime()));
        userCategory.setEndTime(new Timestamp(end_date.getTime()));
        userCategory.setMediaNumber(number);
        if (getTodayDate().getTime() / 1000 == begin_date.getTime() / 1000) {
            userCategory.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_NORMAL);
        } else {
            userCategory.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_NOT_START);
        }

//        System.out.println(userCategory.toString());

        if (utilDao.save(userCategory)) {
            result.put("success", true);
            result.put("message", "添加成功，请等待跳转");

        } else {
            result.put("success", false);
            result.put("message", "写入数据时发生了点小意外，请稍后再试");

        }

        return result;
    }

    /**
     * 前往用户套餐列表页
     *
     * @param user_id 用户id
     * @param session 服务器缓存
     * @param model   页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toCategoryList.do")
    public String toCategoryList(Long user_id, HttpSession session, Model model) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        User user = userDao.findByUser_id(user_id);

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return "no_power";
            }
        }

        List<Category> categoryList = categoryDao.findAll();

        // 找到所有用户套餐
        List<UserCategory> userCategoryList = userCategoryDao.findByUser_id(user_id);

        Map<Long, Integer> tempMap = new HashMap<>();

        for (UserCategory uc : userCategoryList
                ) {

            if (UserCategory.USER_CATEGORY_STATE_NORMAL.equals(uc.getUserCategoryState())) {

                // 查询套餐对应的正常状态的媒体总数
                int a = mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                        user.getUserId(), uc.getCategoryId(), Collections.singletonList(MediaData.MEDIA_STATE_NORMAL));

                // 将数据写入临时Map中以供页面调用
                tempMap.put(uc.getUserCategoryId(), (a * 100 / uc.getMediaNumber()));

            }

        }

        model.addAttribute("category", categoryList);
        model.addAttribute("user", user);
        model.addAttribute("tempMap", tempMap);
        model.addAttribute("userCategoryList", userCategoryList);

        return "admin/client/client_category_list";
    }

    /**
     * 停止客户的某一套餐
     *
     * @param user_id         客户
     * @param userCategory_id 套餐
     * @param session         服务器缓存
     * @return 是否成功
     */
    @RequestMapping("/stopUserCategory.do")
    @ResponseBody
    public Map stopUserCategory(Long user_id, Long userCategory_id, HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        User user = userDao.findByUser_id(user_id);

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                result.put("success", false);
                result.put("message", "您没有权限操作该用户");
                return result;
            }
        }

        // 查找对应的用户套餐
        UserCategory userCategory = userCategoryDao.findByUser_category_id(userCategory_id);

        // 更新用户套餐
        userCategory.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_OVER);
        userCategory.setEndTime(new Timestamp(System.currentTimeMillis()));

        // 更新数据库中的用户套餐
        if (!utilDao.update(userCategory)) {

            // 更新失败
            result.put("success", false);
            result.put("message", "更新对应的用户套餐时发生了错误，请稍后再试。");
            return result;

        }

        // 查找对应的媒体数据
        List<MediaData> mediaDataList = mediaDataDao.findByUser_idAndCategory_idAndState(
                user_id, userCategory.getCategoryId(), MediaData.MEDIA_STATE_NORMAL);

        // 判断是否有对应的媒体数据，没有媒体数据跳过更新媒体数据状态
        if (mediaDataList.size() != 0) {

            // 更新查找到的媒体数据状态
            for (MediaData m : mediaDataList
                    ) {
                m.setMediaState(MediaData.MEDIA_STATE_WILL_DELETE);
            }

            // 更新数据库中的媒体数据
            if (!mediaDataDao.updateMediaList(mediaDataList)) {

                // 更新失败
                result.put("success", true);
                result.put("message", "更新用户套餐成功，但是在更新对应的媒体数据是发生了错误，媒体数据将在下一个更新周期自动更新状态。");
                return result;

            }

        }

        result.put("success", true);
        result.put("message", "更新用户套餐成功，更新对应的媒体数据成功");
        return result;

    }

    /**
     * 前往更新套餐信息页面
     *
     * @param userCategory_id 用户套餐id
     * @param session         服务器缓存
     * @param model           页面数据缓存
     * @return 页面
     */
    @RequestMapping("/toUpdateCategory.do")
    public String toUpdateCategory(Long userCategory_id, HttpSession session, Model model) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        UserCategory userCategory = userCategoryDao.findByUser_category_id(userCategory_id);

        User user = userDao.findByUser_id(userCategory.getUserId());

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return "no_power";
            }
        }

        if (UserCategory.USER_CATEGORY_STATE_OVER.equals(userCategory.getUserCategoryState())) {
            return "no_power";
        }

        List<Category> categoryList = new ArrayList<>(adminInfo.getCategorySet());

        model.addAttribute("user", user);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("userCategory", userCategory);

        return "admin/client/client_category_update";
    }

    /**
     * 更新客户套餐
     *
     * @param user_category_id 客户套餐
     * @param session          服务器缓存
     * @param begin_date       生效时间
     * @param end_date         结束时间
     * @param number           数据量
     * @return 是否成功
     */
    @RequestMapping("/updateCategory.do")
    @ResponseBody
    public Map updateCategory(Long user_category_id, HttpSession session,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date, Integer number) {
        Map<String, Object> result = new HashMap<>();

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        UserCategory userCategory = userCategoryDao.findByUser_category_id(user_category_id);

        User user = userDao.findByUser_id(userCategory.getUserId());

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                result.put("success", false);
                result.put("message", "您没有权限操作该数据");
                return result;
            }
        }

        if (UserCategory.USER_CATEGORY_STATE_OVER.equals(userCategory.getUserCategoryState())) {
            result.put("success", false);
            result.put("message", "用户套餐已经过期，无法对其进行数据修改");
            return result;
        }

        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndCategory_id(
                user.getUserId(), userCategory.getCategoryId());

        // 清除队列中已经过期的套餐
        int num = userCategoryList.size();
        for (int i = 0; i < num; i++) {

            UserCategory uc = userCategoryList.get(i);

// System.out.println(uc.toString());
            // 清楚过期以及自身的用户套餐
            if (UserCategory.USER_CATEGORY_STATE_OVER.equals(uc.getUserCategoryState())
                    || uc.getUserCategoryId() == userCategory.getUserCategoryId()) {
                userCategoryList.remove(i);
                i--;
                num--;
            }
        }

        // 获取输入时间的时间戳
        long inputBeginTime = begin_date.getTime();
        long inputEndTime = end_date.getTime();

        boolean beginTimeFlag = false;
        boolean endTimeFlag = false;

        // 判断开始时间、结束时间是否在已存在套餐内
        for (UserCategory uc : userCategoryList
                ) {
            long ucBeginTime = uc.getBeginTime().getTime();
            long ucEndTime = uc.getEndTime().getTime();

            if (inputBeginTime > ucBeginTime && inputBeginTime < ucEndTime) {
                // 输入的开始时间在这个套餐的时间范围内
                beginTimeFlag = true;
                break;
            } else if (inputEndTime > ucBeginTime && inputEndTime < ucEndTime) {
                // 输入的结束时间在这个套餐的时间范围内
                endTimeFlag = true;
                break;
            }
        }

        if (beginTimeFlag || endTimeFlag) {

            result.put("success", false);
            result.put("message", "该时间段内存在相同的套餐");
            return result;

        }

        userCategory.setEndTime(new Timestamp(inputEndTime));
        userCategory.setMediaNumber(number);
        if (getTodayDate().getTime() / 1000 == begin_date.getTime() / 1000) {
            userCategory.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_NORMAL);
        }

        if (utilDao.update(userCategory)) {
            result.put("success", true);
            result.put("message", "修改套餐数据成功，请等待跳转");

        } else {
            result.put("success", false);
            result.put("message", "写入数据时发生了点小意外，请稍后再试");
        }

        return result;

    }

    @RequestMapping("/toClientMedia.do")
    public String toClientMedia(HttpSession session, Model model,
                                TablePage tablePage) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        User user = userDao.findByUser_id(tablePage.getUser_id());

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return "no_power";
            }
        }

//        System.out.println(tablePage.getMedia_state_list().isEmpty());

        // 判断页面数据对象中是否有相应数据，没有给予初始值
        if (tablePage.getMedia_state_list() == null || tablePage.getMedia_state_list().isEmpty()) {
            tablePage.setMedia_state_list(Arrays.asList(MediaData.MEDIA_STATE_NORMAL, MediaData.MEDIA_STATE_WILL_DELETE));
        }


        tablePage.setRows(mediaDataDao.countByUser_idAndCategory_idAndMedia_state(user.getUserId(), tablePage.getCategory_id(), tablePage.getMedia_state_list()));

        List<MediaData> mediaDataList = mediaDataDao.findByPage(tablePage);

        List<MediaCode> mediaCodeList = mediaCodeDao.findByUser_idAndCategory_id(user.getUserId(), tablePage.getCategory_id());

        List<Category> categoryList = categoryDao.findAll();

        model.addAttribute("category", categoryList);
        model.addAttribute("mediaDataList", mediaDataList);
        model.addAttribute("mediaCodeList", mediaCodeList);
        model.addAttribute("tablePage", tablePage);

        return "admin/client/client_media_list";
    }

}
