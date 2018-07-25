package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClCssd;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ClCssdMapper extends Mapper<ClCssd>,OracleInsertListMapper<ClCssd> {

    /*@Insert("<script>" +
            "INSERT ALL INTO CL_CSSD(ID,SDSX,CJR,CJSJ) values"+
            " <foreach collection='list' item='item' index='index' separator=' INTO CL_CSSD ' >  " +
            " (#{item.id},#{item.sdsx},#{item.cjr},#{item.cjsj})" +
            " </foreach> " +
            " select 1 from dual"+
            "</script>")
    void saveBatch(@Param("list")List<ClCssd> list);*/
}