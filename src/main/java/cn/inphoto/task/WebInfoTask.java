package cn.inphoto.task;

import cn.inphoto.dao.UserCategoryDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.user.CodeWebInfo;
import cn.inphoto.dbentity.user.PicWebinfo;
import cn.inphoto.dbentity.user.ShareInfo;
import cn.inphoto.dbentity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebInfoTask {

    @Resource
    private UserDao userDao;

    @Resource
    private WebinfoDao webinfoDao;

    private static WebInfoTask webInfoTask;

    public static void setWebInfoTask(WebInfoTask webInfoTask) {
        WebInfoTask.webInfoTask = webInfoTask;
    }

    @PostConstruct
    public void init() {
        webInfoTask = this;
        webInfoTask.userDao = this.userDao;
        webInfoTask.webinfoDao = this.webinfoDao;
    }

    public static String dataPath;

    @Value("#{properties['data_path']}")
    public void setDataPath(String data_path) {
        dataPath = data_path;
    }


    @Scheduled(cron = "0 0 4 * * ? ")
    public void cleanOverImage() throws IOException {

        List<User> userList = userDao.findAll();

        for (User u : userList
                ) {

            System.out.println("清理" + u.toString() + "设置文件夹");

            List<PicWebinfo> picWebinfoList = webinfoDao.findPicAllByUser_id(u.getUserId());

            List<CodeWebInfo> codeWebInfoList = webinfoDao.findCodeAllByUser_id(u.getUserId());

            List<ShareInfo> shareInfoList = webinfoDao.findShareAllByUser_id(u.getUserId());

            List<String> filePathList = new ArrayList<>();

            if (!picWebinfoList.isEmpty()) {
                for (PicWebinfo p : picWebinfoList
                        ) {
                    filePathList.add(p.getBackground());
                }
            }

            if (!codeWebInfoList.isEmpty()) {
                for (CodeWebInfo c : codeWebInfoList
                        ) {
                    filePathList.add(c.getBackground());
                    filePathList.add(c.getButtonPic());
                }
            }

            if (!shareInfoList.isEmpty()) {
                for (ShareInfo s : shareInfoList
                        ) {
                    filePathList.add(s.getShareChatsIcon());
                    filePathList.add(s.getShareMomentsIcon());
                }
            }

            if (!filePathList.isEmpty()) {
                String settingPath = dataPath + File.separator + u.getUserId() + File.separator + "settings";
                File settingFile = new File(settingPath);
                File[] files = settingFile.listFiles();
                assert files != null;
                for (File f : files
                        ) {
                    boolean flag = false;
                    for (String s : filePathList
                            ) {
                        if (s.equals(f.getCanonicalPath())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("删除" + f.getName());
                        f.delete();
                    }
                }
            }

        }

    }


}
