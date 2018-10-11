package cn.bidostar.ticserver.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.CarIntents;
import cn.bidostar.ticserver.utils.I;

/**
 * Created by admins on 2018/3/12.
 * 定时任务广播
 */

public class AlarmReceive extends BroadcastReceiver {
    private static final String TAG = "cn.bidostar.ticserver.receiver.AlarmReceive";
    @Override
    public void onReceive(Context context, Intent intent) {
        //I.e(TAG,"网络有变化了"+intent.getAction());
        ConnectivityManager manager = null;
        NetworkInfo gprs = null;
        NetworkInfo wifi = null;

        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            I.e(TAG,"network change："+intent.getAction());
            manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    String sleepZt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
                    if(isRun(context)){
                        AppApplication.getInstance().uploadGps();
                    }else if ("00".equals(sleepZt)){//网络链接之后，如果程序没有启动，直接启动
                        Intent intentMy=new Intent(Intent.ACTION_MAIN);
                        intentMy.addCategory(Intent.CATEGORY_LAUNCHER);
                        ComponentName cn=new ComponentName("cn.bidostar.ticserver",
                                "cn.bidostar.ticserver.TestActivity");
                        intentMy.setComponent(cn);
                        //Intent intentMy = new Intent(context, getTopActivity(context));
                        intentMy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intentMy);
                    }
                }else{
                    //I.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                }
            }else{
                //I.e(TAG, "当前没有网络连接，请确保你已经打开网络 22");
            }
        }
        String filter = CarIntents.ACTION_WAKEUP+CarIntents.ACTION_GOTOSLEEP;
        if(filter.contains(intent.getAction())){
            if(!isRun(context)){//程序没有运行，直接启动应用程序
                Intent intentMy=new Intent(Intent.ACTION_MAIN);
                intentMy.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn=new ComponentName("cn.bidostar.ticserver",
                        "cn.bidostar.ticserver.TestActivity");
                intentMy.setComponent(cn);
                //Intent intentMy = new Intent(context, getTopActivity(context));
                intentMy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentMy);
            }
        }
    }


    public boolean isRun(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = "cn.bidostar.ticserver";
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                I.i("ActivityService isRun()",info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="+info.baseActivity.getPackageName());
                break;
            }
        }
        I.i(TAG,"ActivityService isRun()"+"cn.bidostar.ticserver 程序  ...isAppRunning......"+isAppRunning);
        return isAppRunning;
    }

    /**
     * 判断服务是否开启
     *这里的serviceName是指服务的全名称，最好是全路径的。比如："com.example.demo.service.myservice".
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
