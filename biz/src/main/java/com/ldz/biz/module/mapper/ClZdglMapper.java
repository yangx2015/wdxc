package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClZdgl;
import com.ldz.util.cache.MybatisRedisCache;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespace(implementation=MybatisRedisCache.class)
public interface ClZdglMapper extends Mapper<ClZdgl> {
    @Select("SELECT Z.* FROM CL_ZDGL Z   ,CL_CL C WHERE Z.ZDBH=C.ZDBH(+) AND Z.ZT='00' AND NVL(C.CL_ID,'1') ='1'")
    @Results({
            @Result(property = "zdbh", column = "ZDBH"),
            @Result(property = "xh", column = "XH"),
            @Result(property = "mc", column = "MC"),
            @Result(property = "cs", column = "CS"),
            @Result(property = "zt", column = "ZT"),
            @Result(property = "cjr", column = "CJR"),
            @Result(property = "cjsj", column = "CJSJ"),
            @Result(property = "xgr", column = "XGR")
    })
    List<ClZdgl> getUnboundList();
}