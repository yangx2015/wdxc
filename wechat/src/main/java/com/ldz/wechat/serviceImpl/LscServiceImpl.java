package com.ldz.wechat.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClLscMapper;
import com.ldz.wechat.model.ClLsc;
import com.ldz.wechat.service.LscService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class LscServiceImpl extends BaseServiceImpl<ClLsc,String> implements LscService{
    @Autowired
    private ClLscMapper entityMapper;

    @Override
    protected Mapper<ClLsc> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClLsc.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClLsc entity) {
        entity.setCjsj(new Date());
        entity.setCjr(getOperateUser());
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
