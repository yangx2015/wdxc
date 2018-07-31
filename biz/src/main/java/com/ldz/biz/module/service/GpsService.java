package com.ldz.biz.module.service;

import java.util.List;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.bean.websocketInfo;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClGps;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface GpsService extends BaseService<ClGps,String>{

    ApiResponse<String> onReceiveGps(GpsInfo gpsInfo);
	
    
	/*
	 * 将传入的gps信息转换成地图可用坐标,并对gps信息进行过滤存储
	 * @parm GpsInfo
	 * @Return ApiResponse<String>
	*/ 
   ApiResponse<String> filterAndSave(GpsInfo entity);
   
   
   /*
    * 传入一个gps点位判断改点位是否在所处的电子围栏里面,若在则返回围栏对象
    * @parm GpsInfo
    * @Return Boolean
    */
   ClDzwl JudgePoint(ClGps entity,ClCl clcl);
    
    /*
     * 将tic-server传入的gps信息转换百度 谷歌 经纬度.
     * @parm GpsInfo
     * @Return ClGps
     */
    ClGps  changeCoordinates(GpsInfo entity);
    
    /*
     * 根据原始的gps的事件类型返回ClSbyxsjjl对象,如若对象不为空则存储
     * @parm GpsInfo   ClGps
     */
    ClSbyxsjjl   saveClSbyxsjjl(GpsInfo entity,ClGps clgps,ClCl clcl);
    
    /*
     * 根据gps点位信息转换成推送至前端的模型
     * @parm ClGps GpsInfo
     */
    websocketInfo changeSocket(GpsInfo gpsinfo ,ClGps clpgs,ClGps gpsss);
    
    

    /*
     * 初始化页面点位信息
     * @parm 
     */
    ApiResponse<List<websocketInfo>>   inintGps();
    
    /*
     * 补全设备点火熄火状态
     */
    void sbyxsjjl(GpsInfo info,ClCl cl);
}
