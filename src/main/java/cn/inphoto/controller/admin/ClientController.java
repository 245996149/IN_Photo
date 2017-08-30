package cn.inphoto.controller.admin;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.page.TablePage;
import cn.inphoto.dbentity.page.UserPage;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.util.dbUtil.ClientUtil;
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
import static cn.inphoto.util.PasswordUtil.getRandomPassword;
import static cn.inphoto.util.ResultMapUtil.createResult;

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

        List<User> users = userDao.findByPage(userPage);

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

        User user = userDao.findByEmail(email);

        if (user != null) {
            return createResult(false, "已经存在该email");
        }

        return createResult(true, "通过email验证");
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

        String pwd = getRandomPassword(10);

        user.setPassword(getMD5(pwd));
        user.setAdminId(adminInfo.getAdminId());
        user.setUserState("0");

        if (utilDao.save(user)) {

            //TODO 添加发送邮件逻辑
            // 发送邮件
            if (sendEmail) {
                sendMail(user.getEmail(), "IN Photo注册邮件",
                        "<div>尊敬的" + user.getEmail() + "您好！ 感谢您成功注册IN Photo的会员。</div>" +
                                "<div><includetail><p>我们将为您提供最贴心的服务，祝您使用愉快！</p>" +
                                "<p>您在IN Photo管理中心的登录帐号：</p><p>帐号：" + user.getEmail() + "</p>" +
                                "<p>密码：" + pwd + "</p><p>请您及时登录系统填写用户名，更改密码。</p>" +
                                "<p><a href='" + emailManageAdd + "'>点击前往IN Photo管理中心</a></p>" +
                                "<p>此邮件为系统自动发送，请勿直接回复该邮件</p></includetail></div>");
            } else {
                System.out.println(pwd);
            }

        } else {

            // 保存失败，跳转到错误页面
            return "redirect:/admin/login/error.do";

        }

        // 判断是否现在添加套餐
        if (category) {
            return "redirect:toAddCategory.do?user_id=" + user.getUserId();
        } else {
            return "redirect:toClient.do";
        }
    }


    /**
     * 更改客户状态
     *
     * @param user_id    客户
     * @param user_state 变更的状态
     * @param session    服务器缓存
     * @return 是否成功
     */
    @RequestMapping("/changeUserState.do")
    @ResponseBody
    public Map changeUserState(Long user_id, String user_state, HttpSession session) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        User user = userDao.findByUser_id(user_id);

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return createResult(false, "您没有权限操作该用户");
            }
        }

        if (User.USER_STATE_STOP.equals(user_state)) {
            user.setUserState(User.USER_STATE_STOP);
        } else if (User.USER_STATE_NORMAL.equals(user_state)) {
            user.setUserState(User.USER_STATE_NORMAL);
        } else {
            return createResult(false, "请输入正确的参数");
        }

        if (!utilDao.update(user)) {
            return createResult(false, "更新数据时发生了错误，请稍候再试");
        }

        if (User.USER_STATE_STOP.equals(user_state)) {
            List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndState(user_id, UserCategory.USER_CATEGORY_STATE_NORMAL);
            List<Long> userCategoryIds = new ArrayList<>();
            for (UserCategory uc : userCategoryList
                    ) {
                userCategoryIds.add(uc.getUserCategoryId());
            }
            if (!userCategoryIds.isEmpty()) {
                return ClientUtil.stopUserCategory(userCategoryIds);
            }
        }

        return createResult(true, "更新成功");

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
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date, byte watermark,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date, Integer number) {

        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndCategory_id(user_id, category_id);

        // 清除队列中已经过期的套餐
        int num = userCategoryList.size();
        for (int i = 0; i < num; i++) {

            UserCategory uc = userCategoryList.get(i);

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

            if (inputBeginTime >= ucBeginTime && inputBeginTime <= ucEndTime) {
                // 输入的开始时间在这个套餐的时间范围内
                beginTimeFlag = true;
                break;
            } else if (inputEndTime >= ucBeginTime && inputEndTime <= ucEndTime) {
                // 输入的结束时间在这个套餐的时间范围内
                endTimeFlag = true;
                break;
            }
        }

        if (beginTimeFlag || endTimeFlag) {

            return createResult(false, "该时间段内存在相同的套餐");

        }

        // 更新数据
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
        if (watermark == UserCategory.USER_CATEGORY_IS_NOT_WATERMARK) {
            userCategory.setWatermark(UserCategory.USER_CATEGORY_IS_NOT_WATERMARK);
        } else {
            userCategory.setWatermark(UserCategory.USER_CATEGORY_IS_WATERMARK);
        }

        // 写入数据
        if (!utilDao.save(userCategory)) {
            return createResult(false, "写入数据时发生了点小意外，请稍后再试");
        }

        return createResult(true, "添加成功，请等待跳转");
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
     * @param userCategory_id 套餐
     * @param session         服务器缓存
     * @return 是否成功
     */
    @RequestMapping("/stopUserCategory.do")
    @ResponseBody
    public Map stopUserCategory(Long userCategory_id, HttpSession session) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        // 查找对应的用户套餐
        UserCategory userCategory = userCategoryDao.findByUser_category_id(userCategory_id);

        // 查找对应的用户
        User user = userDao.findByUser_id(userCategory.getUserId());

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return createResult(false, "您没有权限操作该用户");
            }
        }

        // 更新用户套餐
        userCategory.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_OVER);
        userCategory.setEndTime(new Timestamp(System.currentTimeMillis()));

        // 更新数据库中的用户套餐
        if (!utilDao.update(userCategory)) {

            // 更新失败
            return createResult(false, "更新对应的用户套餐时发生了错误，请稍后再试。");

        }

        return createResult(true,
                "更新用户套餐成功，媒体数据将在下一个更新周期自动更新状态。");

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
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date,byte watermark,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date, Integer number) {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        UserCategory userCategory = userCategoryDao.findByUser_category_id(user_category_id);

        User user = userDao.findByUser_id(userCategory.getUserId());

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return createResult(false, "您没有权限操作该数据");
            }
        }

        if (UserCategory.USER_CATEGORY_STATE_OVER.equals(userCategory.getUserCategoryState())) {
            return createResult(false, "用户套餐已经过期，无法对其进行数据修改");
        }

        List<UserCategory> userCategoryList = userCategoryDao.findByUser_idAndCategory_id(
                user.getUserId(), userCategory.getCategoryId());

        // 清除队列中已经过期的套餐
        int num = userCategoryList.size();
        for (int i = 0; i < num; i++) {

            UserCategory uc = userCategoryList.get(i);

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

            return createResult(false, "该时间段内存在相同的套餐");

        }

        // 更新数据
        userCategory.setEndTime(new Timestamp(inputEndTime));
        userCategory.setMediaNumber(number);
        if (getTodayDate().getTime() / 1000 == begin_date.getTime() / 1000) {
            userCategory.setUserCategoryState(UserCategory.USER_CATEGORY_STATE_NORMAL);
        }
        if (watermark != userCategory.getWatermark()) {
            userCategory.setWatermark(watermark);
        }

        // 写入数据
        if (!utilDao.update(userCategory)) {
            return createResult(false, "写入数据时发生了点小意外，请稍后再试");
        }

        return createResult(true, "修改套餐数据成功，请等待跳转");

    }

    /**
     * 前往查看媒体数据页面
     *
     * @param session   服务器缓存
     * @param model     页面数据缓存
     * @param tablePage 页面分页对象
     * @return 页面
     */
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

    /**
     * 重置密码
     *
     * @param user_id 用户id
     * @param session 服务器缓存
     * @return 是否成功
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/resetPassword.do")
    public Map resetPassword(Long user_id, HttpSession session) throws Exception {

        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminUser");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");

        User user = userDao.findByUser_id(user_id);

        // 判断是否有管理员权限
        if (!isAdmin) {
            //没有管理员权限，判断是否有管理权限
            if (user.getAdminId() != adminInfo.getAdminId()) {
                return createResult(false, "您没有权限操作该数据");
            }
        }

        String pwd = getRandomPassword(10);

        user.setPassword(getMD5(pwd));

        if (!utilDao.save(user)) {

            return createResult(false, "在写入数据时发生了错误，请稍候再试");

        }

        // 发送邮件
        if (sendEmail) {
            sendMail(user.getEmail(), "IN Photo注册邮件",
                    "<div>尊敬的" + user.getEmail() + "您好！ </div>" +
                            "<div><includetail><p>我们将为您提供最贴心的服务，祝您使用愉快！</p>" +
                            "<p>您在IN Photo管理中心的登录帐号：</p><p>帐号：" + user.getEmail() + "</p>" +
                            "<p>密码：" + pwd + "</p><p>请您及时登录系统填写用户名，更改密码。</p>" +
                            "<p><a href='" + emailManageAdd + "'>点击前往IN Photo管理中心</a></p>" +
                            "<p>此邮件为系统自动发送，请勿直接回复该邮件</p></includetail></div>");
        } else {
            System.out.println(pwd);
        }

        return createResult(true, "重置密码成功，密码已经发送至客户绑定的邮箱中");

    }

}
