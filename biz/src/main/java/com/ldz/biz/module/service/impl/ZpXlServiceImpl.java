package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClZpXlMapper;
import com.ldz.biz.module.model.ClZpXl;
import com.ldz.biz.module.service.ZpXlService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ZpXlServiceImpl extends BaseServiceImpl<ClZpXl,String> implements ZpXlService{
    @Autowired
    private ClZpXlMapper entityMapper;

    @Override
    protected Mapper<ClZpXl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZpXl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZpXl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }



}
