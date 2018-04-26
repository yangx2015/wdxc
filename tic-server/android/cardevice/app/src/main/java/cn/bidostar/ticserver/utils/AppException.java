package cn.bidostar.ticserver.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by wangg on 2016/12/30.
 */

public class AppException extends Exception implements Thread.UncaughtExceptionHandler {
    private static final long serialVersionUID = 1L;

    /**
     * 是否保存错误日志
     */
    private static final boolean Debug = false;
    //定义异常类型
    private static final byte TYPE_NETWORK = 0x01;//网络异常
    private static final byte TYPE_SOCKET = 0x02;//socket 异常
    private static final byte TYPE_HTTP_CODE = 0x03;//httpcode异常
    private static final byte TYPE_HTTP_ERROR = 0x04;//http错误异常
    private static final byte TYPE_XML = 0x05;
    private static final byte TYPE_IO = 0x06; //IO异常
    private static final byte TYPE_RUN = 0x07;//运行异常

    private byte type;
    private int code;


    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private AppException() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    private AppException(byte type, int code, Exception excp) {
        super(excp);
        this.type = type;
        this.code = code;
        if (Debug) {
            this.saveErrorLog(excp);
        }
    }

    public int getCode() {
        return this.code;
    }

    public int getType() {
        return this.type;
    }

    /**
     * 保存异常日志
     *
     * @param excp
     */
    public void saveErrorLog(Exception excp) {
        String errorlog = "errorlog.txt";
        String savePath = "";
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/weicyframe/Log/";
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                logFilePath = savePath + errorlog;
            }
            //没有挂载SD卡，无法写文件
            if (logFilePath == "") {
                return;
            }
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.println("--------------------" + (new Date().toString()) + "---------------------");
            excp.printStackTrace(pw);
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                }
            }
        }

    }

    public static AppException http(int code) {
        return new AppException(TYPE_HTTP_CODE, code, null);
    }

    public static AppException http(Exception e) {
        return new AppException(TYPE_HTTP_ERROR, 0, e);
    }

    public static AppException socket(Exception e) {
        return new AppException(TYPE_SOCKET, 0, e);
    }

    public static AppException io(Exception e) {
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            return new AppException(TYPE_NETWORK, 0, e);
        } else if (e instanceof IOException) {
            return new AppException(TYPE_IO, 0, e);
        }
        return run(e);
    }

    public static AppException xml(Exception e) {
        return new AppException(TYPE_XML, 0, e);
    }

    public static AppException run(Exception e) {
        return new AppException(TYPE_RUN, 0, e);
    }

    /**
     * 获取APP异常崩溃处理对象
     *
     * @return
     */
    public static AppException getAppExceptionHandler() {
        return new AppException();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }

    }

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     *
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        System.out.println(ex.getMessage());

        StringBuffer exceptionStr = new StringBuffer();
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        System.out.println(exceptionStr.toString());

        final Context context = AppActivityManager.getAppActivityManager().currentActivity();

        if (context == null) {
            return false;
        }

        final String crashReport = getCrashReport(context, ex);
        //显示异常信息&发送报告
        new Thread() {
            public void run() {
                Looper.prepare();
                sendAppCrashReport(context, crashReport);
                Looper.loop();
            }

        }.start();
        return true;
    }

    /**
     * 发送App异常崩溃报告
     *
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context cont, final String crashReport) {
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_exception_error_title);
        builder.setMessage(R.string.app_exception_error_text);
        builder.setPositiveButton(R.string.app_dialog_txt_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //发送异常报告
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain"); //模拟器
                //i.setType("message/rfc822") ; //真机
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{ActivityUtils.getStringByResId(R.string.app_report_email)});
                i.putExtra(Intent.EXTRA_SUBJECT, ActivityUtils.getStringByResId(R.string.app_report_name));
                i.putExtra(Intent.EXTRA_TEXT, crashReport);
                cont.startActivity(Intent.createChooser(i, ActivityUtils.getStringByResId(R.string.app_send_report)));
                //退出
//                AppActivityManager.getAppActivityManager().AppExit(cont);
                AppActivityManager.getAppActivityManager().AppExit();
            }
        });
        builder.setNegativeButton(R.string.app_dialog_txt_clean, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //退出
//                AppActivityManager.getAppActivityManager().AppExit(cont);
                AppActivityManager.getAppActivityManager().AppExit();
            }
        });
        builder.show();
        */
    }

    /**
     * 获取APP崩溃异常报告
     *
     * @param ex
     * @return
     */
    private String getCrashReport(Context context, Throwable ex) {
        PackageInfo pinfo = null;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (pinfo == null) {
            pinfo = new PackageInfo();
        }
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: " + pinfo.versionName + "(" + pinfo.versionCode + ")\n");
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        return exceptionStr.toString();
    }
}
