package com.ldz.biz.module.controller;

import com.ldz.biz.module.bean.ClLsGjInfo;
import com.ldz.biz.module.bean.CsTxTj;
import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.SbyxsjjlService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.TrackPointsForReturn.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/clsbyxsjjl")
public class ClSbyxsjjlCtrl extends BaseController<ClSbyxsjjl, String> {
	@Autowired
	private SbyxsjjlService service;

	@Override
	protected BaseService<ClSbyxsjjl, String> getBaseService() {
		return service;
	}

	/*
	 *
	 * 获取该终端所有历史轨迹数据
	 *  入参每个必填 点火熄火固定值
	 */
	@RequestMapping("/history")
	public ApiResponse<List<ClLsGjInfo>> historyTrajectory(gpsSJInfo gpssjinfo) {

		return service.historyTrajectory(gpssjinfo);
	}

	/*
	 *
	 * 获取该终端某段时间内gps坐标
	 * 入参 开始时间  结束时间 终端编号
	 */
	@PostMapping("/historygps")
	public ApiResponse<List<ClGpsLs>>  getGuiJiGps(gpsSJInfo gpssjinfo) {

		return service.getGuiJiGps(gpssjinfo);

	}

	@PostMapping("/baiduGuiJi")
	public ApiResponse<List<Point>>  getBaiDuGuiJi(gpsSJInfo gpssjinfo) {

		return service.baiduGuiJi(gpssjinfo);

	}


	@RequestMapping("/Safedriving")
	public ApiResponse<List<SafedrivingModel>> getSafeDrivig(){

		return service.getSafeDrivig();

	}

	/*
	 * 获取时间段内超数值
	 */
	@PostMapping("/csxxtj")
	public ApiResponse<CsTxTj> getcs(String cph,String day){

		return service.getcs(cph,day);

	}

	/**
	 * 查询本地库中的百度鹰眼历史数据
	 * @param gpssjinfo
	 * @return
	 */
	@PostMapping("/yyguiji")
	public ApiResponse<List<Point>> getYyGuiJi(gpsSJInfo gpssjinfo){

		return service.getYyGuiJi(gpssjinfo);

	}




}
