package com.ldz.biz.module.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.ClLsGjInfo;
import com.ldz.biz.module.bean.GuiJiGps;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.mapper.ClGpsLsMapper;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.SbyxsjjlService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SbyxsjjlServiceImpl extends BaseServiceImpl<ClSbyxsjjl, String> implements SbyxsjjlService {
	@Autowired
	private ClSbyxsjjlMapper entityMapper;
	@Autowired
	private ClGpsLsMapper clGpsLsMapper;
	/*@Autowired
	private ClClMapper clclmapper;*/

	@Override
	protected Mapper<ClSbyxsjjl> getBaseMapper() {
		return entityMapper;
	}

	@Override
	public boolean fillCondition(LimitedCondition condition) {

		/*LimitedCondition condition2 = new LimitedCondition(ClCl.class);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		condition2.eq(ClCl.InnerColumn.cph, request.getAttribute("cph"));
		condition2.eq(ClCl.InnerColumn.cx, request.getAttribute("cx"));
		List<ClCl> selectByExample = clclmapper.selectByExample(condition2);

		List<String> collect = selectByExample.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
				.map(ClCl::getZdbh).collect(Collectors.toList());
		condition.in(ClSbyxsjjl.InnerColumn.zdbh, collect);*/
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
			if (a >= 10000) {
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
	public ApiResponse<List<GuiJiGps>> getGuiJiGps(gpsSJInfo gpssjinfo) {
		ApiResponse<List<GuiJiGps>> response = new ApiResponse<>();
		List<GuiJiGps> zdbhAllLsGps = clGpsLsMapper.getZdbhAllLsGps(gpssjinfo);
		response.setResult(zdbhAllLsGps);
		return response;
	}

}
