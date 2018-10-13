package com.ldz.biz.module.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.bean.WebsocketInfo;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClWf;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.WfService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.entity.Example;

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
	@Autowired
	private WfService wfxxService;

	@RequestMapping("getOrgCarList")
	public ApiResponse<List<ClCl>> getOrgCarList() {
		SysYh yh = getCurrentUser();
		List<ClCl> carList = clservice.getOrgCarList(yh.getJgdm());
		return ApiResponse.success(carList);
	}

	@RequestMapping("bindDriver")
	public ApiResponse<String> bindDriver(String carId,String driverId){
		return clservice.bindDriver(carId,driverId);
	}
	@RequestMapping("bindDevice")
	public ApiResponse<String> bindDevice(String carId,String devcieId){
		return clservice.bindDevice(carId,devcieId);
	}


	@RequestMapping("unbindDevice")
	public ApiResponse<String> unbindDevice(String carId){
		return clservice.unbindDevice(carId);
	}
	@RequestMapping("unbindDriver")
	public ApiResponse<String> unbindDriver(String carId){
		return clservice.unbindDriver(carId);
	}
	@Override
	protected BaseService<ClCl, String> getBaseService() {
		return clservice;
	}
	
	/**
	 * 更新车辆下一次年审日期
	 * @param entity
	 * @return
	 */
	@GetMapping(value = "/nextNssjYear/{id}")
	public ApiResponse<String> nextNssjYear(@PathVariable("id") String id) {
		return clservice.nextNssjYear(id);
	}
	
	/**
	 * 车辆违法信息录入
	 * @param wfxx
	 * @return
	 */
	@PostMapping(value = "/saveWfxx")
	public ApiResponse<String> saveWfxx(ClWf wfxx) {
		return wfxxService.saveEntity(wfxx);
	}
	
	/**
	 * 查询车辆有多少未处理的违法信息
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/totalWfxx/{id}")
	public ApiResponse<Integer> totalWfxx(@PathVariable("id") String id) {
		Integer total = new Integer(0);
		try{
			Example condition = new Example(ClWf.class);
			condition.and()
			.andEqualTo(ClWf.InnerColumn.wfCl.name(), id)
			.andEqualTo(ClWf.InnerColumn.wfStatus.name(), 0);
			total = wfxxService.countByCondition(condition);
		}catch(Exception e){
			
		}
		
		return new ApiResponse<Integer>(total);
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
	public ApiResponse<List<WebsocketInfo>> inintGps() {

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

	/**
	 *安全驾驶统计（返回图表所需的数据结构）
	 * @param days
	 * @return
	 */
	@RequestMapping("carAccStatistics")
	public ApiResponse<Map<String,Object>> carAccStatistics(Integer days,String type){
		return clservice.carAccStatistics(days,type);
	}

    /*
     * 获取年审饼图 30/60/90天
     */
	@GetMapping("/nianshenbt")
	public ApiResponse<Map<String, Integer>> getnianshen(){

		return clservice.getnianshen();
	}

}
