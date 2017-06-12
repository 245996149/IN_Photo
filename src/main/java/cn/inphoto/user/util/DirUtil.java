package cn.inphoto.user.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by kaxia on 2017/6/12.
 */
public class DirUtil {

    /**
     * 检查文件夹是否已经创建，未创建则创建
     *
     * @param filePath
     */
    public static void createDirectory(String filePath) {
        File file = new File(filePath);

        if (!file.exists() && !file.isDirectory()) {

            file.mkdir();

        }
    }

    /**
     * 获取info.properties中的配置信息
     *
     * @param str 传入的key
     * @return 返回的value
     */
    public static String getConfigInfo(String str) throws IOException {
        Properties p = new Properties();
        String reStr = null;
        InputStream in = null;
        try {
            in = DirUtil.class.getResourceAsStream("/IN_Photo_config.properties");
            p.load(in);
            in.close();
            reStr = p.getProperty(str);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.info("读取文件错误：" + e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return reStr;
    }

    /**
     * 将错误信息以字符串输出
     * @param e
     * @return
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

}
