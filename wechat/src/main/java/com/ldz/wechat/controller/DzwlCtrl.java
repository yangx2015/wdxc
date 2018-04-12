package com.ldz.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClDzwl;
import com.ldz.wechat.service.DzwlService;

/**
 * 电子围栏
 */
@RestController
@RequestMapping("wechat/dzwl")
public class DzwlCtrl extends BaseController<ClDzwl,String>{
    @Autowired
    private DzwlService service;

    @Override
    protected BaseService<ClDzwl, String> getBaseService() {
        return service;
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

}
