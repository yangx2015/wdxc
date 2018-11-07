package com.ldz.znzp.mapper;

import java.util.List;

import com.ldz.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ldz.znzp.model.ClXlzd;

import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation= MybatisRedisCache.class, eviction= FifoCache.class)
public interface ClXlzdMapper extends Mapper<ClXlzd> {
	
	@Select("SELECT * FROM CL_XLzd WHERE xl_id = #{id}")
	@Results({
		@Result(property = "id", column = "ID"),   
		@Result(property = "zdId", column = "ZD_ID"),
		@Result(property = "cjsj", column = "CJSJ") 
	})   
    public List<ClXlzd> findStudents(String tid);

}