package cn.bidostar.ticserver.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.util.List;

public class Utils {

	public static void setMobileDataState(Context context, boolean enabled) {
		TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Method setDataEnabled = telephonyService.getClass().getDeclaredMethod("setDataEnabled",boolean.class);
			if (null != setDataEnabled) {
				setDataEnabled.invoke(telephonyService, enabled);
			}

			System.out.println("setMobileDataState >>>>success>>>>>>>>>>>>>>>>");
			I.e("setMobileDataState >>>>success");
		} catch (Exception e) {
			I.e("setMobileDataState >>>>Exception", e);
			e.printStackTrace();
		}
	}

	public static boolean getMobileDataState(Context context) {
		TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Method getDataEnabled = telephonyService.getClass().getDeclaredMethod("getDataEnabled");
			if (null != getDataEnabled) {
				System.out.println("getMobileDataState >>>>success>>>>>>>>>>>>>>>>");
				I.e("getMobileDataState >>>>success");
				return (Boolean) getDataEnabled.invoke(telephonyService);
			}
		} catch (Exception e) {
			I.e("getMobileDataState >>>>Exception", e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：com.beidian.test.service.BasicInfoService ）
	 * @return
	 */
	public static boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(100);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	/**
	 * 判断进程是否运行
	 * 
	 * @return
	 */
	public static boolean isProessRunning(Context context, String proessName) {

		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : lists) {
			if (info.processName.equals(proessName)) {
				isRunning = true;
			}
		}

		return isRunning;
	}
}
