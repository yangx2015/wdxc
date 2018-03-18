package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.mapper.*;
import com.ldz.sys.model.*;
import com.ldz.sys.service.FwService;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GnServiceImpl extends BaseServiceImpl<SysGn, String> implements GnService {

    @Autowired
    private SysYhJsMapper yhJsMapper;
    @Autowired
    private SysJsGnMapper jsGnMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private SysPtjgMapper ptjgMapper;
    @Autowired
    private SysJgsqlbMapper jgsqMapper;
    @Autowired
    private SysFwgnMapper gnMapper;
    @Autowired
    private FwService fwService;
    @Override
    protected Mapper<SysGn> getBaseMapper() {
        return gnMapper;
    }

    @Override
    public ApiResponse<String> saveEntity(SysGn entity) {
        entity.setCjsj(new Date());
        gnMapper.insert(entity);
        return ApiResponse.success();
    }




    /**
     * 查找某个服务的所有功能
     *
     * @param serviceCode 服务代码
     * @return 功能列表
     */
    @Override
    public List<SysGn> findByServiceCode(String serviceCode) {
        RuntimeCheck.ifBlank(serviceCode,"请输入服务代码");
        return findEq(SysGn.InnerColumn.fwdm,serviceCode);
    }

    /**
     * 设置机构功能
     *
     * @param orgCode      机构代码
     * @param functionCode 功能代码
     * @return 执行结果
     */
    @Override
    public ApiResponse<String> setOrgFunction(String orgCode, List<String> functionCode) {
        SysYh user = getCurrentUser();
        RuntimeCheck.ifBlank(orgCode,"请选择机构");
        SysJg org = jgService.findByOrgCode(orgCode);
        RuntimeCheck.ifNull(org,"机构不存在");

        // 删除旧数据
        SimpleCondition condition = new SimpleCondition(SysJg.class);
        condition.eq(SysJg.InnerColumn.jgdm,orgCode);
        ptjgMapper.deleteByExample(condition);

        // 插入新数据
        List<SysGn> functions = findIn(SysGn.InnerColumn.gndm,functionCode);
        Date now = new Date();
        for (SysGn function : functions) {
            SysJgsq jgsq = new SysJgsq();
            jgsq.setCjr(user.getYhid());
            jgsq.setCjsj(now);
            jgsq.setFwdm(function.getFwdm());
            jgsq.setGndm(function.getGndm());
            jgsq.setJgdm(orgCode);
            jgsqMapper.insertSelective(jgsq);
        }
        return ApiResponse.success();
    }

    /**
     * 查询机构拥有的功能列表
     *
     * @param gndm 机构代码
     * @return 功能列表
     */
    @Override
    public List<SysGn> findByGndm(String gndm) {
        return findEq(SysGn.InnerColumn.gndm,gndm);
    }

    @Override
    public List<SysGn> getUserFunctions(SysYh user) {
        List<String> functionCodes = getUserFunctionCodes(user);
        return findIn(SysGn.InnerColumn.gndm,functionCodes);
    }

    @Override
    public List<SysGn> getOrgFunctions(String orgCode) {
        SimpleCondition condition = new SimpleCondition(SysJgsq.class);
        condition.eq(SysJgsq.InnerColumn.jgdm,orgCode);
        return gnMapper.selectByExample(condition);
    }

    @Override
    public List<String> getUserFunctionCodes(SysYh user) {
        // 获取用户角色
        SimpleCondition condition = new SimpleCondition(SysYhJs.class);
        condition.eq(SysYhJs.InnerColumn.yhId,user.getYhid());
        List<SysYhJs> yhJs = yhJsMapper.selectByExample(condition);
        if (yhJs.size() == 0)  return new ArrayList<>();

        // 获取角色功能
        List<String> jsdms = yhJs.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
        return getRolesFunctionCodes(jsdms);
    }

    @Override
    public List<String> getOrgFunctionCodes(String orgCode) {
        List<SysGn> functions = getOrgFunctions(orgCode);
        return functions.stream().map(SysGn::getGndm).collect(Collectors.toList());
    }

    @Override
    public List<SysGn> getRolesFunctions(List<String> jsdms) {
        List<String> functionCodes = getRolesFunctionCodes(jsdms);
        return findIn(SysGn.InnerColumn.gndm,functionCodes);
    }

    @Override
    public List<String> getRolesFunctionCodes(List<String> jsdms) {
        SimpleCondition condition = new SimpleCondition(SysJsGn.class);
        condition.in(SysJsGn.InnerColumn.jsdm,jsdms);
        List<SysJsGn> roleFunctions = jsGnMapper.selectByExample(condition);
        return roleFunctions.stream().map(SysJsGn::getGndm).collect(Collectors.toList());
    }

    private List<SysFw> getPermissionTree(List<SysFw> services,List<SysGn> functions){
        Map<String,SysFw> serviceMap = services.stream().collect(Collectors.toMap(SysFw::getFwdm,p->p));
        Map<String,SysGn> functionMap = functions.stream().collect(Collectors.toMap(SysGn::getGndm, p->p));
        List<String> serviceCodes = new ArrayList<>();
        for (SysGn function : functions) {
            String serviceCode = function.getFwdm();
            if (StringUtils.isEmpty(serviceCode))continue;
            if (serviceMap.containsKey(serviceCode))continue;
            serviceCodes.add(serviceCode);
        }
        if (serviceCodes.size() != 0){
            List<SysFw> addServices = fwService.findIn(SysFw.InnerColumn.fwdm,serviceCodes);
            services.addAll(addServices);
        }
        for (SysGn function : functions) {
            String fatherCode = function.getFjd();
            // 如果没有父节点，则代码这是个一级功能
            if (StringUtils.isEmpty(fatherCode)){
                SysFw father = serviceMap.get(function.getFwdm());
                if (father == null)continue;
                if (father.getFunctions() == null){
                    List<SysGn> children = new ArrayList<>();
                    children.add(function);
                    father.setFunctions(children);
                }else{
                    father.getFunctions().add(function);
                }
            }else{
                SysGn father = functionMap.get(fatherCode);
                if (father == null)continue;
                if (father.getChildren() == null){
                    List<SysGn> children = new ArrayList<>();
                    children.add(function);
                    father.setChildren(children);
                }else{
                    father.getChildren().add(function);
                }
            }
        }
        return services;
    }

    @Override
    public List<SysFw> getAllPermissionTree() {
        return getPermissionTree(fwService.findAll(),gnMapper.selectAll());
    }

    @Override
    public List<SysFw> getOrgPermissionTree(String jgdm) {
        return getPermissionTree(fwService.findByJgdm(jgdm),getOrgFunctions(jgdm));
    }

    @Override
    public List<SysFw> getUserPermissionTree(SysYh user) {
        List<SysGn> functions = getUserFunctions(user);
        List<String> serviceCodes = functions.stream().map(SysGn::getFwdm).collect(Collectors.toList());
        List<SysFw> services = fwService.findIn(SysFw.InnerColumn.fwdm,serviceCodes);
        return getPermissionTree(services,functions);
    }


}
