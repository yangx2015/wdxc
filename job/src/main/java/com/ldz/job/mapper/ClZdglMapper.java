package com.ldz.job.mapper;

import com.ldz.job.model.ClZdgl;
import com.ldz.util.cache.MybatisRedisCache;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespace(implementation=MybatisRedisCache.class)
public interface ClZdglMapper extends Mapper<ClZdgl> ,OracleInsertListMapper<ClZdgl>{

    @Select(" select * from CL_ZDGL where cl_sfyy is null")
    List<ClZdgl>  getZDNotYy();

}
