package com.ldz.znzp.api;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.znzp.bean.GpsInfo;
import com.ldz.znzp.model.ClCl;
import com.ldz.znzp.model.ClClyxjl;
import com.ldz.znzp.model.ClPb;
import com.ldz.znzp.model.ClXl;
import com.ldz.znzp.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 智能站牌对外提供的消息接口，一共提供如下几个接口
 * 1、校巴车载设备实时位置更新时，向智能站牌发送到站通知
 * 2、智能站牌多媒体广告信息，由运营平台配置完成后向站牌发送
 * 3、智能站牌LED屏信息显示
 * @author Lee
 *
 */
@RestController
@RequestMapping("/api")
public class MessageApi {
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;

	@Autowired
	private ClService clService;

	@Autowired
	private XlService xlService;
	@Autowired
	private ClyxjlService clyxjlService;

	@ Autowired
	private ZnzpService znzpService;
	@Autowired
	private HdService hdService;
	/**
	 * 将校巴实时位置信息发送给智能站牌展示。
	 * 接收到车辆和位置信息后，需要查询当前车辆是在哪条线路上进行运行，确认运营路线，再通过经纬度解析车辆当前运行到哪个站点
	 * 根据智能站牌配置的显示哪条运营线路找到站牌ID，向其发送车辆运行情况
	 */
	@RequestMapping(value="/reporting", method={RequestMethod.POST})
	public ApiResponse<String> reporting(@RequestBody GpsInfo gpsInfo ){
		if (StringUtils.isEmpty(gpsInfo.getDeviceId())){
			return ApiResponse.paramError("设备id不能为空");
		}
		if (StringUtils.isEmpty(gpsInfo.getLongitude())){
			return ApiResponse.paramError("经度不能为空");
		}
		if (StringUtils.isEmpty(gpsInfo.getLatitude())){
			return ApiResponse.paramError("纬度不能为空");
		}

		ClCl car = clService.getByDeviceId(gpsInfo.getDeviceId());
		if (car == null){
			return ApiResponse.notFound("未找到车辆");
		}

		List<ClClyxjl> clClyxjls = clyxjlService.findEq(ClClyxjl.InnerColumn.clId,car.getClId());

		ClPb pb = clService.getCarPb(car.getClId());
		if (pb == null){
			return ApiResponse.notFound("未找到车辆排班");
		}

		ClXl route = xlService.getCarXlfindById(pb.getXlId());
		if (route == null){
			return ApiResponse.notFound("未找到车辆线路");
		}

		ClClyxjl clClyxjl = clClyxjls.size() == 0 ? null : clClyxjls.get(0);

		ApiResponse<String> res = clService.updateGpsNew(gpsInfo,pb,car,route,clClyxjl);
		if (!res.isSuccess()){
			return res;
		}

		xlService.checkRouteInfo(route.getId());
		return clService.report(gpsInfo.getDeviceId(),pb,car,route,clClyxjl);
	}
	// todo 这个方法我没有使用。因为用post请求时，一直获取不到xlId.这个方法放弃
	@PostMapping("updateRouteInfo")
	public ApiResponse<String> updateRouteInfo(String xlId){
		xlService.checkRouteInfo(xlId);
		return ApiResponse.success();
	}

	@RequestMapping(value="/media", method={RequestMethod.POST})
	public void media(HttpServletRequest request){

	}

	@RequestMapping(value="/updateMedia", method={RequestMethod.POST})
	public ApiResponse<String> updateMedia(String jgdm){
		hdService.sendActivitys();
		return ApiResponse.success();
	}

	@RequestMapping(value="/led", method={RequestMethod.POST})
	public void led(HttpServletRequest request){

	}

	@RequestMapping("deleteRedisKey")
	public ApiResponse<String> deleteRedisKey(String key){
//		redisTemplateUtil.delete(key);
		return ApiResponse.success();
	}
}
