package cn.bidostar.ticserver.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * Created by wangg on 2016/12/30.
 */

public class AppActivityManager {
    private static LinkedList<Activity> activityStack;
    private static LinkedList<Activity> activityTemp;

    private static AppActivityManager instance;

    private AppActivityManager() {

    }

    public static void openActivity(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
    public static void openActivityForResult(Context context, Class<? extends Activity> clazz, Intent extras,int requestCode) {
        Intent intent = new Intent(context, clazz);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ((Activity)context).startActivityForResult(intent,requestCode);
    }

    public static void openActivity(Context context, Class<? extends Activity> clazz, Intent extras) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivity(Context context, Class<? extends Activity> clazz, Intent extras, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    /**
     * 获取 一个单例的 AppActivityManager
     *
     * @return
     */
    public static AppActivityManager getAppActivityManager() {
        if (instance == null) {
            instance = new AppActivityManager();
        }
        return instance;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<Activity>();
        }
        activityStack.add(activity);
    }


    /**
     * 添加临时activity
     * @param activity
     */
    public void addTempActivity(Activity activity){
        if(activityTemp==null){
            activityTemp  =  new LinkedList<Activity>();
        }
        if(activity!=null) {
            activityTemp.add(currentActivity());
            I.e("manager",activityTemp.size()+"");
        }
    }

    /**
     * 关闭所有临时的activity
     */
    public void closeTempActivity(){
        I.e("发表成功closeTempActivity");
        if(activityTemp!=null){
            I.e("发表成功closeTempActivity is not null");
            for(int i = 0,size=activityTemp.size();i<size;i++){
                activityStack.remove(activityTemp.get(i));
                activityTemp.get(i).finish();
                I.e("closeTempActivity",activityTemp.size()+"");
            }
            activityTemp.clear();
            activityTemp = null;
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.getLast();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.getLast();
        finishActivity(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivityByClass(Class<?> cls) {

        for (int i = 0, size = activityStack.size(); i < size; i++) {

            if (activityStack.get(i)!=null && activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }



    public int ActivityStackSize() {
        return activityStack == null ? 0 : activityStack.size();
    }
}
