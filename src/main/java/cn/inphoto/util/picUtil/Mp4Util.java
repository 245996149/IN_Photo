package cn.inphoto.user.util.picUtil;

import com.xuggle.xuggler.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static cn.inphoto.user.util.picUtil.GifUtil.merageBufferedImageArray;

/**
 * Created by root on 17-3-14.
 */
public class Mp4Util {

    // 帧与帧间秒数
    public static final double SECONDS_BETWEEN_FRAMES = 0.1;

    // 帧与帧间纳米秒数
    public static final long NANO_SECONDS_BETWEEN_FRAMES =
            (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

    // 最后一帧写入时间
    private static long mLastPtsWrite = Global.NO_PTS;

    private static BufferedImage[] images = null;

    public synchronized static BufferedImage[] mp4ToBufferedImage(String filename) {

        // 确定我们的硬件可以转换这个视频
        if (!IVideoResampler.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
            throw new RuntimeException("你必须安装GXuggler的带有IVideoResampler支持GPL版本来进行这个工作");

        // 创建一个xuggler容器对象
        IContainer container = IContainer.make();

        // 打开容器
        if (container.open(filename, IContainer.Type.READ, null) < 0)

            //if (container.open(in,format) <0)
            throw new IllegalArgumentException("无法打开文件: " + filename);

        //System.out.println("aaaaaaaaa");

        // 查询调用了多少流来进行这个任务
        int numStreams = container.getNumStreams();

        // 遍历流，找到第一视频流
        int videoStreamId = -1;
        IStreamCoder videoCoder = null;
        for (int i = 0; i < numStreams; i++) {
            // 查找流对象

            IStream stream = container.getStream(i);

            // 得到预配置的解码器可以解码这个流

            IStreamCoder coder = stream.getStreamCoder();

            System.out.println(stream.toString());

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                videoStreamId = i;
                videoCoder = coder;
                break;
            }

        }


        if (videoStreamId == -1)
            throw new RuntimeException("无法在容器中找到视频流: " + filename);

        // 现在我们在这个文件中找到了视频流，让我们打开解码器开始工作

        if (videoCoder.open() < 0)
            throw new RuntimeException("无法在容器中找到视频流: " + filename);

        IVideoResampler resampler = null;
        if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
            // 如果这个流不在BGR24内, VideoResampler帮助我们将它转换

            resampler =
                    IVideoResampler.make(videoCoder.getWidth(),
                            videoCoder.getHeight(),
                            IPixelFormat.Type.BGR24,
                            videoCoder.getWidth(),
                            videoCoder.getHeight(),
                            videoCoder.getPixelType());
            if (resampler == null)
                throw new RuntimeException("无法为" + filename + "创建颜色空间重采样 ");
        }

        // 现在，我们开始通过容器遍历每个数据包

        IPacket packet = IPacket.make();
        while (container.readNextPacket(packet) >= 0) {

            // 现在我们有一个包，让我们看看它是否是我们要的视频流

            if (packet.getStreamIndex() == videoStreamId) {
                // 我们分配一个Xuggle拿出来的新的图片数据

                IVideoPicture picture =
                        IVideoPicture.make(videoCoder.getPixelType(), videoCoder.getWidth(), videoCoder.getHeight());

                int offset = 0;
                while (offset < packet.getSize()) {
                    // 现在，我们将这个视频解码以检查错误

                    int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
                    if (bytesDecoded < 0)
                        throw new RuntimeException("got error decoding video in: " + filename);
                    offset += bytesDecoded;

                    // 一些解码器会消耗数据包中的数据，但不能创建一个完整的视频图像。你需要经常检查以保证获得一个完全解码的图像

                    if (picture.isComplete()) {
                        IVideoPicture newPic = picture;

                        // 如果重采样不是空，这意味着我们没有得到bgr24格式的视频，需要转换成bgr24格式。

                        if (resampler != null) {
                            // 我们必须重采样
                            newPic =
                                    IVideoPicture.make(resampler.getOutputPixelFormat(),
                                            picture.getWidth(),
                                            picture.getHeight());
                            if (resampler.resample(newPic, picture) < 0)
                                throw new RuntimeException("文件名为: " + filename + "的视频不能重采样");
                        }

                        if (newPic.getPixelType() != IPixelFormat.Type.BGR24)
                            throw new RuntimeException("文件名为: " + filename + "的视频无法解码为BGR 24位的数据");

                        // 将BGR 24位的数据转换为BufferedImage

                        BufferedImage javaImage = Utils.videoPictureToImage(newPic);

//                        if (images == null) {
//                            images = new BufferedImage[]{javaImage};
//                        } else {
//                            images = Arrays.copyOf(images, images.length + 1);
//                            images = merageArray(images, new BufferedImage[]{javaImage});
//                        }

                        // 处理视频帧

                        processFrame(newPic, javaImage);
                    }
                }
            } else {
                // 这个包不是我们视频流的一部分，所以我们放弃它
                do {
                } while (false);
            }

        }

        // 关闭并清理流

        if (videoCoder != null) {
            videoCoder.close();
            videoCoder = null;
        }
        if (container != null) {
            container.close();
            container = null;
        }
        return images;
    }

    private static void processFrame(IVideoPicture picture, BufferedImage image) {
        try {


            // 如果未初始化，将第一个帧作为最后一帧

            if (mLastPtsWrite == Global.NO_PTS)
                mLastPtsWrite = picture.getPts() - NANO_SECONDS_BETWEEN_FRAMES;

            // 如果帧时间大于最后一帧加上间隔时间，则开始写出到临时文件中

            if (picture.getPts() - mLastPtsWrite >= NANO_SECONDS_BETWEEN_FRAMES) {
                // 生成一个临时文件名
                System.out.println("cptr:" + picture.getMyCPtr());
                System.out.println(picture.isKeyFrame());
                System.out.println("pts:" + picture.getFormattedTimeStamp());
                //File file = new File("F:/1/" + i + ".jpg");
                //i++;
                // 写出到PNG

                //ImageIO.write(image, "jpg", file);

                // 显示文件信息

                //double seconds = ((double) picture.getPts()) / Global.DEFAULT_PTS_PER_SECOND;
                //System.out.printf("at elapsed time of %6.3f seconds wrote: %s\n", seconds, file);

                if (images == null) {
                    images = new BufferedImage[]{image};
                } else {
                    images = Arrays.copyOf(images, images.length + 1);
                    images = merageBufferedImageArray(images, new BufferedImage[]{image});
                }

                // 更新最后一帧的时间

                mLastPtsWrite += NANO_SECONDS_BETWEEN_FRAMES;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
