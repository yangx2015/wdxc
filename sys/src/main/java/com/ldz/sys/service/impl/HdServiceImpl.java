package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.mapper.SysHdyxMapper;
import com.ldz.sys.model.SysHdyx;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.HdService;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.ContextUtil;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2018/2/26
 */
@Service
public class HdServiceImpl extends BaseServiceImpl<SysHdyx,String> implements HdService{
    @Autowired
    private SysHdyxMapper hdyxMapper;
    @Autowired
    private JgService jgService;
    @Override
    protected Mapper<SysHdyx> getBaseMapper() {
        return hdyxMapper;
    }


    @Override
    public boolean fillCondition(LimitedCondition condition){

        return true;
    }

    /**
     * 新增活动
     *
     * @param entity 参数
     * @return 操作结果
     */
    @Override
    public ApiResponse<String> saveEntity(SysHdyx entity) {
        // 参数检查
        RuntimeCheck.ifBlank(entity.getHdbt(),"请输入活动标题");
        RuntimeCheck.ifBlank(entity.getHdlx(),"请选择活动类型");
        RuntimeCheck.ifBlank(entity.getJgdm(),"请选择机构");
        SysJg org = jgService.findByOrgCode(entity.getJgdm());
        RuntimeCheck.ifNull(org,"机构不存在");

        // 执行新增操作
        SysHdyx activity = new SysHdyx();
        SysYh yh = ContextUtil.getCurrentUser();
        activity.setCjr(yh.getYhid());
        activity.setCjsj(new Date());
        activity.setJgdm(yh.getJgdm());
        activity.setZt(Dict.CommonStatus.VALID.getCode());
        return ApiResponse.success();
    }

    /**
     * 编辑活动
     *
     * @param entity 参数
     * @return 操作结果
     */
    @Override
    public ApiResponse<String> updateEntity(SysHdyx entity) {
        // 参数检查
        RuntimeCheck.ifBlank(entity.getHdId(),"请选择活动");
        SysHdyx hd = findById(entity.getHdId());
        RuntimeCheck.ifNull(hd,"活动不存在");

        // 修改数据
        hdyxMapper.updateByPrimaryKeySelective(entity);
        return ApiResponse.success();
    }
}
