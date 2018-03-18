package com.ldz.sys.controller;

import com.github.pagehelper.Page;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysHdyx;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.HdService;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.ContextUtil;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 活动营销
 */
@RestController
@RequestMapping("/api/hd")
public class HdController {
    @Autowired
    private HdService service;


    @RequestMapping("add")
    public ApiResponse<String> add(SysHdyx entity){
        return service.saveEntity(entity);
    }

    @RequestMapping("update")
    public ApiResponse<String> update(SysHdyx entity){
        return service.updateEntity(entity);
    }

    @RequestMapping("pager")
    public ApiResponse<List<SysHdyx>> pager(Page<SysHdyx> pager){
        return service.pager(pager);
    }

}
