package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ZdglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ZdglServiceImpl extends BaseServiceImpl<ClZdgl,String> implements ZdglService{
    @Autowired
    private ClZdglMapper entityMapper;

    @Override
    protected Mapper<ClZdgl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZdgl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZdgl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public void insetAndUpdate(ClZdgl entity) {
		
	 boolean flag= ifExists("zdbh",entity.getZdbh());
		if (flag==true) {
			update(entity);
		}else {
			save(entity);
		}
	}
}
