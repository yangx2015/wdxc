package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClClyxjlMapper;
import com.ldz.biz.module.model.ClClyxjl;
import com.ldz.biz.module.model.ClPb;
import com.ldz.biz.module.service.ClyxjlService;
import com.ldz.biz.module.service.PbService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClyxjlServiceImpl extends BaseServiceImpl<ClClyxjl,String> implements ClyxjlService{
    @Autowired
    private ClClyxjlMapper entityMapper;

    @Autowired
    private PbService pbService;

    @Override
    protected Mapper<ClClyxjl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClClyxjl.class;
    }


    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        String time = DateUtils.getNowTime().substring(11);
        //排班
        SimpleCondition pbCondition = new SimpleCondition(ClPb.class);
        pbCondition.gte(ClPb.InnerColumn.endTime,time);
        pbCondition.and().andCondition(" TO_CHAR (PBSJ, 'yyyy-MM-dd') = ",DateUtils.getToday("yyyy-MM-dd"));
        List<ClPb> pbList=pbService.findByCondition(pbCondition);
        List<String> clList=null;
        if(pbList!=null&&pbList.size()>0){
            clList=pbList.stream().map(ClPb::getClId).collect(Collectors.toList());
            if(clList!=null&&clList.size()>0){
                condition.in(ClClyxjl.InnerColumn.clId,clList);
            }else {
                return false;
            }
        }else {
            return false;
        }
        condition.and().andCondition(" TO_CHAR(CJSJ, 'yyyy-MM-dd') = ",DateUtils.getToday("yyyy-MM-dd"));
        condition.setOrderByClause("cjsj desc");
        return true;
    }


    @Override
    public ApiResponse<String> saveEntity(ClClyxjl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }


}
