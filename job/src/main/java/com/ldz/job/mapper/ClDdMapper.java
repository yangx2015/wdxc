package com.ldz.job.mapper;
import com.ldz.job.model.ClDd;

import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface ClDdMapper extends Mapper<ClDd> {
    @Update("<script>" +
            " UPDATE CL_DD D SET D.DDZT='12',D.BHYY='约车时间已过期，系统自动驳回用车申请' WHERE D.DDZT IN ('10','11') AND <![CDATA[ (D.YYSJ < (SYSDATE-1)) ]]>  " +
            "</script>")
    void updateBeOverdueOrder();
}