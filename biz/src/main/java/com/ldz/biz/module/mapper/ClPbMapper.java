package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClPb;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ClPbMapper extends Mapper<ClPb> , OracleInsertListMapper<ClPb> {

    @Select("<script>" +
            " SELECT NVL(SUM(CLASSES),0) as su FROM CL_PB WHERE PBSJ=to_date(#{pbsj} ,'yyyyMMdd') AND CL_ID=#{clId}  " +
            "</script>")
    String getClPbClasses(@Param("clId") String clId, @Param("pbsj") String pbsj);
}