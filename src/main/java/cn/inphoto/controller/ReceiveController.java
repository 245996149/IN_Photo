package cn.inphoto.controller;

import cn.inphoto.dao.*;
import cn.inphoto.dbentity.user.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static cn.inphoto.util.DBUtil.judgeMediaCode;
import static cn.inphoto.util.DirUtil.*;
import static cn.inphoto.util.picUtil.GifUtil.bufferedImageToGif;
import static cn.inphoto.util.picUtil.GifUtil.merageBufferedImageArray;

/**
 * 文件上传控制器
 * Created by kaxia on 2017/6/6.
 */
@Controller
@RequestMapping("/receive")
public class ReceiveController {

    private Logger logger = Logger.getLogger(ReceiveController.class);

    @Value("#{properties['data_path']}")
    public String data_path;

    @Resource
    UtilDao utilDao;

    @Resource
    UserDao userDao;

    @Resource
    UserCategoryDao userCategoryDao;

    @Resource
    CategoryDao categoryDao;

    @Resource
    MediaDataDao mediaDataDao;

    @Resource
    MediaCodeDao mediaCodeDao;

    /**
     * @param request
     * @param response
     * @param names       文件的唯一编码
     * @param category_id 套餐的唯一码
     * @param media_code  文件的提取码
     * @param second      gif的间隔时间
     * @param number      gif的张数
     * @param user_id     用户的id号
     * @return 返回是否提交成功
     * @throws IOException
     */
    @RequestMapping("/receiveMedia.do")
    @ResponseBody
    public Map<String, Object> receiveMedia(HttpServletRequest request, HttpServletResponse response,
                                            String names, Integer category_id, String media_code, Integer second,
                                            Integer number, Long user_id) throws IOException {

        // 设置请求、返回的字符编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 创建返回的Map
        Map<String, Object> result = new HashMap<>();

        // 判断必填参数是否为空，为空直接反馈给客户端
        if (names == null || category_id == null || user_id == null || "".equals(names)) {

            result.put("success", false);
            result.put("code", 100);
            result.put("message", "userId、names、type三者为必填参数，不能为空");
            logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                    + category_id + "，返回的信息为：" + result.toString());
            return result;

        }

        // 根据user_id查询user
        User user = userDao.findByUser_id(user_id);

        if (user == null) {

            result.put("success", false);
            result.put("code", 101);
            result.put("message", "未找到user_id=" + user_id + "对应的用户");
            logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                    + category_id + "，返回的信息为：" + result.toString());
            return result;

        }

        Category category = categoryDao.findByCategory_id(category_id);

        // 设置日志信息
        MDC.put("user_id", user.getUserId());
        MDC.put("category_id", category_id);

        // 根据category_code、user_id查询用户有效期内的系统
        UserCategory userCategory =
                userCategoryDao.findByUser_idAndCategory_idAndState(
                        user.getUserId(), category_id,
                        UserCategory.USER_CATEGORY_STATE_NORMAL);

        if (userCategory == null) {

            result.put("success", false);
            result.put("code", 103);
            result.put("message", "user_id=" + user_id + "的用户没有category_id=" + category_id + "状态正常的系统");
            logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                    + category_id + "，返回的信息为：" + result.toString());
            return result;

        }

        // 获取配置文件中的存储数据的根目录
        //String path = getConfigInfo("data_path");
        String path = data_path;

        System.out.println(data_path);

        //设置InPhoto媒体数据用户存储的目录，判断路径是否存在，不存在则创建
        String userPath = path + File.separator + user.getUserId();
        createDirectory(userPath);

        try {

            //创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());

            //判断 request 是否有文件上传,即多部分请求
            if (!multipartResolver.isMultipart(request)) {

                // 判断userId是否有效，无效反馈给客户端
                result.put("success", false);
                result.put("code", 104);
                result.put("message", "请求中未包含文件");
                logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                        + category_id + "，返回的信息为：" + result.toString());
                return result;

            }

            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            // 创建文件路径
            String filePath = null;

            // 判断该套餐是否需要合成GIF
            if (category.getMadeGif() == 0) {

                // 不需要合成gif
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String fileName = file.getOriginalFilename();

                        System.out.println(fileName);
                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (!"".equals(fileName.trim())) {

                            // 设置文件的存储路径，判断路径是否存在，不存在则创建
                            String picPath = userPath + File.separator + category.getCategoryCode();
                            createDirectory(picPath);

                            // 获取图片尾缀
                            String tempFileName[] = fileName.split("\\.");

                            // 设置文件路径
                            filePath = picPath + File.separator + names + "." + tempFileName[1];
                            //定义上传路径
                            File localFile = new File(filePath);
                            file.transferTo(localFile);
                        }
                    }

                }

            } else {

                //需要合成gif

                //创建临时存储用户生成GIF的Map<String, BufferedImage>
                Map<String, BufferedImage> temMap = new HashMap<>();

                while (iter.hasNext()) {
                    //取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        //取得当前上传文件的文件名称
                        String fileName = file.getOriginalFilename();

//                        System.out.println(fileName);
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (!"".equals(fileName.trim())) {

//                            System.out.println(fileName);

                            InputStream in = file.getInputStream();

                            BufferedImage bufferedImage = ImageIO.read(in);

                            // 获取图片尾缀
                            String tempFileName[] = fileName.split("\\.");

                            String temFileName = fileName.replaceAll("." + tempFileName[1], "");

                            temMap.put(temFileName, bufferedImage);

                        }
                    }
                }

