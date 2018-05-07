package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.ClXl;
import com.ldz.biz.module.model.ClZnzp;
import com.ldz.biz.module.service.ZnzpService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/znzp")
public class ZnzpCtrl extends BaseController<ClZnzp,String> {
    @Autowired
    private ZnzpService znzpService;
    @Override
    protected BaseService<ClZnzp, String> getBaseService() {
        return znzpService;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ClZnzp znzp){
        return znzpService.saveEntity(znzp);
    }

    @Override
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClZnzp znzp){
        return znzpService.updateEntity(znzp);
    }

    @RequestMapping("getXlIds")
    public ApiResponse<List<String>> getXlIds(String zpId){
        return znzpService.getXlIds(zpId);
    }
}
