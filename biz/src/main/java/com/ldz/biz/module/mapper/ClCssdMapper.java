package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClCssd;
import com.ldz.biz.module.model.ClZdgl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClCssdMapper extends Mapper<ClCssd> {

    /*@Insert("<script>" +
            "INSERT ALL INTO CL_CSSD(ID,SDSX,CJR,CJSJ) values"+
            " <foreach collection='list' item='item' index='index' separator=' INTO CL_CSSD ' >  " +
            " (#{item.id},#{item.sdsx},#{item.cjr},#{item.cjsj})" +
            " </foreach> " +
            " select 1 from dual"+
            "</script>")
    void saveBatch(@Param("list")List<ClCssd> list);*/
}