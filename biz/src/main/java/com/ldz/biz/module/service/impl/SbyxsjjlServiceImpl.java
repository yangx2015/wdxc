package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.bean.ClLsGjInfo;
import com.ldz.biz.module.bean.CsTxTj;
import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.mapper.ClGpsLsMapper;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.mapper.ClXcMapper;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.*;
import com.ldz.biz.module.service.ClYyService;
import com.ldz.biz.module.service.SbyxsjjlService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.bean.TrackJiuPian;
import com.ldz.util.bean.TrackPointsForReturn;
import com.ldz.util.bean.TrackPointsForReturn.Point;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.exception.RuntimeCheckException;
import com.ldz.util.yingyan.GuiJIApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SbyxsjjlServiceImpl extends BaseServiceImpl<ClSbyxsjjl, String> implements SbyxsjjlService {
	@Autowired
	private ClSbyxsjjlMapper entityMapper;
	@Autowired
	private ClGpsLsMapper clGpsLsMapper;
	@Autowired
	private ClZdglMapper zdglMapper;
	@Autowired
	private ClXcMapper xcMapper;
	@Autowired
	private ClYyService clYyService;
	@Value("${biz.scTime}")
	private String scTime;

	/*
	 * @Autowired private ClClMapper clclmapper;
	 */

	@Override
	protected Mapper<ClSbyxsjjl> getBaseMapper() {
		return entityMapper;
	}

	@Override
	public boolean fillPagerCondition(LimitedCondition condition) {
		String minutes = getRequestParamterAsString("minutes"); // 获取请求异常信息时长
		if(StringUtils.isNotBlank(minutes)) {
			String startTime = DateUtils.calculateTime(LocalDateTime.now(), "-", 60 * Integer.parseInt(minutes));
			try {
				condition.and().andGreaterThanOrEqualTo("cjsj", DateUtils.getDate(startTime,"yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		SysYh user = getCurrentUser();
		SimpleCondition simpleCondition = new SimpleCondition(ClZdgl.class);
		simpleCondition.eq(ClZdgl.InnerColumn.jgdm,user.getJgdm());
		List<ClZdgl> devices = zdglMapper.selectByExample(simpleCondition);
		List<String> deviceCodes = devices.stream().map(ClZdgl::getZdbh).collect(Collectors.toList());
		condition.in(ClSbyxsjjl.InnerColumn.zdbh,deviceCodes);
		return true;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClSbyxsjjl.class;
	}

	@Override
	public ApiResponse<String> saveEntity(ClSbyxsjjl entity) {
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<List<ClLsGjInfo>> historyTrajectory(gpsSJInfo gpssjinfo) {
		ApiResponse<List<ClLsGjInfo>> apiResponse = new ApiResponse<>();
		String xcTime = "2018-07-10 00:00:00";
		Date xcDate;
		Date startDate;
		Date endDate;
		try {
			xcDate = DateUtils.getDate(xcTime,"yyyy-MM-dd HH:mm:ss");
			startDate = DateUtils.getDate(gpssjinfo.getStartTime(),"yyyy-MM-dd HH:mm:ss");
			endDate = DateUtils.getDate(gpssjinfo.getEndTime(),"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			throw new RuntimeCheckException("日期转换异常");
		}
		List<ClLsGjInfo> cclLsGjInfos;
		if (startDate.getTime() > xcDate.getTime()){
			cclLsGjInfos = getTrackListNew(gpssjinfo);
		}else if (endDate.getTime() < xcDate.getTime()){
			cclLsGjInfos = getTrackListOld(gpssjinfo);
		}else{
			cclLsGjInfos = getTrackListNew(gpssjinfo);
			gpssjinfo.setEndTime(xcTime);
			List<ClLsGjInfo> cclLsGjInfos1 = getTrackListOld(gpssjinfo);
			cclLsGjInfos.addAll(cclLsGjInfos1);
		}
		apiResponse.setResult(cclLsGjInfos);
		return apiResponse;
	}

	private List<ClLsGjInfo> getTrackListNew(gpsSJInfo gpssjinfo){
		SimpleCondition condition = new SimpleCondition(ClXc.class);
		condition.gte(ClXc.InnerColumn.xcKssj,gpssjinfo.getStartTime());
		condition.lte(ClXc.InnerColumn.xcJssj,gpssjinfo.getEndTime());
		condition.eq(ClXc.InnerColumn.clZdbh,gpssjinfo.getZdbh());
		List<ClXc> xcList = xcMapper.selectByExample(condition);
		if (xcList.size() == 0){
			return new ArrayList<>();
		}

		List<ClLsGjInfo> cclLsGjInfos = new ArrayList<>();
		for (ClXc xc : xcList) {
			if (StringUtils.isEmpty(xc.getXcStartEnd()) || !xc.getXcStartEnd().contains(","))continue;
			long duration;
			try {
				Date startDate = DateUtils.getDate(xc.getXcKssj(),"yyyy-MM-dd HH:mm:ss");
				Date endDate = DateUtils.getDate(xc.getXcJssj(),"yyyy-MM-dd HH:mm:ss");
				duration = endDate.getTime() - startDate.getTime();
			} catch (ParseException e) {
				throw new RuntimeCheckException("日期转换异常");
			}

			ClLsGjInfo clLsGjInfoIn = new ClLsGjInfo();
			String[] points = xc.getXcStartEnd().split(",");
			String startPoint = points[0];
			String endPoint = points[1];
			clLsGjInfoIn.setJsjps(startPoint.replaceAll("-",","));
			clLsGjInfoIn.setKsgps(endPoint.replaceAll("-",","));
			clLsGjInfoIn.setKssj(xc.getXcKssj());
			clLsGjInfoIn.setJssj(xc.getXcJssj());
			clLsGjInfoIn.setSc(duration);
			cclLsGjInfos.add(clLsGjInfoIn);
		}
		return cclLsGjInfos;
	}
	private List<ClLsGjInfo> getTrackListOld(gpsSJInfo gpssjinfo){
		List<ClSbyxsjjl> historyTrajectory = entityMapper.historyTrajectory(gpssjinfo);

		List<List<ClSbyxsjjl>> clLsGjInfos = new ArrayList<>();

		for (int i = 0; i < historyTrajectory.size() - 1;) {
			if (historyTrajectory.get(i).getSjlx().equals("50")
					&& historyTrajectory.get(i + 1).getSjlx().equals("60")) {
				List<ClSbyxsjjl> ins = new ArrayList<>();
				ins.add(historyTrajectory.get(i));
				ins.add(historyTrajectory.get(i + 1));
				clLsGjInfos.add(ins);
				i = i + 2;
			} else {
				i = i + 1;
			}
		}
		List<ClLsGjInfo> cclLsGjInfos = new ArrayList<>();
		for (List<ClSbyxsjjl> clLsGjInfo : clLsGjInfos) {
			long a = clLsGjInfo.get(1).getCjsj().getTime() - clLsGjInfo.get(0).getCjsj().getTime();
			if (a >= Long.parseLong(scTime)) {
				ClLsGjInfo clLsGjInfoIn = new ClLsGjInfo();
				clLsGjInfoIn.setJsjps(clLsGjInfo.get(1).getWd() + "," + clLsGjInfo.get(1).getJd());
				clLsGjInfoIn.setKsgps(clLsGjInfo.get(0).getWd() + "," + clLsGjInfo.get(0).getJd());
				clLsGjInfoIn.setKssj(simpledate(clLsGjInfo.get(0).getCjsj()));
				clLsGjInfoIn.setJssj(simpledate(clLsGjInfo.get(1).getCjsj()));
				clLsGjInfoIn.setSc(a);
				cclLsGjInfos.add(clLsGjInfoIn);
			}
		}
		return cclLsGjInfos;
	}

	public String simpledate(Date date) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return simpleDateFormat.format(date);
	}

	@Override
	public ApiResponse<List<ClGpsLs>> getGuiJiGps(gpsSJInfo gpssjinfo) {
		ApiResponse<List<ClGpsLs>> response = new ApiResponse<>();

		List<ClGpsLs> zdbhAllLsGps = clGpsLsMapper.getZdbhAllLsGps(gpssjinfo);

		/*for (ClGpsLs guiJiGps : zdbhAllLsGps) {
			if (guiJiGps.getGdjd() == null || guiJiGps.getGdwd() == null) {

				guiJiGps.setGdjd(guiJiGps.getBdjd());
				guiJiGps.setGdwd(guiJiGps.getBdwd());
				clGpsLsMapper.updateByPrimaryKeySelective(guiJiGps);
			}
		}*/
		response.setResult(zdbhAllLsGps);
		return response;
	}

	@Override
	public ApiResponse<List<SafedrivingModel>> getSafeDrivig() {
		String type = getRequestParamterAsString("type");
		ApiResponse<List<SafedrivingModel>> apiResponse = new ApiResponse<>();
		HttpServletRequest request = getRequset();
		String sjxmLike = request.getParameter("sjxmLike");
		Map<String,Object> param = new HashMap<>();
		param.put("sjxmLike",sjxmLike);
		List<SafedrivingModel> safedriving = entityMapper.Safedriving(param);

		if ("aqjs".equals(type)){
			for (SafedrivingModel safedrivingModel : safedriving) {
				int total = safedrivingModel.getOverspeedCount() +
						safedrivingModel.getSpeedownCount() +
						safedrivingModel.getSpeedupCount() +
						safedrivingModel.getWheelCount();
				safedrivingModel.setTotal(total);
			}
			safedriving.sort(Comparator.comparingInt(SafedrivingModel::getTotal).reversed());
		}else if ("cstj".equals(type)){
			safedriving.sort(Comparator.comparingInt(SafedrivingModel::getOverspeedCount).reversed());
		}
		apiResponse.setResult(safedriving);
		return apiResponse;
	}

	@Override
	public ApiResponse<CsTxTj> getcs(String cph, String day) {
		if (StringUtils.isEmpty(cph)) {
			RuntimeCheck.ifBlank(cph, "车牌号不能为空！");
		}
		if (StringUtils.isEmpty(day)) {
			day = "3";
		}
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(new Date());
		calendarStart.set(Calendar.HOUR_OF_DAY, 23);
		calendarStart.set(Calendar.MINUTE, 59);
		calendarStart.set(Calendar.SECOND, 59);
		Date end = calendarStart.getTime();
		calendarStart.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(day));
		Date start = calendarStart.getTime();
		ApiResponse<CsTxTj> apiResponse = new ApiResponse<>();
		CsTxTj csTxTj = new CsTxTj();
		List<ClSbyxsjjl> clSbyxsjjls = entityMapper.findByCphAndTime(cph, start, end).stream()
				.filter(s -> s != null && s.getCjsj() != null).collect(Collectors.toList());
		SimpleDateFormat f = new SimpleDateFormat("HH");
		List<Integer> list = new ArrayList<>();
		int yi = 0;
		int er = 0;
		int san = 0;
		int si = 0;
		int wu = 0;
		int liu = 0;
		int qi = 0;
		for (ClSbyxsjjl clSbyxsjjl : clSbyxsjjls) {
			String format = f.format(clSbyxsjjl.getCjsj());
			if (StringUtils.equals(format, "08") || StringUtils.equals(format, "09")) {
				yi++;
			}

			if (StringUtils.equals(format, "10") || StringUtils.equals(format, "11")) {
				er++;
			}
			if (StringUtils.equals(format, "12") || StringUtils.equals(format, "13")) {
				san++;
			}
			if (StringUtils.equals(format, "14") || StringUtils.equals(format, "15")) {
				si++;
			}
			if (StringUtils.equals(format, "16") || StringUtils.equals(format, "17")) {
				wu++;
			}
			if (StringUtils.equals(format, "18") || StringUtils.equals(format, "19")) {
				liu++;
			}
			if (StringUtils.equals(format, "20") || StringUtils.equals(format, "21")) {
				qi++;
			}

		}
		list.add(yi);
		list.add(er);
		list.add(san);
		list.add(si);
		list.add(wu);
		list.add(liu);
		list.add(qi);
		csTxTj.setCount(list);
		apiResponse.setResult(csTxTj);
		return apiResponse;
	}

	/**
	 * 纠偏选项默认值为：
	 * need_denoise=1,radius_threshold=0, need_vacuate=1,need_mapmatch=0, radius_threhold=0,transport_mode=driving
	 * 取值规则为：
	 * 1.去噪，示例：
	 * need_denoise =0：不去噪
	 * need_denoise =1：去噪
	 *
	 * 2.抽稀，示例：
	 * need_vacuate =0：不抽稀
	 * need_vacuate=1：抽稀
	 *
	 * 3.绑路，示例：
	 * need_mapmatch=0：不绑路
	 * need_mapmatch=1：绑路
	 *
	 * 4.定位精度过滤，用于过滤掉定位精度较差的轨迹点，每个轨迹点示例：
	 * radius_threshold=0：不过滤
	 * radius_threshold=20：过滤掉radius大于20的轨迹点。
	 *
	 * radius_threshold的设置参考：若只希望保留GPS定位结果，则可设置为20（通常 GPS 定位精度不大于20）；若希望保留GPS和Wi-Fi定位结果，去除基站定位结果，则可设置为100（通常Wi-Fi 定位精度不超过100米）。
	 *
	 * 5.交通方式，鹰眼将根据不同交通工具选择不同的纠偏策略，目前支持驾车、骑行和步行，示例：
	 * transport_mode=driving
	 * transport_mode=riding
	 * transport_mode=walking
	 * 绑路时会依据道路形状进行补点，例如：原始轨迹在道路拐弯处缺点，绑路将进行补充，补点的定位时间目前取的是前一个原始点的定位时间。
	 * @param gpssjinfo
	 * @return
	 */
	@Override
	public ApiResponse<List<Point>> baiduGuiJi(gpsSJInfo gpssjinfo) {
		ApiResponse<List<Point>> apiResponse = new ApiResponse<>();

		TrackJiuPian guijis = new TrackJiuPian();
		guijis.setAk(GuiJIApi.AK);
		guijis.setCoord_type_output("bd09ll");

		Date kais = simpledate(gpssjinfo.getStartTime());

		Date jiesu = simpledate(gpssjinfo.getEndTime());

		guijis.setEnd_time(String.valueOf(jiesu.getTime() / 1000));

		guijis.setStart_time(String.valueOf(kais.getTime() / 1000));

		guijis.setEntity_name(gpssjinfo.getZdbh());
		guijis.setIs_processed("1");
		guijis.setProcess_option(
				"need_denoise=1,need_vacuate=1,need_mapmatch=1,transport_mode=driving");
		guijis.setService_id(GuiJIApi.SERVICE_ID);
		guijis.setSort_type("asc");

		guijis.setSupplement_mode("driving");

		TrackPointsForReturn points = GuiJIApi.getPoints(guijis, GuiJIApi.getPointsURL);
		List<Point> points2 = points.getPoints();
		apiResponse.setResult(points2);

		return apiResponse;
	}


	/**
	 * 本地库中的历史Gps 轨迹
	 * @param gpssjinfo
	 * @return
	 */
	@Override
	public ApiResponse<List<Point>> getYyGuiJi(gpsSJInfo gpssjinfo) {
		ApiResponse<List<Point>> apiResponse = new ApiResponse<>();
		SimpleCondition condition  = new SimpleCondition(Clyy.class);
		condition.and().andBetween(Clyy.InnerColumn.loc_time.name() , gpssjinfo.getStartTime() , gpssjinfo.getEndTime());
		condition.eq(Clyy.InnerColumn.zdbh.name() , gpssjinfo.getZdbh());
		condition.setOrderByClause(" LOCTIME ASC");
		List<Clyy> clyys = clYyService.findByCondition(condition);
		List<Point> points = new ArrayList<>();
		clyys.stream().forEach(clyy -> {
			Point p = new Point();
			p.set_object_key(clyy.getObject_key());
			p.setDirection(Integer.parseInt(clyy.getDirection()));
			p.setLatitude(clyy.getLatitude().doubleValue());
			p.setLongitude(clyy.getLongitude().doubleValue());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long locTime = 0;
			try {
				locTime = sdf.parse(clyy.getLoc_time()).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			p.setLoc_time(locTime);
			p.setSpeed(clyy.getSpeed().doubleValue());
			points.add(p);
		});
		apiResponse.setResult(points);

		return apiResponse;
	}

	public Date simpledate(String date) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = null;
		try {
			date2 = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date2;
	}

}
