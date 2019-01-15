package com.ldz.biz.module.controller;

import com.ldz.biz.module.bean.DdClModel;
import com.ldz.biz.module.model.ClXl;
import com.ldz.biz.module.model.ClZd;
import com.ldz.biz.module.service.ZdService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clzd")
public class ClZDCtrl extends BaseController<ClZd, String> {
	@Autowired
	private ZdService zdservice;

	@Override
	protected BaseService<ClZd, String> getBaseService() {
		return zdservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(@Valid ClZd entity) {
		return zdservice.updateEntity(entity);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(@Valid ClZd entity) {
		if(StringUtils.isNotEmpty(entity.getId())){
			return zdservice.updateEntity(entity);
		}
		return zdservice.saveEntity(entity);
	}

	/**
	 * 获取线路下站点和车辆的信息
	 * @param xlId	线路ID
	 * @return
	 */
	@RequestMapping(value = "/getzdcl", method = {RequestMethod.POST})
	public ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId) {
		return zdservice.getBySiteVehicleList(xlId);
	}

	@RequestMapping("/getByXlId")
	public ApiResponse<List<ClZd>> getByXlId(String xlId){
		List<ClZd> stations = zdservice.getByXlId(xlId);
		if(stations!=null&&stations.size()>0){
			for(ClZd zd:stations){
				String mc=zd.getMc();
				try {
					mc=StringUtils.remove(mc,"-回行");
				}catch (Exception e){}
				zd.setMc(mc);
			}
		}
		return ApiResponse.success(stations);
	}

	@RequestMapping("getNotBindList")
	public ApiResponse<List<ClZd>> getNotBindList(String stationId){
		return zdservice.getNotBindList(stationId);
	}

	@RequestMapping(value = "/getNearbyStations",method =  {RequestMethod.GET})
	public ApiResponse<List<ClZd>> getNearbyStations(String lng,String lat){
		return ApiResponse.success(zdservice.getNearbyStations(lng,lat));
	}
	@RequestMapping(value = "/getNearbyRoutes",method =  {RequestMethod.GET})
	public ApiResponse<List<ClXl>> getNearbyRoutes(String lng, String lat){
		return ApiResponse.success(zdservice.getNearbyRoutes(lng,lat));
	}
	@RequestMapping(value = "/getNearbyRoutesAndStations",method =  {RequestMethod.GET})
	public ApiResponse<Map<String,Object>> getNearbyRoutesAndStations(String lng, String lat){
		return ApiResponse.success(zdservice.getNearbyRoutesAndStations(lng,lat));
	}

	@RequestMapping("getStationInfo")
	public ApiResponse<Map<String,Object>> getStationInfo(String lng,String lat){
		return zdservice.getStationInfo(lng,lat);
	}

}
