package cn.inphoto.controller;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.user.*;
import cn.inphoto.util.ZIPUtil;
import cn.inphoto.weChatUtil.HttpRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import net.coobird.thumbnailator.Thumbnails;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.*;

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

    @Resource
    private WebinfoDao webinfoDao;

    @Resource
    private MediaDataDao mediaDataDao;

    /**
     * 输出媒体、套餐系统图片数据
     *
     * @param response  Response对象
     * @param request   Request对象
     * @param id        媒体、套餐系统id
     * @param type      具体类型
     *                  type=1 media_data 媒体数据
     *                  type=2 pic_bg 展示页面背景
     *                  type=3 code_bg 提取液面背景
     *                  type=4 code_con 提取液面确认按钮
     *                  type=5 share_moments_icon 朋友圈小图标
     *                  type=6 share_chats_icon 分享给好友小图标
     *                  type=7 测试用数据
     * @param thumbnail 是否压缩
     * @throws IOException 抛出IO错误
     */
    @RequestMapping("/getMedia.do")
    public void getMedia(HttpServletResponse response, HttpServletRequest request,
                         Long id, int type, boolean thumbnail, boolean download) throws IOException {

        String file_path = null;

        FileInputStream fis = null;

        OutputStream outputStream = null;

        try {

            switch (type) {
                case 1://type=1 media_data

                    MediaData mediaData = mediaDataDao.findByMedia_id(id);
                    if (mediaData != null) {
                        file_path = mediaData.getFilePath();
                    }
                    break;

                case 2://type=2 pic_bg

                    PicWebInfo picWebInfo = webinfoDao.findPicByPic_id(id);
                    if (picWebInfo != null) {
                        file_path = picWebInfo.getBackground();
                    }
                    break;

                case 3://type=3 code_bg

                    CodeWebInfo codeWebinfo1 = webinfoDao.findCodeByCode_id(id);
                    if (codeWebinfo1 != null) {
                        file_path = codeWebinfo1.getBackground();
                    }
                    break;
                case 4://type=4 code_con

                    CodeWebInfo codeWebinfo2 = webinfoDao.findCodeByCode_id(id);
                    if (codeWebinfo2 != null) {
                        file_path = codeWebinfo2.getButtonPic();
                    }
                    break;
                case 5://type=5 share_moments_icon

                    ShareInfo shareInfo1 = webinfoDao.findShareByShare_id(id);
                    if (shareInfo1 != null) {
                        file_path = shareInfo1.getShareMomentsIcon();
                    }
                    break;
                case 6://type=6 share_chats_icon

                    ShareInfo shareInfo2 = webinfoDao.findShareByShare_id(id);
                    if (shareInfo2 != null) {
                        file_path = shareInfo2.getShareChatsIcon();
                    }
                    break;

                case 7://type=7 测试用数据
                    file_path = request.getSession().getServletContext().getRealPath("/images/test.jpg");
                    break;
                default:
                    break;
            }

            if (file_path == null || "".equals(file_path)) {
                file_path = request.getSession().getServletContext().getRealPath("/images/error.png");
            }

//            logger.info("接收到读取图片文件请求，请求id为：" + id + "，type为：" + type + "，thumbnail为：" + thumbnail + "的图片文件路径为：" + file_path);

            // 判断是否压缩
            if (thumbnail) {

                // 压缩成100px
                Thumbnails.of(new File(file_path)).size(100, 100).toOutputStream(response.getOutputStream());

            } else {

                if (download) {
                    response.setContentType("application/zip");
                    //设置内容作为附件下载  fileName有后缀,比如1.jpg
                    response.setHeader("Content-Disposition", "attachment; filename=" + file_path);
                }

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
            }

        } catch (Exception e) {

            e.printStackTrace();
//            logger.info("id为：" + id + "的media读取错误，错误信息为：" + getErrorInfoFromException(e));

        } finally {
            if (fis != null) {
                fis.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * 根据媒体数据ID队列，打包下载媒体数据
     *
     * @param response      Request对象
     * @param session       Session对象
     * @param media_id_list 媒体数据ID队列
     * @throws IOException 抛出IO错误
     */
    @RequestMapping("/getMedias.do")
    @ResponseBody
    public void getMedias(HttpServletResponse response, HttpSession session,
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

        // 创建文件数组
        File[] files = new File[mediaDataList.size()];

        // 将媒体对象中的文件路径赋给文件数组
        for (int i = 0; i < mediaDataList.size(); i++) {
            files[i] = new File(mediaDataList.get(i).getFilePath());
        }

        // 将html头文件写为下载
        response.setContentType("application/zip");
        //设置内容作为附件下载  fileName有后缀,比如1.jpg
        response.setHeader("Content-Disposition", "attachment; filename=" + user.getUserName() + ".zip");


        try (OutputStream outputStream = response.getOutputStream()) {

            // 创建zip文件字节数组
            byte[] b = createZIP(files);

            response.setContentLength(b.length);

            // 将zip文件字节数组写出到response
            outputStream.write(b);
            // 关闭输出流
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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


        // 创建文件数组
        File[] files = new File[downloadData.size()];

        // 将媒体对象中的文件路径赋给文件数组
        for (int i = 0; i < downloadData.size(); i++) {
            files[i] = new File(downloadData.get(i).getFilePath());
        }

        //设置InPhoto媒体数据用户存储的目录，判断路径是否存在，不存在则创建
        String tmpPath = data_path + File.separator + "tmp";

        // 创建6位验证码字符串对象
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        // 生成6位随机码
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(9));
        }

        ServletContext context = request.getSession().getServletContext();

        ZIPUtil zipUtil = new ZIPUtil(files, tmpPath, code.toString(), context);

        zipUtil.start();

        return createResult(true, code.toString());

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
        return createResult(true, request.getContextPath() + "/get/getZipFile.do?code=" + code);
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