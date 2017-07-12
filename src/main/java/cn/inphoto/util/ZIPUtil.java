package cn.inphoto.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by root on 17-4-1.
 */
public class ZIPUtil {

    public static byte[] createZIP(File[] files) throws IOException {

        // 创建返回的字节数组
        byte[] b = null;

        // 创建输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 创建zip输出流，并抛出异常
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {

            // 遍历文件
            for (File f : files) {

                // 文件输入流
                FileInputStream inputStream = new FileInputStream(f);

                // 创建zip对象
                ZipEntry entry = new ZipEntry(f.getName());

                // 定位zip输出流
                zos.putNextEntry(entry);

                int b2;

                // 将文件输入流读取到zip输出流中
                while ((b2 = inputStream.read()) != -1) {
                    zos.write(b2);
                }

                // 关闭文件输入流
                inputStream.close();

            }

            // 关闭流
            zos.close();
            b = bos.toByteArray();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}
