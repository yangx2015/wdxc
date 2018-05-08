package com.ldz.job.service;

import com.ldz.job.model.ClGps;
import com.ldz.sys.base.BaseService;

public interface GpsService extends BaseService<ClGps, String>{
	
	 /*
     * 插入一条数据 如果数据存在则更新
     * 
     */
    void insetAndUpdate(ClGps entity);
	
    
    /*
     * 根据终端编号 将redis里面的gps缓存存入数据库中
     */
    void InsetRedisToDb(String zdbh);
    
 
}
