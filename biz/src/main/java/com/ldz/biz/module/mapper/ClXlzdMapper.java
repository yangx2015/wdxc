package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClXlzd;
import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface ClXlzdMapper extends Mapper<ClXlzd> ,OracleInsertListMapper<ClXlzd>{
}