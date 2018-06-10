package com.ldz.biz.module.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 终端管理
 */
@RestController
@RequestMapping("api/zdgl")
public class ZdCtrl extends BaseController<ClZdgl,String> {
    @Autowired
    private ZdglService service;
    @Override
    protected BaseService<ClZdgl, String> getBaseService() {
        return service;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(@Valid ClZdgl znzp){
        return service.saveEntity(znzp);
    }

    @Override
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(@Valid ClZdgl entity){
        return service.updateEntity(entity);
    }

    /**
     * 查询所有未被绑定的未被停用的终端
     * @return
     */
    @RequestMapping(value="/getunboundlist", method={RequestMethod.POST})
    public ApiResponse<List<ClZdgl>> UnboundList(){
        return service.unboundList();
    }
    /*
     * 获取设备状况展示
     */
    @GetMapping("/zdcx")
    public ApiResponse<Map<String, Integer>> getzdxc(){
    	
    	return service.getzdxc();
    }

    @RequestMapping("getVersionInfo")
    public ApiResponse<String> getVersionInfo(String deviceId){
        return service.getVersionInfo(deviceId);
    }

    /**
     * 上传excel文件，批量导入终端
     */
    @PostMapping("/excel")
    public ApiResponse<String> saveBatch(String filePath) throws IOException {
        return service.saveBatch(filePath);
    }


}
