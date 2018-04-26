package cn.bidostar.ticserver.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import cn.wang.weicy.device.cardevice.StrongServiceAidlInterface;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/1/21.
 * 向服务器发送socket数据的service服务，并且此服务也监听carbindservice的状态
 */
public class SocketCarBindService extends Service {

    public final static String TAG = "cn.bidostar.ticserver.service.SocketCarBindService";

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    startService1();
                    break;

                default:
                    break;
            }

        };
    };

    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service1
     */
    @Override
    public void onTrimMemory(int level) {
        startService1();
    }

    private StrongServiceAidlInterface startS1 = new StrongServiceAidlInterface.Stub(){
        @Override
        public void stopService() throws RemoteException{
            Intent i = new Intent(getBaseContext(), CarBindService.class);
            getBaseContext().stopService(i);
        }
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), CarBindService.class);
            getBaseContext().startService(i);

        }
    };



    @SuppressLint("NewApi")
    public void onCreate() {

        Toast.makeText(SocketCarBindService.this, "SocketCarBindService 启动中...", Toast.LENGTH_SHORT)
                .show();
        startService1();
        /*
         * 此线程用监听Service2的状态
         */
        new Thread() {
            public void run() {
                while (true) {
                    boolean isRun = Utils.isServiceWork(SocketCarBindService.this,
                            "cn.bidostar.ticserver.service.CarBindService");
                    if (!isRun) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    /**
     * 判断Service1是否还在运行，如果不是则启动Service1
     */
    private void startService1() {
        boolean isRun = Utils.isServiceWork(SocketCarBindService.this,
                "cn.bidostar.ticserver.service.CarBindService");
        if (isRun == false) {
            try {
                startS1.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("SocketCarBindService", "onStartCommand"+TAG);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) startS1;
    }
}
