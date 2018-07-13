package com.ldz.biz.module.service;

import com.ldz.biz.module.model.Clyy;
import com.ldz.sys.base.BaseService;

import java.util.List;

public interface ClYyService extends BaseService<Clyy,String> {

    void saveBatch(List<Clyy> clyys);

}