//                System.out.println(temMap.size());

                //判断Map中是否有BufferedImage，且BufferedImage数量等于上传参数的数量，
                if (temMap.size() == 0 || temMap.size() != number) {

                    result.put("success", false);
                    result.put("code", 105);
                    result.put("message", "文件接收未完整！");
                    logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                            + category_id + "，返回的信息为：" + result.toString());
                    return result;

                }

                BufferedImage[] images = new BufferedImage[]{temMap.get("1")};

                for (int i = 1; i < number; i++) {

                    images = Arrays.copyOf(images, images.length + 1);

                    images = merageBufferedImageArray(images, new BufferedImage[]{temMap.get(i + 1 + "")});

                }

                //设置gif文件的存储路径，判断路径是否存在，不存在则创建
                String gifDirPath = userPath + File.separator + category.getCategoryCode();
                createDirectory(gifDirPath);

                filePath = gifDirPath + File.separator + names + ".gif";

                boolean createGif;

                //判断合成gif是否需要透明背景
                if (category.getGifTransparency() == 0) {
                    // 将接收到BufferedImage数组转换成普通GIF，并输出一个成功与否的Boolean
                    createGif = bufferedImageToGif(images, filePath, second);
                } else {
                    // 将接收到BufferedImage数组转换成带透明通道的GIF，并输出一个成功与否的Boolean
                    createGif = bufferedImageToGif(images, filePath, second, new Color(0, 0, 0));
                }

                //  判断BufferedImage数组转换成GIF是否成功，
                if (!createGif) {

                    result.put("success", false);
                    result.put("code", 106);
                    result.put("message", "Gif文件转换失败");
                    logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id=" + category_id
                            + "，number=" + number + "，second=" + second + "返回的信息为：" + result.toString());
                    return result;

                }

            }

            // 创建MediaData用于将媒体信息写入数据库
            MediaData mediaData = new MediaData();
            mediaData.setUserId(user.getUserId());
            mediaData.setCategoryId(category.getCategoryId());
            mediaData.setFilePath(filePath);
            mediaData.setMediaName(names);
            mediaData.setMediaState(MediaData.MEDIA_STATE_NORMAL);

            // 将媒体信息写入数据库
            if (!utilDao.save(mediaData)) {

                result.put("success", false);
                result.put("code", 107);
                result.put("message", "文件写入数据库时发生了错误，请稍后再试");
                logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id=" + category_id +
                        "，返回的信息为：" + result.toString());
                return result;

            }

            System.out.println(mediaData.toString());

            // 创建验证码对象用于更新、新增验证码表
            MediaCode mediaCode = new MediaCode();
            mediaCode.setCategoryId(category.getCategoryId());
            mediaCode.setMediaCode(media_code);
            mediaCode.setUserId(user.getUserId());
            mediaCode.setMediaId(mediaData.getMediaId());

            System.out.println(mediaCode.toString());

            // 更新、新增验证码表，并返回是否成功
            if (!judgeMediaCode(mediaCodeDao, utilDao, mediaCode)) {

                result.put("success", false);
                result.put("code", 108);
                result.put("message", "验证码写入数据库中发生错误");
                logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                        + category_id + "，返回的信息为：" + result.toString());
                return result;

            }

            // 判断数据总量是否超过用户购买量，超过则将时间最早的数据移到回收站中
            if (mediaDataDao.countByUser_idAndCategory_idAndMedia_state(
                    user.getUserId(), category.getCategoryId(), MediaData.MEDIA_STATE_NORMAL) > userCategory.getMediaNumber()) {

                // 数据总量超过用户购买量，获取该用户改套餐系统正常状态下创建时间最早的一条媒体数据
                MediaData mediaDataEntity = mediaDataDao.findByUser_idAndCategory_idAndMedia_stateOrderByCreate_timeLimitOne(
                        user.getUserId(), category.getCategoryId(), MediaData.MEDIA_STATE_NORMAL);

                // 将该数据移到回收站
                mediaDataEntity.setMediaState(MediaData.MEDIA_STATE_RECYCLE);

                // 更新该数据库中该数据信息
                utilDao.update(mediaDataEntity);
            }

            result.put("success", true);
            result.put("code", 200);
            result.put("message", "数据写入系统成功");
            logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                    + category_id + "，返回的信息为：" + result.toString());
            return result;

        } catch (Exception e) {

            result.put("success", false);
            result.put("code", 99);
            result.put("message", "发生未知错误，错误信息为：" + getErrorInfoFromException(e));
            logger.info("接收到的names=" + names + "，user_id=" + user_id + ",category_id="
                    + category_id + "，返回的信息为：" + result.toString());
            return result;

        }
    }
}


