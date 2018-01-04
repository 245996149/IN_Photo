package cn.inphoto.controller.user;

import cn.inphoto.dao.CategoryDao;
import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.ShareClickDataDao;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.ShareClickData;
import cn.inphoto.dbentity.user.User;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user/statistic")
public class StatisticController {

    private static Logger logger = Logger.getLogger(StatisticController.class);

    @Resource
    private ShareClickDataDao shareClickDataDao;

    @Resource
    private MediaDataDao mediaDataDao;

    @RequestMapping("/toStatistic.do")
    public String toStatistic(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        model.addAttribute("user", user);

        session.setAttribute("nav_code", UserController.STATISTIC_CODE);
        return "user/statistic";
    }

    @RequestMapping("/getBasicData.do")
    @ResponseBody
    public Map[] getBasicData(HttpSession session, Integer category_id,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date) {

        User user = (User) session.getAttribute("loginUser");

        int days = (int) Math.abs((end_date.getTime() - begin_date.getTime())
                / (24 * 60 * 60 * 1000) + 1);

        // 创建返回的数组
        Map[] maps = new HashMap[days];

        // 创建日期编码对象
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");

        // 创建日历对象
        Calendar calendar = Calendar.getInstance();

        // 给日历对象设置时间
        calendar.setTime(end_date);
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

        // 循环查询七天内的点击量
        for (int i = days; i > 0; i--) {

            if (category_id == null || category_id == 0) {
                category_id = null;
            }

            int a = shareClickDataDao.countByTime(
                    user.getUserId(), category_id, begin, end);

            int b = mediaDataDao.countByUser_idAndCategory_idAndMedia_stateAndCreate_Time(
                    user.getUserId(), category_id, begin, end, MediaData.MediaState.Normal);

            // 创建返回的Map对象
            Map<String, Object> result = new HashMap<>();

            // 将数据添加到返回的map中
            result.put("name", format.format(begin));
            result.put("click_num", a);
            result.put("upload_num", b);

            // 将map对象添加到队列中
            maps[i - 1] = result;

            // 日期减少一天
            end = begin;
            calendar.add(Calendar.DATE, -1);
            begin = calendar.getTime();
        }

        return maps;
    }

    @RequestMapping("/getPhoneSankeyData.do")
    @ResponseBody
    public Map<String, Object> getPhoneSankeyData(HttpSession session, Integer category_id,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date) {
        User user = (User) session.getAttribute("loginUser");

        // 创建日历对象
        Calendar calendar = Calendar.getInstance();

        // 给日历对象设置时间
        calendar.setTime(end_date);
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

        if (category_id == null || category_id == 0) {
            category_id = null;
        }

        List<ShareClickData> shareClickData = shareClickDataDao.findByTime(user.getUserId(), category_id, begin, end);

        Set<String> nodes = new HashSet<>();

        List<Link> links = new ArrayList<>();

        nodes.add("设备");

        List<Link> links1 = new ArrayList<>();
        List<Link> links2 = new ArrayList<>();
        List<Link> links3 = new ArrayList<>();

        for (ShareClickData s : shareClickData
                ) {

            System.out.println(s.toString());

            nodes.add(s.getOsType().name());
            nodes.add(s.getOsVersion());
            nodes.add(s.getBrand());

            links1.add(new Link("设备", s.getOsType().name(), 1));
            links2.add(new Link(s.getOsType().name(), s.getOsVersion(), 1));
            links3.add(new Link(s.getOsVersion(), s.getBrand(), 1));
        }

        links.addAll(a(links1));
        links.addAll(a(links2));
        links.addAll(a(links3));

        Map<String, Object> result = new HashMap<>();

        result.put("nodes", nodes);
        result.put("links", links);
        return result;
    }

    public List<Link> a(List<Link> links) {

        System.out.println(1111111);

        List<Link> result = new ArrayList<>();

        for (Link l : links
                ) {
            if (result.isEmpty()) {
                result.add(l);
            } else {
                boolean flag = false;
                loop1:
                for (Link l2 : result
                        ) {
                    if (l2.equals(l)) {
                        flag = true;
                        l2.setValue(l2.getValue() + 1);
                        break loop1;
                    }
                }
                if (!flag) {
                    result.add(l);
                }
            }
        }
        return result;

    }

    class Link {
        private String source;
        private String target;
        private int value;

        public Link() {
        }

        public Link(String source, String target, int value) {

            this.source = source;
            this.target = target;
            this.value = value;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Link{" +
                    "source='" + source + '\'' +
                    ", target='" + target + '\'' +
                    ", value=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Link link = (Link) o;

            if (!source.equals(link.source)) return false;
            return target.equals(link.target);
        }

        @Override
        public int hashCode() {
            int result = source.hashCode();
            result = 31 * result + target.hashCode();
            return result;
        }
    }

}
