package com.ldz.wechat.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClXlzdMapper;
import com.ldz.wechat.model.ClXlzd;
import com.ldz.wechat.service.XlzdService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class XlzdServiceImpl extends BaseServiceImpl<ClXlzd,String> implements XlzdService{
    @Autowired
    private ClXlzdMapper entityMapper;

    @Override
    protected Mapper<ClXlzd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXlzd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXlzd entity) {
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setCjsj(now);
         entity.setId(genId());
         save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String> updateEntity(ClXlzd entity) {
	      ClXlzd findById = findById(entity.getId());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}
}