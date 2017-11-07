package cn.inphoto.controller;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dbentity.user.MediaData;
import cn.inphoto.dbentity.user.User;
import cn.inphoto.util.ZIPUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

import static cn.inphoto.util.DirUtil.createDirectory;
import static cn.inphoto.util.DirUtil.deleteDir;
import static cn.inphoto.util.ResultMapUtil.createResult;
import static cn.inphoto.util.ZIPUtil.createZIP;
import static cn.inphoto.util.picUtil.QRUtil.writeToStream;

/**
 * 文件获取控制器
 * Created by kaxia on 2017/6/19.
 */
@Controller
@RequestMapping("/get")
public class DownloadController {

    private Logger logger = Logger.getLogger(DownloadController.class);

    @Value("#{properties['data_path']}")
    public String data_path;

    @Value("#{properties['AliyunOSSEndpoint']}")
    public String endpoint;

    @Value("#{properties['AliyunAccessKeyId']}")
    public String accessKeyId;

    @Value("#{properties['AliyunAccessKeySecret']}")
    public String accessKeySecret;

    @Value("#{properties['AliyunOSSBucketName']}")
    public String bucketName;

    @Value("#{properties['tmp_path']}")
    public String tmpPath;

    @Resource
    private MediaDataDao mediaDataDao;

    /**
     * 根据媒体数据ID队列，打包下载媒体数据
     *
     * @param session       Session对象
     * @param media_id_list 媒体数据ID队列
     * @throws IOException 抛出IO错误
     */
    @RequestMapping("/getMedias.do")
    @ResponseBody
    public Map getMedias(HttpSession session,
                         String media_id_list) throws IOException {

        // 获取session中的user
        User user = (User) session.getAttribute("loginUser");

        // 将接收到的数组转换为JSONArray
        JSONArray jsonArray = new JSONArray(media_id_list);

        // 创建用于查询的media_ids队列
        List<Long> media_ids = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            media_ids.add(jsonArray.getLong(i));
        }

        // 加载数据库中的对象
        List<MediaData> mediaDataList = mediaDataDao.findByMedia_ids(media_ids);

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        String tmp_file = tmpPath + File.separator + user.getUserId();
        createDirectory(tmp_file);

        // 创建文件数组
        File[] files = new File[mediaDataList.size()];

        // 将媒体对象中的文件路径赋给文件数组
        for (int i = 0; i < mediaDataList.size(); i++) {
            String tmp[] = mediaDataList.get(i).getMediaKey().split("/");
            client.getObject(
                    new GetObjectRequest(bucketName, mediaDataList.get(i).getMediaKey()),
                    new File(tmp_file + File.separator + tmp[tmp.length - 1]));
            files[i] = new File(tmp_file + File.separator + tmp[tmp.length - 1]);
        }

        // 创建zip文件字节数组
        byte[] b = createZIP(files);

        String mediaKey = "tmp/" + System.currentTimeMillis() + ".zip";

        client.putObject(bucketName, mediaKey, new ByteArrayInputStream(b));

        client.shutdown();

        deleteDir(new File(tmp_file));

        return createResult(true, "http://file.in-photo.cn/" + mediaKey);

    }

    @RequestMapping("/createMediasForDateZip.do")
    @ResponseBody
    public Map createMediasForDateZip(Long user_id, Integer category_id, HttpServletRequest request,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_date,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date) {

        List<MediaData> mediaDataList =
                mediaDataDao.findByUser_idAndCategory_idAndBeginDateAndEndDate(
                        user_id, category_id, begin_date, end_date);

        if (mediaDataList.size() == 0) {
            return createResult(false, "该日期内没有媒体数据");
        }

        List<MediaData> downloadData = new ArrayList<>();

        for (MediaData m : mediaDataList
                ) {
            if (m.getMediaState() == MediaData.MediaState.Normal || m.getMediaState() == MediaData.MediaState.WillDelete) {
                downloadData.add(m);
            }
        }

        if (downloadData.size() == 0
                ) {
            return createResult(false, "该日期内没有有效的媒体数据");
        }

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        String tmp_file = tmpPath + File.separator + user_id;
        createDirectory(tmp_file);

        // 创建文件数组
        File[] files = new File[downloadData.size()];

        // 将媒体对象中的文件路径赋给文件数组
        for (int i = 0; i < downloadData.size(); i++) {
            String tmp[] = downloadData.get(i).getMediaKey().split("/");
            client.getObject(
                    new GetObjectRequest(bucketName, downloadData.get(i).getMediaKey()),
                    new File(tmp_file + File.separator + tmp[tmp.length - 1]));
            files[i] = new File(tmp_file + File.separator + tmp[tmp.length - 1]);
        }

        String code = "tmp/" + System.currentTimeMillis() + ".zip";

        ServletContext context = request.getSession().getServletContext();

        ZIPUtil zipUtil = new ZIPUtil(
                files, tmp_file, code, context, client, bucketName);

        zipUtil.start();

        return createResult(true, code);

    }

    @RequestMapping("/checkCreateZipStates.do")
    @ResponseBody
    public Map checkCreateZipStates(String code, HttpServletRequest request) {
        System.out.println(11111111);
        ServletContext context = request.getSession().getServletContext();
        Boolean b = (Boolean) context.getAttribute("zip" + code);
        if (b == null) {
            return createResult(false, "为找到对应的压缩文件");
        } else if (!b) {
            return createResult(false, "正在压缩中！请勿刷新页面");
        }
        return createResult(true, "http://file.in-photo.cn/" + code);
    }

    @RequestMapping("/getZipFile.do")
    @ResponseBody
    public void getZipFile(String code, HttpServletResponse response) {

        FileInputStream fis = null;

        OutputStream outputStream = null;

        String file_path = data_path + File.separator + "tmp" + File.separator + code + ".zip";

        response.setContentType("application/zip");
        //设置内容作为附件下载  fileName有后缀,比如1.jpg
        response.setHeader("Content-Disposition", "attachment; filename=" + file_path);

        try {

            // 将图片文件写入输入流
            fis = new FileInputStream(file_path);

            response.setContentLength(fis.available());

            // 创建一个输入流大小的byte数组
            byte[] content = new byte[fis.available()];

            // 将输入流写入到数组中
            fis.read(content);

            // 创建输出流
            outputStream = response.getOutputStream();

            // 将数组写出到输出流中
            outputStream.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据传入的url判断是否解码，并生成该url对应的二维码
     *
     * @param response Response对象
     * @param url      输入的URL
     * @param encode   是否是编码的
     * @throws IOException 抛出IO错误
     */
    @RequestMapping("/getQR.do")
    @ResponseBody
    public void getQR(HttpServletResponse response, String url, boolean encode) throws IOException {

        // 判断url是否是编码的，编码的需要解码
        if (encode) {
            url = URLDecoder.decode(url, "UTF-8");
        }

        //System.out.println(url);

        try (OutputStream outputStream = response.getOutputStream()) {

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            //内容所使用编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);

            //生成二维码
            writeToStream(bitMatrix, "jpg", outputStream);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}