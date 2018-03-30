package com.ldz.biz.api;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.InstructionService;
import com.ldz.biz.module.service.SpkService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.SnowflakeIdWorker;


/*
 * 业务系统对外开放的接口
 * 
 */

@RestController
@RequestMapping("/pub/")
public class MessageApi {
   @Autowired
   private GpsService gpsservice;
   @Autowired
   private SpkService spkservice;
   @Autowired
   private InstructionService intstruction;
	/*
	 * 给tic-server提供gps存储接口
	 */
	@PostMapping("/gps/save")
	public ApiResponse<String> filterAndSave(@RequestBody GpsInfo entity){
		
		return gpsservice.filterAndSave(entity);
	}
	
	/*
	 * 给tic-server提供云视屏存储接口
	 */
	@PostMapping("/spk/save")
	public ApiResponse<String> saveSpk(@RequestBody GpsInfo entity){
		
		return spkservice.saveSpk(entity);
	}
	
	@PostMapping("/intstruction/send")
	public ApiResponse<String> sendinstruction(@RequestBody GpsInfo info){
		
		
		return intstruction.sendinstruction(info);
		
	}
	
	
	@Autowired
	private ClSbyxsjjlMapper  mapper;
	@Autowired
	SnowflakeIdWorker idWorker;
	@GetMapping("/testSave")
	public ApiResponse<String> sendinstruction(){
		ClSbyxsjjl clsb= new ClSbyxsjjl();
		
		clsb.setId(String.valueOf(idWorker.nextId()));
		clsb.setJd(new BigDecimal(11));
		clsb.setWd(new BigDecimal(22));
		clsb.setCjsj(new Date());
		clsb.setJid(new BigDecimal(33));
		clsb.setYxfx(new Double(11));
		clsb.setZdbh("11");
		clsb.setSjjb("20");
		clsb.setSjlx("10");
		
		mapper.insertSelective(clsb);
		
		return ApiResponse.success();
		
	}
}
