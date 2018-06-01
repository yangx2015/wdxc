package com.ldz.biz.module.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ldz.sys.base.LimitedCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;

import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ZdglServiceImpl extends BaseServiceImpl<ClZdgl,String> implements ZdglService{
    @Autowired
    private ClZdglMapper entityMapper;
    @Autowired
    private ClService clService;

    @Override
    protected Mapper<ClZdgl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZdgl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZdgl entity) {
    	ClZdgl findById = findById(entity.getZdbh());
    	if (findById!=null) {
			return ApiResponse.fail("终端编号已存在");
		}
    	//默认设置终端在线状态为离线
    	entity.setZxzt("20");
    	
    	//默认设备碰撞灵敏度
    	entity.setPzlmd("10");
    	//默认设备急加速灵敏度
    	entity.setJslmd("2");
    
    	//默认设备视屏上传模式
    	entity.setSpscms("20");
    	//默认设备的心跳
    	entity.setGpsxt("10");
    	
        entity.setCjr(getOperateUser());
        entity.setCjsj(new Date());
        save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public void insetAndUpdate(ClZdgl entity) {
		
	 boolean flag= ifExists("zdbh",entity.getZdbh());
		if (flag==true) {
			update(entity);
		}else {
			save(entity);
		}
	}

    @Override
    public ApiResponse<String> updateEntity(ClZdgl entity) {
//    	ClZdgl findById = findById(entity.getZdbh());
//    	if (findById!=null) {
//			return ApiResponse.fail("终端编号已存在");
//		}
        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        return ApiResponse.success();
    }

    public ApiResponse<List<ClZdgl>> unboundList(){
//        1、定义初始变量
        ApiResponse<List<ClZdgl>> result = new ApiResponse<List<ClZdgl>>();
        List<ClZdgl> list=entityMapper.getUnboundList();
        result.setResult(list);
        return result;
    }


    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        String cphLike = getRequestParamterAsString("cphLike");
        if (StringUtils.isNotEmpty(cphLike)){
            List<ClCl> carList = clService.findLike(ClCl.InnerColumn.cph,cphLike);
            if (CollectionUtils.isEmpty(carList)){
                return false;
            }
            List<String> zdbhs = carList.stream().map(ClCl::getZdbh).collect(Collectors.toList());
            condition.in(ClZdgl.InnerColumn.zdbh,zdbhs);
        }
        return true;
    }
    /**
     * 自定义分页的对象
     * @param resultPage
     */
    protected void afterPager(PageInfo<ClZdgl> resultPage){
        if(resultPage!=null){
            List<ClZdgl> list=resultPage.getList();
            if(list!=null&&list.size()>0){
                List<String> listIds = list.stream().map(ClZdgl::getZdbh).collect(Collectors.toList());
                List<ClCl>clList=clService.findIn(ClCl.InnerColumn.zdbh,listIds);
                Map<String,ClZdgl> zdbhMap = list.stream().collect(Collectors.toMap(ClZdgl::getZdbh, p->p));
                if(clList!=null&&clList.size()>0){
                    for(ClCl cl:clList){
                        ClZdgl zdbh=zdbhMap.get(cl.getZdbh());
                        if (zdbh == null)continue;

                        zdbh.setCl(cl);
                        zdbh.setCph(cl.getCph());
                    }
                }
            }
        }
    }

	@Override
	public ApiResponse<Map<String, Integer>> getzdxc() {
		int dianhuo =0;
		int xihuo =0;
		int lixian=0;
		List<ClZdgl> selectAll = entityMapper.selectAll();
	       RuntimeCheck.ifEmpty(selectAll, "暂无设备");
		for (ClZdgl clZdgl : selectAll) {
			//点火
			if (StringUtils.equals(clZdgl.getZxzt(), "00")) {
				dianhuo++;
			}
			//熄火
			if (StringUtils.equals(clZdgl.getZxzt(), "10")) {
				xihuo++;
			}
			//离线
			if (StringUtils.equals(clZdgl.getZxzt(), "20")) {
				lixian++;
			}
			
		}
		Map<String,Integer> map = new HashMap<>();
		map.put("设备总数", selectAll.size());
		map.put("设备在线数量", dianhuo);
		map.put("设备熄火数量", xihuo);
		map.put("设备离线数量", lixian);
		ApiResponse<Map<String, Integer>>  apiResponse = new ApiResponse<>();
		apiResponse.setResult(map);
		
		return apiResponse;
	}
}
