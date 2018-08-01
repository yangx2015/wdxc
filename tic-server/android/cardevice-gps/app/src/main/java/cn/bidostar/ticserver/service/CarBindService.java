package cn.bidostar.ticserver.service;

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
 * 这个service 主要是用于监听service的重启
 */

public class CarBindService extends Service {
    public final static String TAG = "cn.bidostar.ticserver.service.CarBindService";

    /**
     * 使用aidl 启动SocketCarBindService*/
    private StrongServiceAidlInterface startS2 = new StrongServiceAidlInterface.Stub() {
        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), SocketCarBindService.class);
            getBaseContext().stopService(i);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), SocketCarBindService.class);
            getBaseContext().startService(i);
        }
    };




    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    startService2();
                    break;

                default:
                    break;
            }

        };
    };

    /**
     * 判断Service2是否还在运行，如果不是则启动Service2
     */
    private void startService2() {
        boolean isRun = Utils.isServiceWork(CarBindService.this,
                "cn.bidostar.ticserver.service.SocketCarBindService");
        if (isRun == false) {

            try {
               startS2.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) startS2;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("CarbindService", "onStartCommand"+TAG);
        return START_STICKY;
    }

    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service2
     */
    @Override
    public void onTrimMemory(int level) {
        /*
         * 启动service2
         */
        startService2();

    }


    @Override
    public void onCreate() {

       /* Toast.makeText(CarBindService.this, "CarBindService 正在启动...", Toast.LENGTH_SHORT)
                .show();*/
        startService2();
        /*
         * 此线程用监听SocketCarBindService的状态
         */
        new Thread() {
            public void run() {
                while (true) {
                    boolean isRun = Utils.isServiceWork(CarBindService.this,
                            "cn.bidostar.ticserver.service.SocketCarBindService");
                    if (!isRun) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

}
