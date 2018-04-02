package com.ldz.znzp.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.gps.Gps;
import com.ldz.znzp.base.BaseService;
import com.ldz.znzp.bean.GpsInfo;
import com.ldz.znzp.model.ClCl;
import com.ldz.znzp.model.ClZd;

import java.math.BigDecimal;

public interface ClService extends BaseService<ClCl,String>{
    ApiResponse<String> saveEntity(ClCl entity);

    void report(String tid);

    void updateGps(GpsInfo gpsInfo);


    ClZd findCurrentZd(Gps currentGps, ClCl car);


    ClZd getCurrentZd(BigDecimal jd, BigDecimal wd, ClCl car, String currentZdId);

}
