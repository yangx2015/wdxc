package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.mapper.SysPtjgMapper;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.OrgUtil;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * auther chenwei
 * create at 2018/2/26
 */
@Service
public class JgServiceImpl extends BaseServiceImpl<SysJg,String> implements JgService{
    @Autowired
    private SysPtjgMapper ptjgMapper;
    @Autowired
    private DataSource dataSource;

    @Override
    protected Mapper<SysJg> getBaseMapper() {
        return ptjgMapper;
    }

    @Override
    public List<SysJg> getOrgTree(List<SysJg> orgList) {
        Map<String,SysJg> orgMap = orgList.stream().collect(Collectors.toMap(SysJg::getJgdm,p->p));
        List<SysJg> root = new ArrayList<>();
        for (SysJg org : orgList) {
            String fatherCode = org.getFjgdm();
            if (StringUtils.isEmpty(fatherCode)){
                root.add(org);
            }else{
                SysJg father = orgMap.get(fatherCode);
                if (father == null)continue;
                father.getChildren().add(org);
            }
        }
        return root;
    }

    @Override
    public List<SysJg> getOrgTreeByOrgCodes(List<String> orgCodes) {
        List<SysJg> orgs = findIn(SysJg.InnerColumn.jgdm,orgCodes);
        return getOrgTree(orgs);
    }

    /**
     * 根据机构层级获取机构列表
     *
     * @param level 机构层级
     * @return 机构列表
     */
    @Override
    public List<SysJg> findByLevel(Integer level) {
        RuntimeCheck.ifNull(level,"机构层级不能为空");
        return findEq(SysJg.InnerColumn.jgdj,level);
    }

    /**
     * 新增机构
     *
     * @param entity 参数
     * @return 操作结果
     */
    @Override
    public ApiResponse<String> saveEntity(SysJg entity) {
        RuntimeCheck.ifBlank(entity.getJglx(),"机构类型不能为空");
        Dict.OrgType orgType = Dict.OrgType.toEnum(entity.getJglx());
        RuntimeCheck.ifNull(orgType,"机构类型不存在");

        entity.setCjr(getOperateUser());
        entity.setCjsj(new Date());
        entity.setZt(Dict.CommonStatus.VALID.getCode());
        String orgCode = genOrgCode(entity);
        entity.setJgdm(orgCode);
        if (StringUtils.isEmpty(entity.getFjgdm())){ // 一级机构
            entity.setJgdj(1);
        }else{
            String fatherCode = entity.getFjgdm();
            int fatherLevel = OrgUtil.getLevel(fatherCode);
            entity.setJgdj(fatherLevel + 1);
        }
        ptjgMapper.insertSelective(entity);
        return ApiResponse.success();
    }

    private String genOrgCode(SysJg org){
        String fatherCode = org.getFjgdm();
        if (fatherCode == null) fatherCode = "";
        SimpleCondition condition = new SimpleCondition(SysJg.class);
        condition.eq(SysJg.InnerColumn.jgdj,org.getJgdj());
        condition.setOrderByClause(SysJg.InnerColumn.jgdm.desc());
        List<SysJg> orgs = findByCondition(condition);
        if (orgs.size() == 0)return fatherCode + "001";
        String maxJgdm = orgs.get(0).getJgdm();
        int max = Integer.parseInt(maxJgdm);
        return fatherCode + StringUtils.leftPad(""+max,3,"0");
    }

    @Override
    public SysJg findByOrgCode(String orgCode) {
        List<SysJg> jgs = findEq(SysJg.InnerColumn.jgdm,orgCode);
        if (jgs.size() == 0)return null;
        return jgs.get(0);
    }

    /**
     * 获取机构下面的所有子机构
     *
     * @param orgCode
     * @return
     */
    @Override
    public List<SysJg> findAllSubOrg(String orgCode) {
        return ptjgMapper.selectAll();
//        String like = "" + orgCode + "%";
//        String sql = "select * from sys_hdyx where jgdm like "+ like;
//
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        return jdbcTemplate.queryForList(sql,SysJg.class);
    }
}
