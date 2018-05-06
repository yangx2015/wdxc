package com.ldz.znzp.service;

import com.ldz.znzp.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.znzp.model.ClZnzp;

public interface ZnzpService extends BaseService<ClZnzp,String>{
    ApiResponse<String> saveEntity(ClZnzp entity);

    ApiResponse<String> updateMedia(String jgdm);
}
