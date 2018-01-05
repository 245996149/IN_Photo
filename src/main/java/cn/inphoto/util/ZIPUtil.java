package cn.inphoto.util;

import cn.inphoto.dbentity.util.PythonInfo;
import com.aliyun.oss.OSSClient;
import com.google.gson.Gson;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                byte[] b3 = new byte[4096];

                // 将文件输入流读取到zip输出流中
                while ((b2 = inputStream.read(b3)) != -1) {
                    zos.write(b3, 0, b2);
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
            context.setAttribute(code, false);
            pythonZip(files);
            client.putObject(bucketName, code, new File(zipPath));
            context.setAttribute(code, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }

    public synchronized void pythonZip(File[] files) throws IOException, InterruptedException {
        Gson gson = new Gson();
        List<String> fileList = new ArrayList<>();
        for (File f : files
                ) {
            if (!f.exists() || f.isDirectory()) {
                continue;
            }
            fileList.add(f.getAbsolutePath());
        }
        PythonInfo py = new PythonInfo(zipPath, fileList);
        String str = gson.toJson(py);
        String pyPath = this.getClass().getResource("/").getPath() + "python/Zip.py";
        String[] args1 = new String[]{"python", pyPath, str};
        Process pr = Runtime.getRuntime().exec(args1);
        BufferedReader input = new BufferedReader(new InputStreamReader(
                pr.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
            context.setAttribute(code + "speed", line);
        }
        input.close();
        pr.waitFor();
    }
}
