package cn.bidostar.ticserver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bidostar.ticserver.service.CarBindService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.I;

/**
 * Created by admins on 2018/1/26.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    //重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        //后边的XXX.class就是要启动的服务   启动第一个service
        /*Intent service = new Intent(context,CarBindService.class);
        context.startService(service);
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(context, SocketCarBindService.class);
        context.startService(serviceTwo);

        */

        if(intent.getAction().equals("com.car.wakeup")){//唤醒
            I.e("com.car.wakeup ----");
        }else if(intent.getAction().equals("com.car.gotosleep")){//准备休眠
            I.e("com.car.gotosleep ----");
        }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            //这就是其它操作
        }else{

        }

        I.e("BootBroadcastReceiver", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("cn.bidostar.ticserver");
        context.startActivity(intent1);
    }
}
