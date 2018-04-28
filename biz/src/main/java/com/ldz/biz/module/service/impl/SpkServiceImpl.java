package com.ldz.biz.module.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClSpkMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClSpk;
import com.ldz.biz.module.service.SpkService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.redis.RedisTemplateUtil;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SpkServiceImpl extends BaseServiceImpl<ClSpk,String> implements SpkService{
	
	
	private static final Logger log = LoggerFactory.getLogger(SpkServiceImpl.class);

    @Autowired
    private ClSpkMapper entityMapper;
    
	@Autowired
	private SimpMessagingTemplate websocket;
	@Autowired
	private RedisTemplateUtil redisutils;
     
  /*  @Value("${spk.url}")
	private String path;*/
    
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
    protected void afterPager(PageInfo<ClSpk> resultPage){
    	
    	List<ClSpk> list = resultPage.getList();
    	
    	if (CollectionUtils.isNotEmpty(list)) {
			
    		list.stream().forEach(s->s.setDz(null));
		}
    	
        return;
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
		
		log.info("收到的文件模型:"+entity);
		ClSpk clSpk = new ClSpk();
        ClCl selectOne=new ClCl();
        selectOne.setZdbh(entity.getDeviceId());
		//通过终端编号找到对应车辆信息
		ClCl clinfo = clclmapper.selectOne(selectOne);
		
		clSpk.setDz(entity.getFileLocalPath());//本地地址 必传
		
		clSpk.setUrl(entity.getFilePath());//url   必传
		
		clSpk.setWjm(entity.getFileRealName());//文件名称  必传
		clSpk.setZdbh(entity.getDeviceId());//设备id  必传
		clSpk.setSplx(entity.getEventType());
		clSpk.setCjsj(new Date()); //创建时间
		clSpk.setClId(clinfo.getClId());//车辆id
		clSpk.setCph(clinfo.getCph());//车牌号
		clSpk.setId(genId());//自增长id
		clSpk.setJgdm(clinfo.getJgdm());//机构代码
		clSpk.setJgmc(clinfo.getJgmc());//机构名称
		//这个视屏的任务id
		if (StringUtils.isNotEmpty(entity.getTaskId())) {
			clSpk.setBj(entity.getTaskId());
		}

		save(clSpk);

		if (StringUtils.isNotEmpty(entity.getTaskId())) {
		BoundListOperations<Object, Object> boundListOps = redisutils.boundListOps("BJ"+entity.getDeviceId());
		String index = (String) boundListOps.index(0);
		if (StringUtils.isNotEmpty(index)) {
			
			for (int i = 0; i < boundListOps.size(); i++) {
				String index2 = (String) boundListOps.index(i);
				if (StringUtils.equals(entity.getTaskId(), index2)) {
					websocket.convertAndSend("/topic/sendhbsp", clSpk);
					log.info("视屏合并成功,并推送至前端"+clSpk);
					boundListOps.remove(i, index2);
				}
				
			}
			
		}
		
	}
		
		
		return ApiResponse.success();
	}
}
