package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClZdgl;
import com.ldz.wechat.service.ZdglService;

/**
 * 终端管理
 */
@RestController
@RequestMapping("wechat/zdgl")
public class ZdCtrl extends BaseController<ClZdgl,String> {
    @Autowired
    private ZdglService service;
    @Override
    protected BaseService<ClZdgl, String> getBaseService() {
        return service;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ClZdgl znzp){
        return service.saveEntity(znzp);
    }

    @Override
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClZdgl entity){
        return service.updateEntity(entity);
    }
}
