package com.ldz.sys.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysFw;
import com.ldz.sys.model.SysGn;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.GnService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 平台功能
 */
@RestController
@RequestMapping("/api/gn")
public class GnController extends BaseController<SysGn, String> {
    @Autowired
    private GnService gnService;

    @Override
    protected BaseService<SysGn, String> getBaseService() {
        return gnService;
    }

    @Override
    public ApiResponse<String> save(SysGn entity) {
        SysYh user = getCurrentUser();
        entity.setCjr(user.getYhid());
        return this.gnService.saveEntity(entity);
    }
    @RequestMapping(value = "getUserFunctions",method = RequestMethod.GET)
    public ApiResponse<List<SysGn>> getUserFunctions(){
        SysYh user = getCurrentUser();
        return ApiResponse.success(gnService.getUserFunctions(user));
    }

    @RequestMapping("getAllPermissionTree")
    public ApiResponse<List<SysFw>> getAllPermissionTree(){
        return ApiResponse.success(gnService.getAllPermissionTree());
    }
    @RequestMapping("getOrgPermissionTree")
    public ApiResponse<List<SysFw>> getOrgPermissionTree(){
        SysYh user = getCurrentUser();
        return ApiResponse.success(gnService.getOrgPermissionTree(user.getJgdm()));
    }
    @RequestMapping("getUserPermissionTree")
    public ApiResponse<List<SysFw>> getUserPermissionTree(){
        SysYh user = getCurrentUser();
        return ApiResponse.success(gnService.getUserPermissionTree(user));
    }

}
