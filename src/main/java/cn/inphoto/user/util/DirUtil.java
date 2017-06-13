package cn.inphoto.user.util;

import org.apache.log4j.Logger;

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
    public static String getConfigInfo(String str) throws IOException {

        // 创建配置文件对象
        Properties p = new Properties();
        // 创建返回字符串对象
        String reStr = null;
        // 创建文件输入流对象
        InputStream in = null;

        try {
            // 将文件通过文件输入流打开
            in = DirUtil.class.getResourceAsStream("/IN_Photo_config.properties");
            // 配置文件对象读取文件输入流
            p.load(in);
            // 关闭文件输入流
            in.close();
            // 配置文件对象读取参数并将其赋予返回字符串对象
            reStr = p.getProperty(str);
        } catch (Exception e) {
            logger.info("读取文件错误：" + getErrorInfoFromException(e));
        } finally {
            if (in != null) {
                in.close();
            }
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

}
