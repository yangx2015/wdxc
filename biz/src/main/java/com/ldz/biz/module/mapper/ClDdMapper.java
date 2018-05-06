package com.ldz.biz.module.mapper;

import java.util.List;

import com.ldz.biz.module.bean.DdTongjiTJ;
import com.ldz.biz.module.model.ClDd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ClDdMapper extends Mapper<ClDd> {
	List<ClDd> DdTongji(DdTongjiTJ dd);


    @Select({"<script> " +
        " INSERT INTO CL_DDLSB (ID, SJQRSJ, HCDZ, MDD, CPH, SJ, SJXM, ZJ, SC, DJ, LC, SCF, LCF, CK, CKZW, CKLXDH, ZWS, CKLX, YYSJ, DDZT, FKZT, FKFS, FKSJ, PJDJ, PJNR, CJSJ, CJR, XGSJ, XGR, JGDM, JGMC, CL_ID, GLF, SY, CLLX, WF, DZQRSJ, SHSJ, DZXM, FKBZ, DD_ID, XGCS, DDXGSJ, DDCJSJ, DDXGR)  " +
        " SELECT sys_guid(),SJQRSJ, HCDZ, MDD, CPH, SJ, SJXM, ZJ, SC, DJ, LC, SCF, LCF, CK, CKZW, CKLXDH, ZWS, CKLX, YYSJ, DDZT, FKZT, FKFS, FKSJ, PJDJ, PJNR, CJSJ, CJR, XGSJ, XGR, JGDM, JGMC, CL_ID, GLF, SY, CLLX, WF, DZQRSJ, SHSJ, DZXM, FKBZ, ID, (SELECT NVL(MAX(XGCS),0)+1 FROM CL_DDLSB B WHERE B.DD_ID=#{orderid}) XGCS, sysdate DDXGSJ, sysdate DDCJSJ, #{userid} AS DDXGR " +
        " FROM CL_DD  WHERE ID=#{orderid} " +
        " </script>"})
    void insertCopyOrder(@Param("orderid") String id,@Param("userid") String userId);
}