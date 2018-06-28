package cn.bidostar.ticserver.utils;

/**
 * Created by wangg on 2017/7/14.
 */

import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


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



}
