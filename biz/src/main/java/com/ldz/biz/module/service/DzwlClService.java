package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClDzwlCl;

public interface DzwlClService extends BaseService<ClDzwlCl,String>{
    ApiResponse<String> saveEntity(ClDzwlCl entity);
}
