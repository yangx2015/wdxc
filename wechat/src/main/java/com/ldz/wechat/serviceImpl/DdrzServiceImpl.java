package com.ldz.wechat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.wechat.mapper.ClDdrzMapper;
import com.ldz.wechat.model.ClDdrz;
import com.ldz.wechat.service.DdrzService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class DdrzServiceImpl extends BaseServiceImpl<ClDdrz,String> implements DdrzService{
    @Autowired
    private ClDdrzMapper entityMapper;

    @Override
    protected Mapper<ClDdrz> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClDdrz.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClDdrz entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

    /**
     * 通过订单ID查询该订单下的所有列表
     * @param orderId
     * @return
     */
    @Override
    public List<ClDdrz> getOrderList(String orderId) {
        SimpleCondition condition = new SimpleCondition(ClDdrz.class);
        condition.eq(ClDdrz.InnerColumn.ddId,orderId);
        condition.setOrderByClause(ClDdrz.InnerColumn.cjsj.asc());
        List<ClDdrz> oracleLog = findByCondition(condition);
        return oracleLog;
    }
}
