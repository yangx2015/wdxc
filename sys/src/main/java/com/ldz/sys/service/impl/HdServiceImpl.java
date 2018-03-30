package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.mapper.SysHdyxMapper;
import com.ldz.sys.mapper.SysYxhdwjMapper;
import com.ldz.sys.model.SysHdyx;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.model.SysYxhdwj;
import com.ldz.sys.service.HdService;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.ContextUtil;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

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
    private SysYxhdwjMapper yxhdwjMapper;
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
//        RuntimeCheck.ifBlank(entity.getJgdm(),"请选择机构");
//        SysJg org = jgService.findByOrgCode(entity.getJgdm());
//        RuntimeCheck.ifNull(org,"机构不存在");

        // 执行新增操作
//        SysYh yh = ContextUtil.getCurrentUser();
//        entity.setCjr(yh.getYhid());
        entity.setCjsj(new Date());
//        entity.setJgdm(yh.getJgdm());
        entity.setZt(Dict.CommonStatus.VALID.getCode());
        saveFiles(entity);
        return ApiResponse.success();
    }

    private void saveFiles(SysHdyx hdyx){
        if (hdyx == null)return;
        List<String> files = hdyx.getFilePaths();
        if (files == null || files.size() == 0)return;
        Date now = new Date();
        String operator = getOperateUser();
        for (String file : files) {
            int index = file.lastIndexOf(".");
            if (index <= 0)continue;
            String fileType = file.substring(index+1);
            SysYxhdwj yxhdwj = new SysYxhdwj();
            yxhdwj.setCjr(operator);
            yxhdwj.setCjsj(now);
            yxhdwj.setHdId(hdyx.getHdId());
            yxhdwj.setWjlx(fileType);
            yxhdwj.setId(genId());
            yxhdwj.setWjlj(file);
            yxhdwj.setWllj(file);
            yxhdwjMapper.insertSelective(yxhdwj);
        }
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
