package com.ldz.biz.module.service.impl;

import java.util.Date;

import com.ldz.sys.base.LimitedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClSpkMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClSpk;
import com.ldz.biz.module.service.SpkService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SpkServiceImpl extends BaseServiceImpl<ClSpk,String> implements SpkService{
    @Autowired
    private ClSpkMapper entityMapper;
     
    @Value("${spk.url}")
	private String path;
    
    @Autowired
    private ClClMapper clclmapper;

    @Override
    protected Mapper<ClSpk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClSpk.class;
    }

    @Override
    public boolean fillCondition(LimitedCondition condition){
        return true;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSpk entity) {
        SysYh user = getCurrentUser();
        Date now = new Date();
        entity.setCjr(getOperateUser());
        entity.setCjsj(now);
        entity.setId(genId());
        entity.setJgdm(user.getJgdm());

        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ClSpk entity) {
        ClSpk oldRecord = findById(entity.getId());
        RuntimeCheck.ifNull(oldRecord,"未找到记录");
        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        return ApiResponse.success();
    }


	@Override
	public ApiResponse<String> saveSpk(GpsInfo entity) {
		ClSpk clSpk = new ClSpk();
		
        ClCl selectOne=new ClCl();
        selectOne.setZdbh(entity.getDeviceId());
		//通过终端编号找到对应车辆信息
		ClCl clinfo = clclmapper.selectOne(selectOne);
		
		clSpk.setDz(entity.getFileLocalPath());//本地地址 必传
		clSpk.setUrl(path+entity.getFilePath());//url   必传
		clSpk.setWjm(entity.getFileRealName());//文件名称  必传
		clSpk.setZdbh(entity.getDeviceId());//设备id  必传
		clSpk.setSplx(entity.getFilePostfix());//文件后缀 ->视屏类型 必传

		clSpk.setCjsj(new Date()); //创建时间
		clSpk.setClId(clinfo.getClId());//车辆id
		clSpk.setCph(clinfo.getCph());//车牌号
		clSpk.setId(genId());//自增长id
		clSpk.setJgdm(clinfo.getJgdm());//机构代码
		clSpk.setJgmc(clinfo.getJgmc());//机构名称


		save(clSpk);

		return ApiResponse.success();
	}
}
