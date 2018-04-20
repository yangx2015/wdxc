package com.ldz.biz.module.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    	ClZdgl findById = findById(entity.getZdbh());
    	if (findById!=null) {
			return ApiResponse.fail("终端编号已存在");
		}
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
}
