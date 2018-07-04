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
    @Select("SELECT Z.* FROM CL_ZDGL Z  left join CL_CL C on  Z.ZDBH=C.ZDBH where Z.ZT='00' AND IFNULL(C.CL_ID,'1') ='1'")
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

/*    @Insert("<script>" +
            "INSERT ALL INTO CL_ZDGL(ZDBH,MC,XH,PZLMD,SPSCMS,CMD,ZXZT,JSLMD,GPSXT,CJR,CJSJ) values"+
            " <foreach collection='list' item='item' index='index' separator=' INTO CL_ZDGL ' >  " +
            " (#{item.zdbh},#{item.mc},#{item.xh},#{item.pzlmd},#{item.spscms},#{item.cmd},#{item.zxzt},#{item.jslmd},#{item.gpsxt},#{item.cjr},#{item.cjsj}) " +
            " </foreach> " +
            " select 1 from dual "+
            "</script>")
    void saveBatch(@Param("list")List<ClZdgl> list);*/

}