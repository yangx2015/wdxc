package com.ldz.sys.mapper;

import com.ldz.sys.model.SysJsGn;
import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface SysJsGnMapper extends Mapper<SysJsGn>,OracleInsertListMapper<SysJsGn> {
}
