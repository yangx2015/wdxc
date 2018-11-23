package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClXlzd;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import tk.mybatis.mapper.common.Mapper;

//@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface ClXlzdMapper extends Mapper<ClXlzd> ,OracleInsertListMapper<ClXlzd>{
}
