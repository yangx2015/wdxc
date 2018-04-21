package com.ldz.biz.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.bean.ClLsGjInfo;
import com.ldz.biz.module.bean.GuiJiGps;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.SbyxsjjlService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

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
	public ApiResponse<List<GuiJiGps>>  getGuiJiGps(gpsSJInfo gpssjinfo) {

		return service.getGuiJiGps(gpssjinfo);
	}

}
