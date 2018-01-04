package cn.inphoto.util;

import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.PicWebInfo;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.log.UserLogLevel;
import cn.inphoto.task.MediaTask;
import com.aliyun.oss.OSSClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Properties;

/**
 * Created by kaxia on 2017/6/12.
 */
@Component
public class DirUtil {

    private static Logger logger = Logger.getLogger(DirUtil.class);

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
     * 检查文件夹是否已经创建，未创建则创建
     *
     * @param filePath 文件夹路径
     */
    public static void createDirectory(String filePath) {

        // 根据文件夹路径创建文件对象
        File file = new File(filePath);

        // 判断文件对象是否是文件夹、是否存在
        if (!file.exists() && !file.isDirectory()) {

            //文件对象不是文件夹、不存在，则创建文件夹
            file.mkdir();

        }
    }

    /**
     * 将错误信息以字符串输出
     *
     * @param e 错误信息
     * @return 字符串
     */
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }

    /**
     * 接受设置图片上传，并将其随机命名放置到设置文件夹下
     *
     * @param file 接收的文件
     * @return
     * @throws IOException
     */
    public static MediaData createSettingsPic(MultipartFile file, User user, int category_id) throws IOException {
        // 获取上传文件名
        String fileName = file.getOriginalFilename();
        //设置InPhoto媒体数据用户设置存储的目录
        String mediaKey = user.getUserId() + "/" + "settings/settings_pic_" +
                (int) ((Math.random() * 9 + 1) * 10000) + "." + fileName.split("\\.")[1];

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        client.putObject(bucketName, mediaKey, file.getInputStream());

        client.shutdown();

        MediaData mediaData = new MediaData();
        mediaData.setMediaKey(mediaKey);
        mediaData.setMediaName("setting" + System.currentTimeMillis());
        mediaData.setUserId(user.getUserId());
        mediaData.setCategoryId(category_id);
        mediaData.setMediaType(MediaData.MediaType.SettingsData);
        mediaData.setMediaState(MediaData.MediaState.Normal);

        return mediaData;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            assert children != null;
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    logger.log(UserLogLevel.TASK,
                            "遍历删除文件夹" + (new File(dir, aChildren)).getAbsolutePath() + "时发生了错误");
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
