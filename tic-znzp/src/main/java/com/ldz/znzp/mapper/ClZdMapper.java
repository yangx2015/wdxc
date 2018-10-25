package com.ldz.znzp.mapper;

import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.znzp.model.ClZd;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

public interface ClZdMapper extends Mapper<ClZd> {
}
