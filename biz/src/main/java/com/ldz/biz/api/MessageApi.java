package com.ldz.biz.api;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.service.GpsLsService;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.InstructionService;
import com.ldz.biz.module.service.SpkService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.redis.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;
	@Autowired
	private GpsLsService gpsLsService;

	/*
	 * 给tic-server提供gps存储接口
	 */
	@PostMapping("/gps/save")
	public ApiResponse<String> filterAndSave(@RequestBody GpsInfo entity) {

		return gpsservice.filterAndSave(entity);
	}

	/*
	 * 给tic-server提供云视屏存储接口
	 */
	@PostMapping("/spk/save")
	public ApiResponse<String> saveSpk(@RequestBody GpsInfo entity) {

		return spkservice.saveSpk(entity);
	}

	@PostMapping("/intstruction/send")
	public ApiResponse<String> sendinstruction( GpsInfo info) {

       return  intstruction.sendinstruction(info);
	}

	@PostMapping("/batchUpdate")
	public ApiResponse<String> batchUpdate( GpsInfo info,String jgdm) {

		return  intstruction.batchUpdate(info,jgdm);
	}

	/*@GetMapping("/test")
	public String test(){
		SimpleCondition s = new SimpleCondition(ClGpsLs.class);
		Example.Criteria criteria = s.createCriteria();
		criteria.andCondition("CJSJ >= to_date('2018-07-27 07:45:16','yyyy-MM-dd HH:mi:ss') and CJSJ <= to_date('2018-07-27 08:00:00','yyyy-MM-dd HH:mi:ss') and zdbh = '865923030038639' ");
		s.and(criteria);

		s.setOrderByClause(" CJSJ desc ");
		List<ClGpsLs> gpsLs = gpsLsService.findByCondition(s);
		List<PointListBean> listBeans = new ArrayList<>();
		gpsLs.stream().forEach(clGpsLs -> {
			PointListBean pointListBean = new PointListBean();
			pointListBean.setCoord_type_input("bd09ll");
			pointListBean.setDirection(clGpsLs.getFxj().doubleValue());
			pointListBean.setLatitude(clGpsLs.getBdwd().doubleValue());
			pointListBean.setLongitude(clGpsLs.getBdjd().doubleValue());
			pointListBean.setLoc_time(clGpsLs.getCjsj().getTime() / 1000);
			pointListBean.setSpeed(Double.parseDouble(clGpsLs.getYxsd()));
			listBeans.add(pointListBean);
		});

		return GuiJIApi.trackPoint(listBeans);
	}*/

	/*@GetMapping("test")
	public void s(){
		System.out.println("------");
		redisTemplateUtil.boundValueOps("1").set("123",10,TimeUnit.SECONDS);
		System.out.println("++++++");
		System.out.println(redisTemplateUtil.boundValueOps("1").get());
	}

	@GetMapping("tt")
	public void a(){
		System.out.println("========");
		System.out.println(redisTemplateUtil.boundValueOps("1").get());
		redisTemplateUtil.delete("1");
		System.out.println("|||||||||||||||||||||");
	}*/


}
