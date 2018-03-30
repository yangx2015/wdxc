package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.bean.websocketInfo;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClGps;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface GpsService extends BaseService<ClGps,String>{
	
    
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
   ClDzwl JudgePoint(GpsInfo entity);
    
    /*
     * 将tic-server传入的gps信息转换百度 谷歌 经纬度.
     * @parm GpsInfo
     * @Return ClGps
     */
    ClGps  changeCoordinates(GpsInfo entity);
    
    /*
     * 根据原始的gps的事件类型返回ClSbyxsjjl对象,如若对象不为空则存储
     * @parm GpsInfo
     */
    ClSbyxsjjl   saveClSbyxsjjl(GpsInfo entity);
    
    /*
     * 根据gps点位信息转换成推送至前端的模型
     * @parm ClGps GpsInfo
     */
    websocketInfo changeSocket(GpsInfo gpsinfo ,ClGps clpgs);
}
