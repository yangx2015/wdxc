package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.ClXc;
import com.ldz.biz.module.service.XcService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xc")
public class XcController extends BaseController<ClXc,String> {
    @Autowired
    private XcService service;

    @Override
    protected BaseService<ClXc, String> getBaseService() {
        return service;
    }
}
