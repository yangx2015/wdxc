package com.ldz.znzp.mapper;

import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.znzp.model.ClZd;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;
// todo 暂时去掉这里的缓存。
//@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface ClZdMapper extends Mapper<ClZd> {
}
