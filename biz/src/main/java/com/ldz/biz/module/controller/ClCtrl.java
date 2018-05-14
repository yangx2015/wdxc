package com.ldz.biz.module.controller;

import com.ldz.biz.module.bean.websocketInfo;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.GpsService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 车辆设定
 */
@RestController
@RequestMapping("api/cl")
public class ClCtrl extends BaseController<ClCl, String> {
	@Autowired
	private ClService clservice;
	@Autowired
	private GpsService gpsservice;

	@RequestMapping("getOrgCarList")
	public ApiResponse<List<ClCl>> getOrgCarList() {
		SysYh yh = getCurrentUser();
		List<ClCl> carList = clservice.getOrgCarList(yh.getJgdm());
		return ApiResponse.success(carList);
	}

	@Override
	protected BaseService<ClCl, String> getBaseService() {
		return clservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(@Valid ClCl entity) {
		return clservice.updateEntity(entity);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(@Valid ClCl entity) {
		return clservice.saveEntity(entity);
	}

	@GetMapping("/InitClGps")
	public ApiResponse<List<websocketInfo>> inintGps() {

		return gpsservice.inintGps();

	}

	/**
	 * 车辆类型统计
	 * 电子围栏，返回左边目录树 车辆类型统计
	 *[{"children":[{"mapCen":{"lat":30.554572,"lng":114.378443},"title":"鄂A123456"},{"mapCen":{"lat":30.564572,"lng":114.278443},"title":"鄂A123457"}],"expand":true,"title":"公务车"},{"children":[{"mapCen":{"lat":30.554562,"lng":114.378553},"title":"鄂B655552"},{"mapCen":{"lat":30.554372,"lng":114.378233},"title":"鄂A129957"}],"expand":true,"title":"校巴"}]
	 * 车辆类别(大车、小车)
	 * --车牌号
	 * -------车辆GPS坐标
	 * @param zxzt 在线状态 00:查询在线车辆，00点火 10 熄火 20离线
	 * @return
	 */

	@GetMapping("/getcltj")
	public ApiResponse<List<Map<String,Object>>> getVehicleTypeStatistics(String zxzt) {
		return clservice.getVehicleTypeStatistics(zxzt);
	}
	
	@GetMapping("/nianshen")
	public ApiResponse<List<ClCl>> nianshen(ClCl car){
		
		return clservice.nianshen(car);
		
	}

	@RequestMapping("carAccStatistics")
	public ApiResponse<Map<String,Object>> carAccStatistics(){
		return clservice.carAccStatistics();
	}

    /*
     * 获取年审饼图 30/60/90天
     */
	@GetMapping("/nianshenbt")
	public ApiResponse<Map<String, Integer>> getnianshen(){

		return clservice.getnianshen();
	}

}
