package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClDzwlCl;

public interface DzwlClService extends BaseService<ClDzwlCl,String>{
    ApiResponse<String> saveEntity(ClDzwlCl entity);
}
