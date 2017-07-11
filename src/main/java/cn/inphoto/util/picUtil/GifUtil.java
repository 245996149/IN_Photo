package cn.inphoto.user.util.picUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by kaxia on 2017/6/12.
 */
public class GifUtil {

    /**
     * 将bufferedImage转化成GIF，背景透明
     *
     * @param pic 输入的bufferedImage数组
     * @param newPic 输出的文件路径
     * @param second gif间隔
     * @param c 透明背景的颜色
     * @return 是否合成成功
     */
    public static boolean bufferedImageToGif(BufferedImage[] pic, String newPic, int second, Color c) {
        try {
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            // e.setRepeat(0);
            e.start(newPic);
            // BufferedImage src[] = new BufferedImage[pic.length];
            for (int i = 0; i < pic.length; i++) {
                //e.setSize(192*2,108*2);
                e.setQuality(20);
                // 设置播放的延迟时间
                e.setDelay(second);
                // 设置是否循环
                e.setRepeat(0);
                // 读入需要播放的jpg文件
                // src[i] = ImageIO.read(new File(pic[i]));
                // 添加到帧中
                e.setTransparent(c);
                e.addFrame(pic[i]);
            }
            e.finish();
            return true;

        } catch (Exception e) {
            // logger.info("jpg转换为Gif错误：" + e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将bufferedImage转化成GIF
     *
     * @param pic 输入的bufferedImage数组
     * @param newPic 输出的文件路径
     * @param second gif间隔
     * @return 是否合成成功
     */
    public static boolean bufferedImageToGif(BufferedImage[] pic, String newPic, int second) {
        return bufferedImageToGif(pic, newPic, second, null);
    }

    /**
     * 将两个bufferefImage数组合成为一个
     *
     * @param bufferedImages1
     * @param bufferedImages2
     * @return 合成后的bufferefImage数组
     */
    public static BufferedImage[] merageBufferedImageArray(BufferedImage[] bufferedImages1, BufferedImage[] bufferedImages2) {
        int b1 = bufferedImages1.length;
        int b2 = bufferedImages2.length;
        bufferedImages1 = Arrays.copyOf(bufferedImages1, b1 + b2);
        System.arraycopy(bufferedImages2, 0, bufferedImages1, b1, b2);
        return bufferedImages1;
    }

}
