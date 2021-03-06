package cn.inphoto.test.upload;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by root on 17-3-9.
 */
public class UploadTest {

    @Test
    public void uploadTest() throws Exception {

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            sb.append(n);
        }
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int n = random.nextInt(10);
            sb1.append(n);
        }

        sb1.append(sb);

        // 设定服务地址
        String serverUrl = "http://app.in-photo.cn" +
                "/IN_Photo/receive/receiveMedia.do?names=" + sb1 + "&media_code=" + sb + "&category_id=7&user_id=" + 2 + "&second=" + 150 + "&number=" + 8;

        // 设定要上传的普通Form Field及其对应的value

        // 类FormFieldKeyValuePair的定义见后面的代码

        ArrayList<FormFieldKeyValuePair> formFieldKeyValuePairs = new ArrayList<>();

        formFieldKeyValuePairs.add(new FormFieldKeyValuePair("username", "Ming.C"));

        formFieldKeyValuePairs.add(new FormFieldKeyValuePair("password", "HELLO.MING"));

        formFieldKeyValuePairs.add(new FormFieldKeyValuePair("hobby", "Computer programming"));

        // 设定要上传的文件。UploadFileItem见后面的代码

        ArrayList<UploadFileItem> ufi = new ArrayList<>();

//        for (int i = 0; i < 8; i++) {
//            ufi.add(new UploadFileItem("upload" + (i + 1), "g:\\" + (i + 1) + ".png"));
//        }
        ufi.add(new UploadFileItem("upload1", "/home/ming/123.mp4"));
        ufi.add(new UploadFileItem("upload2", "/home/ming/123.jpg"));
//        ufi.add(new UploadFileItem("upload3", "/root/3.png"));
//        ufi.add(new UploadFileItem("upload4", "/root/4.png"));
//        ufi.add(new UploadFileItem("upload5", "/root/5.png"));
//        ufi.add(new UploadFileItem("upload6", "/root/6.png"));
//        ufi.add(new UploadFileItem("upload7", "/root/7.png"));
//        ufi.add(new UploadFileItem("upload8", "/root/8.png"));


        // 类HttpPostEmulator的定义，见后面的代码

        HttpPostEmulator hpe = new HttpPostEmulator();

        Long begin = System.currentTimeMillis();

        String response = hpe.sendHttpPostRequest(serverUrl, formFieldKeyValuePairs, ufi);

        Long end = System.currentTimeMillis();
        System.out.println("Response from server is: " + response);
        System.out.println("用时:" + (end - begin));
    }

}

