package com.ldz.biz.module.service;

import java.util.List;

import com.ldz.biz.module.bean.ClLsGjInfo;
import com.ldz.biz.module.bean.GuiJiGps;
import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface SbyxsjjlService extends BaseService<ClSbyxsjjl,String>{
    ApiResponse<String> saveEntity(ClSbyxsjjl entity);
    ApiResponse<List<ClLsGjInfo>> historyTrajectory(gpsSJInfo gpssjinfo);
	ApiResponse<List<GuiJiGps>> getGuiJiGps(gpsSJInfo gpssjinfo);
	
	ApiResponse<List<SafedrivingModel>> getSafeDrivig();
}
