package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClCdMapper;
import com.ldz.biz.module.model.ClCd;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.service.CdService;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.JsyService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CdServiceImpl extends BaseServiceImpl<ClCd, String> implements CdService {
    @Autowired
    private ClCdMapper entityMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClService clService;
    @Autowired
    private JsyService jsyService;

    @Override
    protected Mapper<ClCd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return ClCd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClCd entity) {
        RuntimeCheck.ifBlank(entity.getCdmc(), "请输入车队名称");
        RuntimeCheck.ifBlank(entity.getDzxm(), "请输入队长姓名");
//    	RuntimeCheck.ifBlank(entity.getSjhm(), "请输入手机号码");
        RuntimeCheck.ifBlank(entity.getZt(), "请输入状态");
        SysYh user = getCurrentUser();
        SysJg org = jgService.findByOrgCode(user.getJgdm());
        Date now = new Date();
        entity.setCjr(getOperateUser());
        entity.setCjsj(now);
        entity.setJgdm(user.getJgdm());
        entity.setJgmc(org.getJgmc());
        entity.setCdbh(genId());
        save(entity);
        setCars(entity.getCdbh(), entity.getClIds());
        setDrivers(entity.getCdbh(), entity.getDriverIds());
        return ApiResponse.saveSuccess();
    }

    private void setCars(String cdbh, String carIds) {
        List<String> carIdList = null;
        if (StringUtils.isNotEmpty(carIds)) {
            String[] carIdsArr = carIds.split(",");
            carIdList = Arrays.asList(carIdsArr);
        }
        ;
        List<ClCl> carList = clService.findEq(ClCl.InnerColumn.cdbh, cdbh);
        if (carIdList == null) {
            if (carList.size() != 0) {
                for (ClCl cl : carList) {
                    cl.setCdbh("");
                    clService.updateEntity(cl);
                }
            }
            return;
        }

        List<String> hasIds = null;
        if (carList.size() != 0) {
            hasIds = new ArrayList<>();
            for (ClCl cl : carList) {
                if (carIdList.contains(cl.getClId())) {
                    hasIds.add(cl.getClId());
                } else {
                    cl.setCdbh("");
                    clService.updateEntity(cl);
                }
            }
        }
        if (hasIds != null){
            carIdList.retainAll(hasIds);
        }
        List<ClCl> modifyCarList = clService.findIn(ClCl.InnerColumn.clId, carIdList);
        for (ClCl cl : modifyCarList) {
            cl.setCdbh(cdbh);
            clService.updateEntity(cl);
        }
    }

    private void setDrivers(String cdbh, String driverIds) {
        List<String> driverIdList = null;
        if (StringUtils.isNotEmpty(driverIds)) {
            String[] arr = driverIds.split(",");
            driverIdList = Arrays.asList(arr);
        }
        ;
        List<ClJsy> driverList = jsyService.findEq(ClJsy.InnerColumn.cdbh, cdbh);
        if (driverIdList == null) {
            if (driverList.size() != 0) {
                for (ClJsy driver : driverList) {
                    driver.setCdbh("");
                    jsyService.updateEntity(driver);
                }
            }
            return;
        }

        List<String> hasIds = null;
        if (driverList.size() != 0) {
            hasIds = new ArrayList<>();
            for (ClJsy driver : driverList) {
                if (driverIdList.contains(driver.getSfzhm())) {
                    hasIds.add(driver.getSfzhm());
                } else {
                    driver.setCdbh("");
                    jsyService.updateEntity(driver);
                }
            }
        }
        if (hasIds != null){
            driverIdList.retainAll(hasIds);
        }
        List<ClJsy> modifyDriverList = jsyService.findIn(ClJsy.InnerColumn.sfzhm, driverIdList);
        for (ClJsy driver : modifyDriverList) {
            driver.setCdbh(cdbh);
            jsyService.updateEntity(driver);
        }
    }


    @Override
    public ApiResponse<String> updateEntity(ClCd entity) {
        RuntimeCheck.ifBlank(entity.getCdmc(), "请输入车队名称");
        RuntimeCheck.ifBlank(entity.getDzxm(), "请输入队长姓名");
//    	RuntimeCheck.ifBlank(entity.getSjhm(), "请输入手机号码");
        RuntimeCheck.ifBlank(entity.getZt(), "请输入状态");
        ClCd findById = findById(entity.getCdbh());
        RuntimeCheck.ifNull(findById, "未找到记录");
        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        setCars(entity.getCdbh(), entity.getClIds());
        setDrivers(entity.getCdbh(), entity.getDriverIds());
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<ClCl>> notBindCarList() {
        SimpleCondition condition = new SimpleCondition(ClCl.class);
        condition.and().andIsNull(ClCl.InnerColumn.cdbh.name()).orEqualTo(ClCl.InnerColumn.cdbh.name(),"");
        List<ClCl> carList = clService.findByCondition(condition);
        return ApiResponse.success(carList);
    }

    @Override
    public ApiResponse<List<ClJsy>> notBindDriverList() {
        SimpleCondition condition = new SimpleCondition(ClJsy.class);
        condition.and().andIsNull(ClJsy.InnerColumn.cdbh.name()).orEqualTo(ClCl.InnerColumn.cdbh.name(),"");
        List<ClJsy> carList = jsyService.findByCondition(condition);
        return ApiResponse.success(carList);
    }
}
