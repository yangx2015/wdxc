package com.ldz.wechat.module.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldz.wechat.module.model.ClJsy;

import tk.mybatis.mapper.common.Mapper;

public interface ClJsyMapper extends Mapper<ClJsy> {

	@Select("SELECT * FROM CL_JSY t WHERE t.SJH=#{sjh} AND t.PWD=#{pwd} AND  rownum=1")
	ClJsy findJzg(@Param("sjh") String sjh,@Param("pwd") String pwd);

	@Select("SELECT * FROM CL_JSY t WHERE t.SFZHM=#{sfzhm}  AND  rownum=1 ")
	ClJsy findById(@Param("sfzhm") String sfzhm);
}
