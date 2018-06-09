package com.ldz.job.mapper;

import org.apache.ibatis.annotations.CacheNamespace;

import com.ldz.job.model.ClZdgl;
import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.util.mapperprovider.OracleInsertListMapper;

import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class)
public interface ClZdglMapper extends Mapper<ClZdgl> ,OracleInsertListMapper<ClZdgl>{
}