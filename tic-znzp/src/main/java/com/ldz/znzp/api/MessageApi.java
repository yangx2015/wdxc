package com.ldz.znzp.api;

import javax.servlet.http.HttpServletRequest;

import com.ldz.znzp.service.ClService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	private ClService clService;
	/**
	 * 将校巴实时位置信息发送给智能站牌展示。
	 * 接收到车辆和位置信息后，需要查询当前车辆是在哪条线路上进行运行，确认运营路线，再通过经纬度解析车辆当前运行到哪个站点
	 * 根据智能站牌配置的显示哪条运营线路找到站牌ID，向其发送车辆运行情况
	 * @param request
	 */
//	@RequestMapping(value="/reporting", method={RequestMethod.POST})
	@RequestMapping(value="/reporting")
	public void reporting(HttpServletRequest request){
		//车载设备ID
//		String sid = request.getParameter("sid");
		String sid = "1234567890ab";
		//车牌号码
		String hphm = request.getParameter("hphm");
		//经度
//		String lng = request.getParameter("lng");
		String lng = "33.3333333";
		//纬度
//		String lat = request.getParameter("lat");
		String lat = "133.333333";
		//定位精度
		String accuracy = request.getParameter("accuracy");
		//方向角
		String direct = request.getParameter("direct");
		clService.updateGps(sid,lng,lat);
		clService.report(sid);
	}
	
	@RequestMapping(value="/media", method={RequestMethod.POST})
	public void media(HttpServletRequest request){
		
	}

	@RequestMapping(value="/led", method={RequestMethod.POST})
	public void led(HttpServletRequest request){
		
	}
}
