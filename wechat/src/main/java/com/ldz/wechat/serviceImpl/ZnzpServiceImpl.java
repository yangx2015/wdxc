package com.ldz.wechat.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClZnzpMapper;
import com.ldz.wechat.model.ClZnzp;
import com.ldz.wechat.service.ZnzpService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ZnzpServiceImpl extends BaseServiceImpl<ClZnzp,String> implements ZnzpService{
    @Autowired
    private ClZnzpMapper entityMapper;

    @Override
    protected Mapper<ClZnzp> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZnzp.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZnzp entity) {
        entity.setCjr(getOperateUser());
        entity.setCjsj(new Date());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ClZnzp znzp) {
        znzp.setXgr(getOperateUser());
        znzp.setXgsj(new Date());
        update(znzp);
        return ApiResponse.success();
    }
}
