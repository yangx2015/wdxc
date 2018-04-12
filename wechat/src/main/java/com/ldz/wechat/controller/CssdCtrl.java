package com.ldz.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClCssd;
import com.ldz.wechat.service.CssdService;

/**
 * 超速设定
 */
@RestController
@RequestMapping("wechat/cssd")
public class CssdCtrl extends BaseController<ClCssd,String>{
    @Autowired
    private CssdService service;

    @Override
    protected BaseService<ClCssd, String> getBaseService() {
        return service;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ClCssd entity){
        return service.saveEntity(entity);
    }

    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClCssd entity){
        return service.updateEntity(entity);
    }

    /**
     * 根据车型获取超速设定值，带有机构筛选条件
     * @param cx
     * @return
     */
    @RequestMapping("getByCx")
    public ApiResponse<List<ClCssd>> getByCx(String cx){
        return service.getByCx(cx);
    }
}
