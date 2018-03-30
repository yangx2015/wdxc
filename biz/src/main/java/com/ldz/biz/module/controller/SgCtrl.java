package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.biz.module.model.ClSg;
import com.ldz.biz.module.service.SgService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

/**
 * 事故管理
 */
@RestController
@RequestMapping("api/sg")
public class SgCtrl extends BaseController<ClSg,String>{
    @Autowired
    private SgService service;

    @Override
    protected BaseService<ClSg, String> getBaseService() {
        return service;
    }
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClSg entity){
        return service.updateEntity(entity);
    }

    @RequestMapping("detail")
    public ApiResponse<ClSg> detail(String sgId){
        RuntimeCheck.ifBlank(sgId,"记录不存在");
        ClSg sg = service.findById(sgId);
        RuntimeCheck.ifNull(sg,"未找到记录");
        service.setSgwj(sg);
        return ApiResponse.success(sg);
    }
}
