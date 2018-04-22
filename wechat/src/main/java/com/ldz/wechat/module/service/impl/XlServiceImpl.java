package com.ldz.wechat.module.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.bean.ClClyxjlModel;
import com.ldz.wechat.module.bean.DdClModel;
import com.ldz.wechat.module.mapper.ClXlMapper;
import com.ldz.wechat.module.model.ClClyxjl;
import com.ldz.wechat.module.model.ClXl;
import com.ldz.wechat.module.service.ClyxjlService;
import com.ldz.wechat.module.service.XlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service("wxXlService")
public class XlServiceImpl extends BaseServiceImpl<ClXl, String> implements XlService {
	@Autowired
	private ClXlMapper entityMapper;

	@Autowired
	private ClyxjlService clyxjlService;

	@Override
	protected Mapper<ClXl> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClXl.class;
	}

   public List<ClXl> getAll(){
	   return findAll();
   }

	public ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId){
		List<DdClModel> clZds=entityMapper.getBySiteVehicleList(xlId);
		if(clZds!=null){
			SimpleCondition condition = new SimpleCondition(ClClyxjl.class);
			condition.eq(ClClyxjl.InnerColumn.xlId,xlId);
			condition.setOrderByClause(ClClyxjl.InnerColumn.cjsj.asc());
			List<ClClyxjl> xlzds = clyxjlService.findByCondition(condition);
			if(xlzds!=null&&xlzds.size()>0){
				for(DdClModel clZd:clZds){
					//到站车辆
					long entryCount=0;
					//出站车辆
					long exportCount=0;
					Iterator<ClClyxjl> iter = xlzds.iterator();
					if(clZd.getVehicleCount()>0){
						List<ClClyxjlModel> list=new ArrayList<ClClyxjlModel>();
						while (iter.hasNext()) {
							ClClyxjl item = iter.next();
							//判断车辆是否在当前站点
							if(StringUtils.equals(item.getZdbh(),clZd.getZdId())){
								ClClyxjlModel model=new ClClyxjlModel();
								model.setCphm(item.getCphm());//车牌号码
								model.setCjsj(item.getCjsj());//创建时间
								//车辆出站状态  0未出站 1、已出站
								String clczzt="1";//
								if(item.getZdjl()!=null&& clZd.getVehicleScope()<item.getZdjl()){
									clczzt="0";
									entryCount++;
								}else{
									exportCount++;
								}
								model.setClczzt(clczzt);//车辆出站状态  0未出站
								model.setCjsj(item.getCjsj());
								model.setZdjl(item.getZdjl());
								list.add(model);
								iter.remove();
							}
						}
						clZd.setVehicleList(list);
					}
					clZd.setEntryCount(entryCount);
					clZd.setExportCount(exportCount);
				}
			}
		}
		ApiResponse<List<DdClModel>> apiResponse=new ApiResponse<List<DdClModel>>();
		apiResponse.setResult(clZds);
		return apiResponse;
	}

}