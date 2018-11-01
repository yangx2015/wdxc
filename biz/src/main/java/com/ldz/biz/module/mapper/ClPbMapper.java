package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClPb;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClPbMapper extends Mapper<ClPb> , OracleInsertListMapper<ClPb> {

    @Select("<script>" +
            " SELECT NVL(SUM(CLASSES),0) as su FROM CL_PB WHERE PBSJ=to_date(#{pbsj} ,'yyyyMMdd') AND CL_ID=#{clId}  " +
            "</script>")
    String getClPbClasses(@Param("clId") String clId, @Param("pbsj") String pbsj);

    @Update("<script>" +
            " UPDATE CL_PB SET ENABLE = '0' " +
            " where ID IN  " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','> " +
            "  #{item} " +
            " </foreach> " +
            "</script>")
    void updateRemovePb(List<String> delPb);
}