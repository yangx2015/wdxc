package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.ClCssd;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClSg;
import com.ldz.biz.module.service.CssdService;
import com.ldz.biz.module.service.SgService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 超速设定
 */
@RestController
@RequestMapping("api/cssd")
public class CssdCtrl extends BaseController<ClCssd,String>{
    @Autowired
    private CssdService service;

    @Override
    protected BaseService<ClCssd, String> getBaseService() {
        return service;
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
