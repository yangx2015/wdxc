package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClXl;
import com.ldz.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface ClXlMapper extends Mapper<ClXl> {
}