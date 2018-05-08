package com.ldz.biz.module.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.mapper.ClJsyMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.JsyService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;

import tk.mybatis.mapper.common.Mapper;

@Service
public class JsyServiceImpl extends BaseServiceImpl<ClJsy,String> implements JsyService{
    @Autowired
    private ClJsyMapper entityMapper;
    @Autowired
    private ClService clService;
   

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
         entity.setPwd("11111");
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

    @Override
    public ApiResponse<List<ClJsy>> notBindList(SysYh user) {
        // 查找已绑定的驾驶员
        SimpleCondition condition = new SimpleCondition(ClCl.class);
        condition.eq(ClCl.InnerColumn.jgdm,user.getJgdm());
        List<ClCl> cars = clService.findByCondition(condition);
        condition = new SimpleCondition(ClJsy.class);
        condition.eq(ClJsy.InnerColumn.jgdm,user.getJgdm());
        List<ClJsy> drivers = entityMapper.selectByExample(condition);
        if (cars.size() != 0){
            List<String> bindDriverIds = cars.stream().filter(p->p.getSjId() != null).map(ClCl::getSjId).collect(Collectors.toList());
            if (bindDriverIds.size() != 0){
                drivers.removeIf(jsy -> bindDriverIds.contains(jsy.getSfzhm()));
            }
        }
        return ApiResponse.success(drivers);
    }

}
