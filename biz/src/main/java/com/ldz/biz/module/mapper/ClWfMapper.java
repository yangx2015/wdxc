package com.ldz.biz.module.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;

import com.ldz.biz.module.model.ClWf;
import com.ldz.util.cache.MybatisRedisCache;

import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface ClWfMapper extends Mapper<ClWf> {
}