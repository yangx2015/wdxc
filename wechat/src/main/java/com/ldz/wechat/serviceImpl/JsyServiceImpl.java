package com.ldz.wechat.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClJsyMapper;
import com.ldz.wechat.model.ClJsy;
import com.ldz.wechat.service.JsyService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class JsyServiceImpl extends BaseServiceImpl<ClJsy,String> implements JsyService{
    @Autowired
    private ClJsyMapper entityMapper;
   

    @Override
    protected Mapper<ClJsy> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClJsy.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClJsy entity) {
    	 SysYh user = getCurrentUser();
    	 RuntimeCheck.ifTrue(ifExists(ClJsy.InnerColumn.sfzhm.name(),entity.getSfzhm()),"身份证号码已存在");
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setCjsj(now);
         entity.setJgdm(user.getJgdm());
         save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String> updateEntity(ClJsy entity) {
		 ClJsy findById = findById(entity.getSfzhm());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}

}
