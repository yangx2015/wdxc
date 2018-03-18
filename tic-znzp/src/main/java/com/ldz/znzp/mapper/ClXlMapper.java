package com.ldz.znzp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ldz.znzp.model.ClXl;

import tk.mybatis.mapper.common.Mapper;

public interface ClXlMapper extends Mapper<ClXl> {
	
	/**
	 * 一对一(@One)注解中的id,使用的是属性名称
	 * 一对多(@Many)注解中的id,是你本对象的主键属性名称
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM CL_XL WHERE id = #{id}") 	
	@Results({
		@Result(property = "id", column = "ID"),
		@Result(property = "xlmc", column = "XLMC"),
	    @Result(property = "clxlzds", javaType = List.class, column = "ID",  
        many = @Many(select = "com.ldz.znzp.mapper.ClXlzdMapper.findStudents"))  
	})   
	public ClXl getManyData(@Param("id") String id);  
}