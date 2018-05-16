package com.ldz.wechat.module.mapper;

import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import com.ldz.wechat.module.model.ClXlzd;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

public interface ClXlzdMapper extends Mapper<ClXlzd>,OracleInsertListMapper<ClXlzd> {
}