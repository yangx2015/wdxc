package com.ldz.wechat.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.wechat.bean.ClClyxjlModel;
import com.ldz.wechat.bean.DdClModel;
import com.ldz.wechat.mapper.ClZdMapper;
import com.ldz.wechat.model.ClClyxjl;
import com.ldz.wechat.model.ClXlzd;
import com.ldz.wechat.model.ClZd;
import com.ldz.wechat.service.ClyxjlService;
import com.ldz.wechat.service.XlzdService;
import com.ldz.wechat.service.ZdService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ZdServiceImpl extends BaseServiceImpl<ClZd,String> implements ZdService{
    @Autowired
    private ClZdMapper entityMapper;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClyxjlService clyxjlService;

    @Override
    protected Mapper<ClZd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZd entity) {
    	 SysYh user = getCurrentUser();
         SysJg org = jgService.findByOrgCode(user.getJgdm());
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setCjsj(now);
         entity.setId(genId());
         entity.setJgdm(user.getJgdm());
         entity.setJgmc(org.getJgmc());
         save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public List<ClZd> getByXlId(String xlId) {
        return getByXlIds(Collections.singletonList(xlId));
    }

    @Override
    public List<ClZd> getByXlIds(List<String> xlIds) {
        List<ClXlzd> xlzds = xlzdService.findIn(ClXlzd.InnerColumn.xlId,xlIds);
        if (xlzds.size() == 0)return new ArrayList<>();
        List<String> routeIds = xlzds.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
//        List<ClZd> stations = findIn(ClZd.InnerColumn.id,routeIds);
//        Map<String,String> zdXlIdsMap = xlzds.stream().collect(Collectors.toMap(ClXlzd::getZdId,ClXlzd::getXlId));
//        for (ClZd station : stations) {
//            station.setXlId(zdXlIdsMap.get(station.getId()));
//        }
        return findIn(ClZd.InnerColumn.id,routeIds);
    }

    @Override
    public void setStationOrder(ClZd station) {
        if (StringUtils.isEmpty(station.getId()) || StringUtils.isEmpty(station.getXlId())){
            return;
        }
        SimpleCondition condition = new SimpleCondition(ClXlzd.class);
        condition.eq(ClXlzd.InnerColumn.zdId,station.getId());
        condition.eq(ClXlzd.InnerColumn.xlId,station.getXlId());
        List<ClXlzd> xlzds = xlzdService.findByCondition(condition);
        if (xlzds.size() == 0)return;
        station.setRouteOrder(xlzds.get(0).getXh());
    }

    @Override
    public ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId) {
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

    @Override
	public ApiResponse<String> updateEntity(ClZd entity) {
		    ClZd findById = findById(entity.getId());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}
}