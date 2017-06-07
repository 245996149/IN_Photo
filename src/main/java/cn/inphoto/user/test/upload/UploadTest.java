package cn.inphoto.user.test.upload;



import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by root on 17-3-9.
 */
public class UploadTest {

    @Test
    public void uploadTest() throws Exception {


        // 设定服务地址
        String serverUrl = "http://app.in-photo.cn" +
                "/gif_test/receive/receiveMedia.do?name=" + 99999;

        // 设定要上传的普通Form Field及其对应的value

        // 类FormFieldKeyValuePair的定义见后面的代码

        ArrayList<FormFieldKeyValuePair> formFieldKeyValuePairs = new ArrayList<>();

        formFieldKeyValuePairs.add(new FormFieldKeyValuePair("username", "Ming.C"));

        formFieldKeyValuePairs.add(new FormFieldKeyValuePair("password", "HELLO.MING"));

        formFieldKeyValuePairs.add(new FormFieldKeyValuePair("hobby", "Computer programming"));

        // 设定要上传的文件。UploadFileItem见后面的代码

        ArrayList<UploadFileItem> ufi = new ArrayList<>();

//        for (int i = 0; i < 8; i++) {
//            ufi.add(new UploadFileItem("upload" + (i + 1), "/root/视频/" + (i + 1) + ".png"));
//        }
        ufi.add(new UploadFileItem("upload1", "C:\\Users\\kaxia\\Documents\\WeChat Files\\chen245996149\\Files\\weixinface1496739335.gif"));
//        ufi.add(new UploadFileItem("upload2", "/root/2.png"));
//        ufi.add(new UploadFileItem("upload3", "/root/3.png"));
//        ufi.add(new UploadFileItem("upload4", "/root/4.png"));
//        ufi.add(new UploadFileItem("upload5", "/root/5.png"));
//        ufi.add(new UploadFileItem("upload6", "/root/6.png"));
//        ufi.add(new UploadFileItem("upload7", "/root/7.png"));
//        ufi.add(new UploadFileItem("upload8", "/root/8.png"));


        // 类HttpPostEmulator的定义，见后面的代码

        HttpPostEmulator hpe = new HttpPostEmulator();

        String response = hpe.sendHttpPostRequest(serverUrl, formFieldKeyValuePairs, ufi);

        System.out.println("Response from server is: " + response);

    }

}

