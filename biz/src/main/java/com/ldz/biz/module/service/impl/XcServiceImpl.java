package com.ldz.biz.module.service.impl;


import com.ldz.biz.module.mapper.ClXcMapper;
import com.ldz.biz.module.model.ClXc;
import com.ldz.biz.module.service.XcService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.*;

@Service
public class XcServiceImpl extends BaseServiceImpl<ClXc,String> implements XcService{
    @Autowired
    private ClXcMapper entityMapper;

    @Override
    protected Mapper<ClXc> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXc.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXc clXc) {
        clXc.setId(genId());
        int i = save(clXc);
        return i==1 ? ApiResponse.success():ApiResponse.fail();
    }

    @Override
    public ApiResponse<List<Map<String, Object>>> history(String zdbh, String startTime, String endTime) {
        RuntimeCheck.ifBlank(zdbh,"请选择车辆");
        SimpleCondition condition = new SimpleCondition(ClXc.class);
        condition.eq(ClXc.InnerColumn.clZdbh,zdbh);
        condition.lte(ClXc.InnerColumn.xcJssj,endTime);
        condition.gte(ClXc.InnerColumn.xcKssj,startTime);
        condition.setOrderByClause( " XC_KSSJ ASC,XC_JSSJ ASC");
        List<ClXc> xcList = findByCondition(condition);
        List<Map<String,Object>> list = new ArrayList<>(xcList.size());
        if (xcList.size() == 0){
            return ApiResponse.success(list);
        }
        for (ClXc xc : xcList) {
            if (StringUtils.isEmpty(xc.getXcStartEnd()))continue;
            String[] startAndEndPoint = xc.getXcStartEnd().split(",");
            String startPoint = startAndEndPoint[0].replace("-",",");
            String endPoint = startAndEndPoint[1].replace("-",",");
            Map<String,Object> map = new HashMap<>();
            map.put("jsjps",endPoint);
            map.put("ksjps",startPoint);
            map.put("kssj",xc.getXcKssj());
            map.put("jssj",xc.getXcJssj());
            long sc = 0 ;
            try {
                Date startDate = DateUtils.getDate(xc.getXcKssj(),"yyyy-MM-dd HH:mm:ss");
                Date endDate = DateUtils.getDate(xc.getXcJssj(),"yyyy-MM-dd HH:mm:ss");
                sc = endDate.getTime() - startDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("sc",sc);
            list.add(map);
        }
        return ApiResponse.success(list);
    }
}
