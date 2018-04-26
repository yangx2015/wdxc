package cn.bidostar.ticserver.utils;

/**
 * Created by wangg on 2017/7/14.
 */

import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoSize;

/**
 * 对文件处理的工具类
 */
public class FileUtils {
    public static void writeFile(String filePath,String text){
        File file = new File(Environment.getExternalStorageDirectory(),filePath);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(text.getBytes());
                fos.close();
            } catch (Exception e) {
                I.e("FileUtils writeFile error",e);
            }
        } else {
            // 此时SDcard不存在或者不能进行读写操作的
            I.e("FileUtils sd writeFile error","此时SDcard不存在或者不能进行读写操作");
        }
    }

    public static String readFile(String fileName){
        String dx = "";
        File file = new File(Environment.getExternalStorageDirectory(),fileName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                dx = new String(b);

            } catch (Exception e) {
                I.e("FileUtils  readFile error",e);
            }
        } else {
            // 此时SDcard不存在或者不能进行读写操作的
            I.e("FileUtils readFile sd error","此时SDcard不存在或者不能进行读写操作");
        }
        return dx;
    }

    public static void TsConvertMp4(String filePath){


        File source = new File(filePath);
        File target = new File(filePath+".mp4");
        System.out.println(source.length());

        AudioAttributes audio = new AudioAttributes();

        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(64000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(22050));
        VideoAttributes video = new VideoAttributes();
        video.setCodec("libxvid");// 转MP4
        video.setBitRate(new Integer(180000));// 180kb/s比特率
        video.setFrameRate(new Integer(1));// 1f/s帧频，1是目前测试比较清楚的，越大越模糊
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("avi");// 转MP4
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        long beginTime = System.currentTimeMillis();
        try {
            // 获取时长
            MultimediaInfo m = encoder.getInfo(source);
            System.out.println(m.getDuration());
            System.out.println("获取时长花费时间是：" + (System.currentTimeMillis() - beginTime));
            beginTime = System.currentTimeMillis();
            encoder.encode(source, target, attrs);
            System.out.println("视频转码花费时间是：" + (System.currentTimeMillis() - beginTime));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }

}
