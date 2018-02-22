package cn.inphoto.task;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.UserDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.log.UserLogLevel;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WebInfoTask {

    private Logger logger = Logger.getLogger(WebInfoTask.class);

    @Resource
    private UserDao userDao;

    @Resource
    private MediaDataDao mediaDataDao;

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
        webInfoTask.mediaDataDao = this.mediaDataDao;
    }

    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String bucketName;

    @Value("#{properties['AliyunOSSEndpoint']}")
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Value("#{properties['AliyunAccessKeyId']}")
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    @Value("#{properties['AliyunAccessKeySecret']}")
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Value("#{properties['AliyunOSSBucketName']}")
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 清理无效的设置文件
     */
    @Scheduled(cron = "0 0 4 * * ? ")
    public void cleanOverImage() {

        List<User> userList = userDao.findAll();

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        for (User u : userList
                ) {

            List<PicWebInfo> picWebInfoList = webinfoDao.findPicAllByUser_id(u.getUserId());

            List<CodeWebInfo> codeWebInfoList = webinfoDao.findCodeAllByUser_id(u.getUserId());

            List<ShareInfo> shareInfoList = webinfoDao.findShareAllByUser_id(u.getUserId());

            List<MediaData> mediaDataList = new ArrayList<>();

            if (!picWebInfoList.isEmpty()) {
                for (PicWebInfo p : picWebInfoList
                        ) {
                    mediaDataList.add(p.getBackgroundMedia());
                }
            }

            if (!codeWebInfoList.isEmpty()) {
                for (CodeWebInfo c : codeWebInfoList
                        ) {
                    mediaDataList.add(c.getBackgroundMedia());
                    mediaDataList.add(c.getButtonPicMedia());
                }
            }

            if (!shareInfoList.isEmpty()) {
                for (ShareInfo s : shareInfoList
                        ) {
                    mediaDataList.add(s.getChatsIconMedia());
                    mediaDataList.add(s.getMomentsIconMedia());
                }
            }

            if (!mediaDataList.isEmpty()) {

                boolean isDelete = false;

                String keyPrifex = u.getUserId() + "/settings/";
                ObjectListing objectListing = client.listObjects(bucketName, keyPrifex);
                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
                StringBuilder a = new StringBuilder();

                if (sums != null && !sums.isEmpty()) {
                    for (OSSObjectSummary s : sums) {
                        boolean flag = false;

                        for (MediaData m : mediaDataList
                                ) {
                            try {
                                if (s.getKey().equals(m.getMediaKey())) {
                                    flag = true;
                                    break;
                                }
                            } catch (Exception e) {
                                logger.log(UserLogLevel.TASK, "异常对象：" + m.toString());
                                logger.log(UserLogLevel.TASK, "清理无效的设置文件,media_id=" + m.getMediaId() + "的媒体数据key为空");
                            }
                        }
                        if (!flag) {
                            isDelete = true;
                            MediaData md = mediaDataDao.findByMediaKey(s.getKey());
                            md.setMediaState(MediaData.MediaState.Delete);
                            md.setDeleteTime(new Timestamp(new Date().getTime()));
                            md.setOverTime(new Timestamp(new Date().getTime()));
                            client.deleteObject(bucketName, s.getKey());
                            a.append(s.getKey()).append("、");
                        }
                    }
                }

                if (isDelete) {
                    MDC.put("user_info", "user_id=" + u.getUserId());
                    logger.log(UserLogLevel.TASK,
                            "清理user_id=" + u.getUserId() + " 的用户的无用的设置图片。共清理了OSSKey为：" + a + " 的设置图片");
                }

                client.shutdown();

            }

        }

    }


}
