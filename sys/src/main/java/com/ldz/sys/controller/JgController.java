package com.ldz.sys.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/jg")
public class JgController extends BaseController<SysJg,String> {
    @Autowired
    private JgService jgService;

    @Override
    protected BaseService<SysJg, String> getBaseService() {
        return jgService;
    }

    @RequestMapping("add")
    public ApiResponse<String> add(SysJg entity){
        return jgService.saveEntity(entity);
    }

    @RequestMapping("getOrgTree")
    public ApiResponse<List<SysJg>> getOrgTree(){
//        SysYh user = getCurrentUser();
        List<SysJg> orgs = jgService.findAllSubOrg("");
        List<SysJg> orgTree = jgService.getOrgTree(orgs);
        return ApiResponse.success(orgTree);
    }

}
