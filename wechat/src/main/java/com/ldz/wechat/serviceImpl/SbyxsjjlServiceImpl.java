package com.ldz.wechat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.mapper.ClSbyxsjjlMapper;
import com.ldz.wechat.model.ClSbyxsjjl;
import com.ldz.wechat.service.SbyxsjjlService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SbyxsjjlServiceImpl extends BaseServiceImpl<ClSbyxsjjl,String> implements SbyxsjjlService{
    @Autowired
    private ClSbyxsjjlMapper entityMapper;

    @Override
    protected Mapper<ClSbyxsjjl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClSbyxsjjl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSbyxsjjl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<List<ClSbyxsjjl>> historyTrajectory(gpsSJInfo gpssjinfo) {
		ApiResponse<List<ClSbyxsjjl>> apiResponse = new ApiResponse<List<ClSbyxsjjl>>();
		apiResponse.setResult(entityMapper.historyTrajectory(gpssjinfo));
		return apiResponse;
	}
}
