package com.ldz.wechat.module.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldz.wechat.module.model.ClJsy;

import tk.mybatis.mapper.common.Mapper;

public interface ClJsyMapper extends Mapper<ClJsy> {

	@Select("SELECT * FROM CL_JSY t WHERE t.SFZHM=#{sfzhm} AND t.XM=#{xm}")
	ClJsy findJzg(@Param("sfzhm") String sfzhm,@Param("xm") String xm);

	@Select("SELECT * FROM CL_JSY t WHERE t.SFZHM=#{sfzhm}")
	ClJsy findById(@Param("sfzhm") String sfzhm);
}
