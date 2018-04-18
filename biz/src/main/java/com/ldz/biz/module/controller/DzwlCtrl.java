package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.ClDd;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.service.DzwlService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 电子围栏
 */
@RestController
@RequestMapping("api/dzwl")
public class DzwlCtrl extends BaseController<ClDzwl,String>{
    @Autowired
    private DzwlService service;

    @Override
    protected BaseService<ClDzwl, String> getBaseService() {
        return service;
    }

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ClDzwl entity){
        return service.saveEntity(entity);
    }

    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClDzwl entity){
        return service.updateEntity(entity);
    }

    /**
     * 设置车辆电子围栏
     * @param clId
     * @param wlIds
     * @return
     */
    @RequestMapping("setCarDzwl")
    public ApiResponse<String> setCarDzwl(String clId,List<String> wlIds){
        return service.setCarDzwl(clId,wlIds);
    }

    /**
     * 为多个车辆设置同一个电子围栏
     * @param carIds
     * @param wlid
     * @return
     */
    @RequestMapping("setCarsDzwl")
    public ApiResponse<String> setCarsDzwl(String carIds,String wlid){
        return service.setCarsDzwl(carIds,wlid);
    }
}
