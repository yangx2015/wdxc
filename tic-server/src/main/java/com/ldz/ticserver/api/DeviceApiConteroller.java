package com.ldz.ticserver.api;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.ticserver.service.BizApiService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.Consts;
import com.ldz.util.bean.RequestCommonParamsDto;

/**
 * 设备信息收集接口
 * @author wanggang
 *
 */
@RestController
@RequestMapping("/api/device")
public class DeviceApiConteroller {

	private static final Logger logger = LoggerFactory.getLogger(DeviceApiConteroller.class);
	@Autowired
	private BizApiService bizApiService;
	@Autowired
	private StringRedisTemplate redisDao;
	/**
	 * 接收客户端发送的Gps坐标数据
	 * @return
	 */
	@RequestMapping("/gps")
	public ApiResponse<String> postGpsData(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		//logger.debug("请求了GPS上传的方法");
		if(dto!=null && StringUtils.isNotBlank(dto.getDeviceId())){
			bizApiService.pushData(dto);
		}
		
		//if(dto!=null && dto.getEventType()!=null && dto.getEventType().equals("60")){
		//	redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).remove(dto.getDeviceId()+Consts.CAR_SPLITE+dto.getChannelId());
		//}else{
			redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).add(dto.getDeviceId()+Consts.CAR_SPLITE+dto.getChannelId());
		//}
		ar.setResult(dto.toString());
		return ar;
	}
	
	/**
	 * 批量接收GPS离线坐标
	 * @param dtos
	 * @return
	 */
	@RequestMapping("/dbgps")
	public ApiResponse<String> postGpsDataAll(@RequestBody List<RequestCommonParamsDto> dtos){
		ApiResponse<String> ar = new ApiResponse<>();
		//logger.debug("请求了批量GPS上传的方法");
		
		for (RequestCommonParamsDto requestCommonParamsDto : dtos) {
			if(requestCommonParamsDto!=null && StringUtils.isNotBlank(requestCommonParamsDto.getDeviceId())){
				bizApiService.pushData(requestCommonParamsDto);
			}
		}
		if(dtos!=null){
			redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).add(dtos.get(0).getDeviceId()+Consts.CAR_SPLITE+dtos.get(0).getChannelId());
		}
		//ar.setResult(dto.toString());
		return ar;
	}
	
	
	/**
	 * 接收上传的汽车速度信息数据
	 * @return
	 */
	@RequestMapping("/speed")
	public ApiResponse<String> postSpeedData(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		
		
		return ar;
	}
	
	/**
	 * 接收上传的其它数据
	 * @return
	 */
	@RequestMapping("/other")
	public ApiResponse<String> postOtherData(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		
		
		return ar;
	}
	
	/**
	 * 接收点火数据
	 * @return
	 */
	@RequestMapping("/ignition")
	public ApiResponse<String> postIgnitionData(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		
		
		return ar;
	}
	
	/**
	 * 接收熄火数据
	 * @return
	 */
	@RequestMapping("/flameout")
	public ApiResponse<String> postFlameoutData(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		
		
		return ar;
	}
	
	
	/**
	 * 接收上传文件后的数据
	 * @return
	 */
	@RequestMapping("/fileparams")
	public ApiResponse<String> postUploadData(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		
		
		return ar;
	}
	
}
