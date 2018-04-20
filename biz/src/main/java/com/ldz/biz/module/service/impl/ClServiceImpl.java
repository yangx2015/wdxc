package com.ldz.biz.module.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.ClClModel;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.JsyService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;
@Service
public class ClServiceImpl extends BaseServiceImpl<ClCl,String> implements ClService{
    @Autowired
    private ClClMapper entityMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClService clService;
    @Autowired
    private JsyService jsyService;

    @Override
    protected Mapper<ClCl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClCl.class;
    }

    @Override
    public ClCl findByOrgCode(String code) {
        List<ClCl> jgs = findEq(ClCl.InnerColumn.clId,code);
        if (jgs.size() == 0)return null;
        return jgs.get(0);
    }

    @Override
    public List<ClCl> getOrgCarList(String orgCode) {
        List<ClCl> carList = clService.findEq(ClCl.InnerColumn.jgdm,orgCode);
        return carList;
    }

    @Override
    public ApiResponse<String> saveEntity( ClCl entity) {
    	 SysYh user = getCurrentUser();
         SysJg org = jgService.findByOrgCode(user.getJgdm());
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setClId(genId());
         entity.setCjsj(now);
         entity.setJgdm(user.getJgdm());
         entity.setJgmc(org.getJgmc());
         if (StringUtils.isNotBlank(entity.getSjId())){
             ClJsy jsy = jsyService.findById(entity.getSjId());
             entity.setSjxm(jsy.getXm());
         }
         save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String> updateEntity(ClCl entity) {
		  ClCl findById = findById(entity.getClId());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}


    @Override
    public ApiResponse<List<Map<String,Object>>> getVehicleTypeStatistics(){
//        1、定义初始变量
        ApiResponse<List<Map<String,Object>>> result = new ApiResponse<List<Map<String,Object>>>();
        List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>();
        String jgdm="";//机构ID
//        2、查询车辆信息
        List<ClClModel> clClList=entityMapper.getVehicleTypeStatistics(jgdm);

        String cx="";
        List<Map<String,Object>> childrenList=null;
        Map<String, Object> cxMap=null;//车型map
        if(clClList!=null&& clClList.size()>0){
            for(ClClModel l:clClList){
                if(!StringUtils.equals(cx,l.getCx())){
                    if(childrenList!=null){
                        cxMap.put("children",childrenList);
                        retList.add(cxMap);
                    }
                    childrenList=new ArrayList<Map<String,Object>>();
                    cxMap=new HashMap<String, Object>();//车型map
                    cx=l.getCx();
                    cxMap.put("expand",true);
                    cxMap.put("title",l.getCxZtMc());
                }

                Map<String, Object> mapCenMap=new HashMap<String, Object>();//坐标map
                mapCenMap.put("lng",l.getBdJd());
                mapCenMap.put("lat",l.getBdWd());

                Map<String, Object> childrenMap=new HashMap<String, Object>();//车辆map
                childrenMap.put("mapCen",mapCenMap);
                childrenMap.put("clid",l.getClId());
                childrenMap.put("cx",l.getCx());
                childrenMap.put("title",l.getCph());
                childrenList.add(childrenMap);
            }
        }
        if(cxMap!=null&&childrenList!=null){
            cxMap.put("children",childrenList);
            retList.add(cxMap);
        }
        result.setResult(retList);
        return result;
    }

}
