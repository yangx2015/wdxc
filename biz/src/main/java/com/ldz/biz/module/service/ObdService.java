package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.ObdMessageBean;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ObdService extends BaseService<ObdMessageBean,String>{

    ApiResponse<Object> getObdTimely(String obdId);
}
