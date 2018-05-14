package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.model.ClLsc;
import com.ldz.biz.module.service.LscService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClLsdwMapper;
import com.ldz.biz.module.model.ClLsdw;
import com.ldz.biz.module.service.LsdwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Service
public class LsdwServiceImpl extends BaseServiceImpl<ClLsdw,String> implements LsdwService{
    @Autowired
    private ClLsdwMapper entityMapper;
    @Autowired
    private LscService lscService;
    @Override
    protected Mapper<ClLsdw> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClLsdw.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClLsdw entity) {
        entity.setId(genId());
        entity.setCjr(getOperateUser());
        entity.setCjsj(new Date());
        entity.setDjcs(new Short("0"));
        save(entity);
        return ApiResponse.saveSuccess();
    }
    /**
     * 临时单位删除
     * @param id
     * @return
     */
    @Override
    public ApiResponse<String> delUnit(String id){
//        1、删除一个临时单位
       this.remove(id);
//        2、删除该单位下所有的车辆
        ClLsc lsc=new ClLsc();
        lsc.setLsdwId(id);
        lscService.remove(lsc);

       return ApiResponse.saveSuccess();
    }
}
