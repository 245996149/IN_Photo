package cn.inphoto.controller.user;

import cn.inphoto.dao.MediaDataDao;
import cn.inphoto.dao.WebinfoDao;
import cn.inphoto.dbentity.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.inphoto.util.DirUtil.getErrorInfoFromException;
import static cn.inphoto.util.ZIPUtil.createZIP;
import static cn.inphoto.util.picUtil.QRUtil.writeToStream;

/**
 * Created by kaxia on 2017/6/19.
 */
@Controller
@RequestMapping("/user/get")
public class DownloadController {

    private Logger logger = Logger.getLogger(DownloadController.class);

    @Resource
    WebinfoDao webinfoDao;

    @Resource
    MediaDataDao mediaDataDao;

    /**
     * 输出媒体、套餐系统图片数据
     *
     * @param response
     * @param request
     * @param id        媒体、套餐系统id
     * @param type      具体类型
     * @param thumbnail 是否压缩
     * @throws IOException
     */
    @RequestMapping("getMedia.do")
    public void getMedia(HttpServletResponse response, HttpServletRequest request, Long id, int type, boolean thumbnail, boolean download) throws IOException {

        String file_path = null;

        FileInputStream fis = null;

        OutputStream outputStream = null;

        try {

            switch (type) {
                case 1://type=1 media_data

                    MediaDataEntity mediaDataEntity = mediaDataDao.findByMedia_id(id);
                    if (mediaDataEntity != null) {
                        file_path = mediaDataEntity.getFilePath();
                    }
                    break;

                case 2://type=2 pic_bg

                    PicWebinfoEntity picWebinfoEntity = webinfoDao.findPicByPic_id(id);
                    if (picWebinfoEntity != null) {
                        file_path = picWebinfoEntity.getBackground();
                    }
                    break;

                case 3://type=3 code_bg

                    CodeWebinfoEntity codeWebinfo1 = webinfoDao.findCodeByCode_id(id);
                    if (codeWebinfo1 != null) {
                        file_path = codeWebinfo1.getBackground();
                    }
                    break;
                case 4://type=4 code_con

                    CodeWebinfoEntity codeWebinfo2 = webinfoDao.findCodeByCode_id(id);
                    if (codeWebinfo2 != null) {
                        file_path = codeWebinfo2.getButtonPic();
                    }
                    break;
                case 5://type=5 share_moments_icon

                    ShareInfoEntity shareInfo1 = webinfoDao.findShareByShare_id(id);
                    if (shareInfo1 != null) {
                        file_path = shareInfo1.getShareMomentsIcon();
                    }
                    break;
                case 6://type=6 share_chats_icon

                    ShareInfoEntity shareInfo2 = webinfoDao.findShareByShare_id(id);
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

            logger.info("接收到读取图片文件请求，请求id为：" + id + "，type为：" + type + "，thumbnail为：" + thumbnail + "的图片文件路径为：" + file_path);

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
            logger.info("id为：" + id + "的media读取错误，错误信息为：" + getErrorInfoFromException(e));

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
     * @param response
     * @param session
     * @param media_id_list 媒体数据ID队列
     * @throws IOException
     */
    @RequestMapping("/getMedias.do")
    @ResponseBody
    public void getMedias(HttpServletResponse response, HttpSession session,
                          String media_id_list) throws IOException {
        // 获取session中的user
        UsersEntity user = (UsersEntity) session.getAttribute("loginUser");

        // 将接收到的数组转换为JSONArray
        JSONArray jsonArray = new JSONArray(media_id_list);

        // 创建用于查询的media_ids队列
        List<Long> media_ids = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            media_ids.add(jsonArray.getLong(i));
        }

        // 加载数据库中的对象
        List<MediaDataEntity> mediaDataList = mediaDataDao.findByMedia_ids(media_ids);

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

            // 将zip文件字节数组写出到response
            outputStream.write(b);
            // 关闭输出流
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getQR.do")
    @ResponseBody
    public void getQR(HttpServletResponse response, String url,boolean encode) throws IOException {

        if (encode) {
            url = URLDecoder.decode(url, "UTF-8");
        }

        System.out.println(url);

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
