package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClPb;
import com.ldz.util.mapperprovider.OracleInsertListMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

public interface ClPbMapper extends Mapper<ClPb> , OracleInsertListMapper<ClPb> {

}