package cn.inphoto.user.util;

import cn.inphoto.user.dbentity.UsersEntity;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Properties;

/**
 * Created by kaxia on 2017/6/12.
 */
public class DirUtil {

    private static Logger logger = Logger.getLogger(DirUtil.class);

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
     * 获取info.properties中的配置信息
     *
     * @param str 传入的key
     * @return 返回的value
     */
    public static String getConfigInfo(String str) {

        // 创建配置文件对象
        Properties p = new Properties();
        // 创建返回字符串对象
        String reStr = null;

        // 将文件通过文件输入流打开
        try(InputStream in = DirUtil.class.getResourceAsStream("/IN_Photo_config.properties");) {
            // 配置文件对象读取文件输入流
            p.load(in);
            // 关闭文件输入流
            in.close();
            // 配置文件对象读取参数并将其赋予返回字符串对象
            reStr = p.getProperty(str);
        } catch (Exception e) {
            logger.info("读取文件错误：" + getErrorInfoFromException(e));
        }
        return reStr;
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
     * @param user 隶属于的用户
     * @return
     * @throws IOException
     */
    public static String createSettingsPic(MultipartFile file, UsersEntity user) throws IOException {
        // 获取上传文件名
        String fileName = file.getOriginalFilename();
        // 获取配置文件中的存储数据的根目录
        String path = getConfigInfo("data_path");
        //设置InPhoto媒体数据用户存储的目录
        String userPath = path + File.separator + user.getUserId();
        createDirectory(userPath);
        //设置InPhoto媒体数据用户设置存储的目录
        String settingsPath = userPath + File.separator + "settings";
        createDirectory(settingsPath);
        // 获取图片尾缀
        String tempFileName[] = fileName.split("\\.");
        // 设置文件路径
        String filePath = settingsPath + File.separator + "settings_pic_" + (int) ((Math.random() * 9 + 1) * 10000) + "." + tempFileName[1];
        // 创建文件
        File localFile = new File(filePath);
        // 将上传文件转移到创建的文件中
        file.transferTo(localFile);

        return filePath;
    }

}
