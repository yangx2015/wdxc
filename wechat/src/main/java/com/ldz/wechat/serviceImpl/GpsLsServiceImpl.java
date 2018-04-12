package com.ldz.wechat.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.mapper.ClGpsLsMapper;
import com.ldz.wechat.model.ClGpsLs;
import com.ldz.wechat.service.GpsLsService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class GpsLsServiceImpl extends BaseServiceImpl<ClGpsLs, String> implements GpsLsService {
	@Autowired
	private ClGpsLsMapper entityMapper;

	@Override
	protected Mapper<ClGpsLs> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClGpsLs.class;
	}

	@Override
	public ApiResponse<String> saveEntity(ClGpsLs entity) {
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String[]> getZdbhAllLsGps(gpsSJInfo info) {

		ApiResponse<String[]> apiResponse = new ApiResponse<>();

		List<ClGpsLs> zdbhAllLsGps = entityMapper.getZdbhAllLsGps(info);
		List<String> gp= new ArrayList<>();
		for (ClGpsLs clGpsLs : zdbhAllLsGps) {
			BigDecimal bdjd = clGpsLs.getBdjd();
			BigDecimal bdwd = clGpsLs.getBdwd();
			gp.add(bdjd.toString());
			gp.add(bdwd.toString());
		}
		 String[] array = gp.toArray(new String[gp.size()]);
		apiResponse.setResult(array);
		
		return apiResponse;
	}
}
