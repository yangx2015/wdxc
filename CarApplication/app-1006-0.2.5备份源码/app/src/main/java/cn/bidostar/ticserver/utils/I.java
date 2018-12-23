package cn.bidostar.ticserver.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类
 * Created by wangg
 */

public class I {
    public static final String CACHE_DIR_NAME = "WeicyCARFDLog";

    public static boolean isDebugModel = false;// 是否输出日志
    public static boolean isSaveDebugInfo = false;// 是否保存调试日志
    public static boolean isSaveCrashInfo = true;// 是否保存报错日志
    private static String TAG = "APP_LOG";
    private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

    /**
     * 清除day天前的日志
     * @param day
     */
    public static void cleanLog(int day){

    }

    public static void v(final String msg){
        if(isDebugModel){
            Log.v(TAG, "log--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("verbose", msg);
        }
    }
    public static void e( final String msg)
    {
        if (isDebugModel)
        {
            Log.e(TAG, "--> " + msg);
        }
        taskAysnc("error",msg);
    }

    private static void taskAysnc(final String file, final String msg){
        write(file,time() + TAG + " --> " + msg + "\n");
    }


    public static void d( final String msg)
    {
        /*if (isDebugModel)
        {
            Log.d(TAG, "log--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("debug", msg);
        }*/
    }

    public static void i(final String msg)
    {
        /*if (isDebugModel)
        {
            Log.i(TAG, "log--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("info", msg);
        }*/
    }

    public static void w(final String msg)
    {
        /*if (isDebugModel)
        {
            Log.w(TAG, "log--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("warn", msg);
        }*/
    }


    public static void v(final String tag, final String msg)
    {
        /*if (isDebugModel)
        {
            Log.v(tag, "--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("verbose", tag + ":>>" + msg);
        }*/
    }

    public static void d(final String tag, final String msg)
    {
        /*if (isDebugModel)
        {
            Log.d(tag, "--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("debug", tag + ":>>" + msg);
        }*/
    }

    public static void i(final String tag, final String msg)
    {
        /*if (isDebugModel)
        {
            Log.i(tag, "--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("info", tag + ":>>" + msg);
        }*/
    }

    public static void w(final String tag, final String msg)
    {
        /*if (isDebugModel)
        {
            Log.w(tag, "--> " + msg);
        }
        if(isSaveDebugInfo) {
            taskAysnc("warn", tag + ":>>" + msg);
        }*/
    }

    /**
     * 调试日志，便于开发跟踪。
     * @param tag
     * @param msg
     */
    public static void e(final String tag, final String msg)
    {
        if (isDebugModel)
        {
            Log.e(tag, "--> " + msg);
        }

        taskAysnc("error",tag+":>>"+msg);
    }

    /**
     * try catch 时使用，上线产品可上传反馈。
     * @param tag
     * @param tr
     */
    public static void e(final String tag, final Throwable tr)
    {
        /*if (isSaveCrashInfo)
        {
            write("sys-try",time() + tag + " [CRASH] --> "
                    + getStackTraceString(tr) + "\n");
        }*/
    }

    /**
     * 获取捕捉到的异常的字符串
     * @param tr
     * @return
     */
    public static String getStackTraceString(Throwable tr)
    {
        if (tr == null)
        {
            return "";
        }

        Throwable t = tr;
        while (t != null)
        {
            if (t instanceof UnknownHostException)
            {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 标识每条日志产生的时间
     * @return
     */
    private static String time()
    {
        return "["
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
                System.currentTimeMillis())) + "] ";
    }

    /**
     * 以年月日作为日志文件名称
     * @return
     */
    private static String date()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System
                .currentTimeMillis()));
    }

    /**
     * 保存到日志文件
     * @param content
     */
    public static synchronized void write(String content)
    {
        try
        {
            FileWriter writer = new FileWriter(getFile(), true);
            writer.write(content);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 保存到日志文件
     * @param content
     */
    public static synchronized void write(String file, String content)
    {
        try
        {
            /*
            FileWriter writer = new FileWriter(getFile(file), true);
            writer.write(content);
            writer.close();
            */
            // 打开一个随机访问文件流，按读写方式
            String filePath = getFile(file);
            if (filePath != null){
                RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");
                // 文件长度，字节数
                long fileLength = randomFile.length();
                // 将写文件指针移到文件尾。
                randomFile.seek(fileLength);
                randomFile.writeBytes(content);
                randomFile.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取日志文件路径
     * @return
     */
    public static String getFile()
    {
        File sdDir = null;

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            sdDir = Environment.getExternalStorageDirectory();

        File cacheDir = new File(sdDir + File.separator + CACHE_DIR_NAME);
        if (!cacheDir.exists())
            cacheDir.mkdir();

        File filePath = new File(cacheDir + File.separator + date() + ".txt");

        return filePath.toString();
    }
    /**
     * 获取日志文件路径
     * @return
     */
    public static String getFile(String file)
    {
        File sdDir = null;

        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED))
                sdDir = Environment.getExternalStorageDirectory();

            File cacheDir = new File(sdDir + File.separator + CACHE_DIR_NAME);
            if (!cacheDir.exists())
                cacheDir.mkdir();
            File filePath = new File(cacheDir + File.separator + date() +file+ ".txt");

            return filePath.toString();
        }catch (Exception e){

        }

        return null;
    }
}
