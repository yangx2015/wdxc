package com.ldz.job.mapper;

import com.ldz.job.model.ClZdgl;
import com.ldz.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@CacheNamespace(implementation=MybatisRedisCache.class)
public interface ClZdglMapper extends Mapper<ClZdgl> ,InsertListMapper<ClZdgl> {
}