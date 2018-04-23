package com.ldz.ticserver.api;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gexin.fastjson.JSON;
import com.ldz.ticserver.plugins.push.AppPushUtils;
import com.ldz.ticserver.plugins.push.PushModel;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.Consts;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.commonUtil.JsonUtil;

/**
 * 指令发送接口
 * @author wanggang
 *
 */
@RestController
@RequestMapping("/api/push")
public class PushApiController {

	//private 
	@Autowired
	private StringRedisTemplate redisDao;
	/**
	 * 向客户端发送指令
	 * @return
	 */
	@RequestMapping("/carcmd")
	public ApiResponse<String> pushCmdToClient(@RequestBody RequestCommonParamsDto dto){
		ApiResponse<String> ar = new ApiResponse<>();
		//RequestCommonParamsDto dto = JSON.parseObject(str, RequestCommonParamsDto.class); //JsonUtil.toBean(str, RequestCommonParamsDto.class);
		//任务为设备+指令+时间
		String taskId = dto.getDeviceId()+dto.getCmdType()+System.currentTimeMillis();
		PushModel pm = new PushModel();
		dto.setTaskId(taskId);
		pm.setClientId(dto.getDeviceId());
		pm.setPushData(dto);
		System.out.println(dto.toString());
		//System.err.println(dto0.toString());
		if(dto.getCmdType()!=null && dto.getCmdType().equals("11")||dto.getCmdType().equals("12")||dto.getCmdType().equals("13")){//发送拍照或者拍摄视频时的命令会用到
			ar.setResult(taskId);
			dto.setCmd(taskId);
			pm.setPushData(dto);
			//AppPushUtils.pushMessageAllOrClientByAlias(pm);
		}
		Set<String> resultSet = redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).members();
		boolean checkOnline = false;
		if(resultSet!=null && resultSet.size()>0){
			Iterator<String> it = resultSet.iterator();
			while(it.hasNext()){
				String deviceIdAll = it.next();
				String[] clientId = deviceIdAll.split(Consts.CAR_SPLITE);
				if(clientId[0].equals(dto.getDeviceId())){
					if(AppPushUtils.checkIdMessage(clientId[1]).getCode() == 11){
						redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).remove(deviceIdAll);
					}else{
						checkOnline = true;
						AppPushUtils.pushMessageAllOrClientByAlias(pm);
					}
				}
			}
		}
		if(checkOnline){
			ar.setMessage("操作成功");
		}else{
			ar.setCode(ApiResponse.FAILED);
			ar.setMessage("设备不在线，发送指令失败");
		}
		return ar;
	}
	
	/**
	 * 验证客户端是否在线
	 * @param dto
	 * @return
	 */
	@GetMapping("/checkOnlin/{deviceId}")
	public ApiResponse<String> checkClientOnline(@PathVariable("deviceId") String deviceId){
		ApiResponse<String> ar = new ApiResponse<>();
		Set<String> resultSet = redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).members();
		boolean checkOnline = false;
		if(resultSet!=null && resultSet.size()>0){
			Iterator<String> it = resultSet.iterator();
			while(it.hasNext()){
				String deviceIdAll = it.next();
				String[] clientId = deviceIdAll.split(Consts.CAR_SPLITE);
				if(clientId[0].equals(deviceId)){
					if(AppPushUtils.checkIdMessage(clientId[1]).getCode() == 11){
						redisDao.boundSetOps(Consts.CAR_ONLINE_KEY).remove(deviceIdAll);
					}else{
						checkOnline = true;
					}
				}
			}
		}
		if(checkOnline){
			ar.setMessage("设备目前在线");
		}else{
			ar.setCode(ApiResponse.FAILED);
			ar.setMessage("设备不在线");
		}
		return ar;
	}
	
	
	
}
