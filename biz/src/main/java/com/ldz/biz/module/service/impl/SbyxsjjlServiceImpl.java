package com.ldz.biz.module.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.ClLsGjInfo;
import com.ldz.biz.module.bean.CsTxTj;
import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.mapper.ClGpsLsMapper;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.SbyxsjjlService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.TrackJiuPian;
import com.ldz.util.bean.TrackPointsForReturn;
import com.ldz.util.bean.TrackPointsForReturn.Point;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.yingyan.GuiJIApi;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SbyxsjjlServiceImpl extends BaseServiceImpl<ClSbyxsjjl, String> implements SbyxsjjlService {
	@Autowired
	private ClSbyxsjjlMapper entityMapper;
	@Autowired
	private ClGpsLsMapper clGpsLsMapper;
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
	public boolean fillCondition(LimitedCondition condition) {

		/*
		 * LimitedCondition condition2 = new LimitedCondition(ClCl.class);
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest();
		 * condition2.eq(ClCl.InnerColumn.cph, request.getAttribute("cph"));
		 * condition2.eq(ClCl.InnerColumn.cx, request.getAttribute("cx")); List<ClCl>
		 * selectByExample = clclmapper.selectByExample(condition2);
		 * 
		 * List<String> collect = selectByExample.stream().filter(s ->
		 * StringUtils.isNotEmpty(s.getZdbh()))
		 * .map(ClCl::getZdbh).collect(Collectors.toList());
		 * condition.in(ClSbyxsjjl.InnerColumn.zdbh, collect);
		 */
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

		ApiResponse<List<ClLsGjInfo>> apiResponse = new ApiResponse<List<ClLsGjInfo>>();

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

		apiResponse.setResult(cclLsGjInfos);

		return apiResponse;
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
		ApiResponse<List<SafedrivingModel>> apiResponse = new ApiResponse<>();

		List<SafedrivingModel> safedriving = entityMapper.Safedriving();

		for (SafedrivingModel safedrivingModel : safedriving) {

			if (StringUtils.isEmpty(safedrivingModel.getOverspeedCount())) {
				safedrivingModel.setOverspeedCount("0");
			}
			if (StringUtils.isEmpty(safedrivingModel.getSpeedownCount())) {
				safedrivingModel.setSpeedownCount("0");
			}
			if (StringUtils.isEmpty(safedrivingModel.getSpeedupCount())) {
				safedrivingModel.setSpeedupCount("0");
			}
			if (StringUtils.isEmpty(safedrivingModel.getWheelCount())) {
				safedrivingModel.setWheelCount("0");
			}
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
				"need_denoise=0,need_vacuate=0,need_mapmatch=1,transport_mode=driving");
		guijis.setService_id(GuiJIApi.SERVICE_ID);
		guijis.setSort_type("asc");

		guijis.setSupplement_mode("driving");

		TrackPointsForReturn points = GuiJIApi.getPoints(guijis, GuiJIApi.getPointsURL);
		List<Point> points2 = points.getPoints();
		apiResponse.setResult(points2);

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
