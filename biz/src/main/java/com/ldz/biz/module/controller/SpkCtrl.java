package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.ClSg;
import com.ldz.biz.module.model.ClSpk;
import com.ldz.biz.module.service.SpkService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 云视频库
 */
@RestController
@RequestMapping("api/spk")
public class SpkCtrl extends BaseController<ClSpk,String>{
    @Autowired
    private SpkService service;

    @Override
    protected BaseService<ClSpk, String> getBaseService() {
        return service;
    }
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClSpk entity){
        return service.updateEntity(entity);
    }
}
