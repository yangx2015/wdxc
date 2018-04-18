package com.ldz.biz.module.mapper;

import com.ldz.biz.module.bean.ClClModel;
import com.ldz.biz.module.model.ClCl;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

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
			"ORDER BY CL.CX ASC,CL.CPH ASC ")
	List<ClClModel> getVehicleTypeStatistics();
}