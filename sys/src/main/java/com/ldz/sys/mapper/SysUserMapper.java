package com.ldz.sys.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;

import com.ldz.sys.model.SysUser;
import com.ldz.util.cache.MybatisRedisCache;

import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface SysUserMapper extends Mapper<SysUser> {
}