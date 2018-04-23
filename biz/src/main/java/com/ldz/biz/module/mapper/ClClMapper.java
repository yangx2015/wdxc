package com.ldz.biz.module.mapper;

import com.ldz.biz.module.bean.ClClModel;
import com.ldz.biz.module.model.ClCl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface ClClMapper extends Mapper<ClCl> {
	/*
	 * 通过车辆终端id找到对应的电子围栏
	 */
	ClCl seleByZdbh(String deviceId);
	
	/*
	 * 通过车辆id集合获取车俩信息
	 * 
	 */
	List<ClCl> getAllClInfo(List<String> list);
	
	ClCl seleClInfoByZdbh(String zdbh);

	@Select("SELECT GPS.BDJD,GPS.BDWD,CL.*,CL.CL_ID AS CLID,CL.SJ_ID AS SJID ,CL.OBD_CODE AS OBDCODE " +
			"  , NVL((SELECT XM.ZDMC FROM SYS_ZDXM XM WHERE XM.ZDLMDM='ZDCLK0019' AND  XM.ZDDM=CL.CX AND ROWNUM =1),'') AS CXZTMC " +
			"FROM CL_CL CL,CL_GPS GPS " +
			"WHERE CL.ZDBH=GPS.ZDBH(+) " +
			" AND CL.JGDM LIKE #{jgdm}||'%' " +
			"ORDER BY CL.CX ASC,CL.CPH ASC ")
	List<ClClModel> getVehicleTypeStatistics(String jgdm);

	@Select("SELECT GPS.BDJD,GPS.BDWD,CL.*,CL.CL_ID AS CLID,CL.SJ_ID AS SJID ,CL.OBD_CODE AS OBDCODE " +
			"  , NVL((SELECT XM.ZDMC FROM SYS_ZDXM XM WHERE XM.ZDLMDM='ZDCLK0019' AND  XM.ZDDM=CL.CX AND ROWNUM =1),'') AS CXZTMC " +
			"FROM CL_CL CL,CL_GPS GPS,CL_ZDGL ZDGL " +
			"WHERE CL.ZDBH=GPS.ZDBH(+) " +
			"  AND CL.ZDBH=ZDGL.ZDBH" +
			" AND CL.JGDM LIKE #{jgdm}||'%' " +
			" AND ZDGL.ZXZT= #{zxzt} " +
			"ORDER BY CL.CX ASC,CL.CPH ASC ")
	List<ClClModel> getVehicleTypeZxztStatistics(@Param("jgdm") String jgdm,@Param("zxzt") String zxzt);

	@Select(" SELECT CL.*,CL.CL_ID AS CLID FROM CL_CL CL " +
			" WHERE CL.ZT='00' AND CL.SJ_ID IS NOT NULL " +
			" AND CL.CX=#{cx} " +
			" AND CL.CL_ID NOT IN (SELECT PB.CL_ID FROM CL_PB PB WHERE PB.XL_ID=#{xlId} AND PB.PBSJ=#{date})" +
			" ORDER BY CL.CJSJ DESC ,CL.CPH DESC" +
			"")
	List<ClClModel> getAllNotPbClList(@Param("xlId")String xlId, @Param("date") Date date,@Param("cx")String cx);
}