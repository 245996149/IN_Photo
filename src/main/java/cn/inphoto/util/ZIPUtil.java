package cn.inphoto.util;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static cn.inphoto.util.DirUtil.createDirectory;
import static cn.inphoto.util.DirUtil.deleteDir;

/**
 * Created by root on 17-4-1.
 */
public class ZIPUtil extends Thread {

    private File[] files;
    private String zipPath;
    private String code;
    private ServletContext context;
    private OSSClient client;
    private String bucketName;


    public ZIPUtil(File[] files, String zipPath, String code, ServletContext context, OSSClient client, String bucketName) {
        this.files = files;
        this.zipPath = zipPath;
        this.code = code;
        this.context = context;
        this.client = client;
        this.bucketName = bucketName;

    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public OSSClient getClient() {
        return client;
    }

    public void setClient(OSSClient client) {
        this.client = client;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public byte[] createZIP(File[] files) throws IOException {

        // 创建返回的字节数组
        byte[] b = null;

        // 创建输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int a = 0;

        // 创建zip输出流，并抛出异常
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {

            // 遍历文件
            for (File f : files) {

                context.setAttribute(code + "speed", (a++) + "/" + files.length);

                if (!f.exists() || f.isDirectory()) {
                    continue;
                }

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

                zos.closeEntry();

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

    @Override
    public void run() {
        try {
            System.out.println("开始创建");
            context.setAttribute(code, false);
            createDirectory(zipPath);
            byte[] b = createZIP(files);
            client.putObject(bucketName, code, new ByteArrayInputStream(b));
            context.setAttribute(code, true);
            System.out.println("创建完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.shutdown();
            }
            deleteDir(new File(zipPath));
        }
    }
}
