package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.bean.Menu;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.mapper.*;
import com.ldz.sys.model.*;
import com.ldz.sys.service.FwService;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.JsService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GnServiceImpl extends BaseServiceImpl<SysGn, String> implements GnService {

    @Autowired
    private SysYhJsMapper yhJsMapper;
    @Autowired
    private SysJsGnMapper jsGnMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private JsService jsService;
    @Autowired
    private GnService gnService;
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
    	SysGn selectByPrimaryKey = gnMapper.selectByPrimaryKey(entity.getGndm());
    	if(selectByPrimaryKey!=null) {
    		return ApiResponse.fail("功能代码已存在");
    	}
    	 SysYh user = getCurrentUser();
        entity.setCjr(user.getYhid());
        entity.setCjsj(new Date());
        gnMapper.insert(entity);
        return ApiResponse.success();
    }

    @Override
    public boolean fillCondition(LimitedCondition condition){
        condition.setOrderByClause("px asc");
        return true;
    }


    @Override
    public ApiResponse<String> updateEntity(SysGn gn) {
    	gn.setXgr(getOperateUser());
    	gn.setXgsj(new Date());
    	SysGn gndm = gnMapper.selectByPrimaryKey(gn.getGndm());
    	if (gndm==null) {
			
    		save(gn);
		}else {
			update(gn);
		}
        

        
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> setRoleFunctions(String jsdm, List<String> gndms) {
        RuntimeCheck.ifBlank(jsdm,"角色代码不能为空");
        List<SysJs> roles = jsService.findEq(SysJs.InnerColumn.jsId,jsdm);
        RuntimeCheck.ifTrue(roles.size() == 0,"未找到记录");

        SimpleCondition condition = new SimpleCondition(SysJsGn.class);
        condition.eq(SysJsGn.InnerColumn.jsdm,jsdm);
        jsGnMapper.deleteByExample(condition);

        if (gndms == null || gndms.size() == 0)return ApiResponse.success();
        List<SysGn> functionList = findIn(SysGn.InnerColumn.gndm,gndms);

        String createUser = getOperateUser();
        Date now = new Date();
        List<SysJsGn> jsGns = new ArrayList<>();
        for (SysGn function : functionList) {
            SysJsGn jsGn = new SysJsGn();
            jsGn.setGndm(function.getGndm());
            jsGn.setJsdm(jsdm);
            jsGn.setCjr(createUser);
            jsGn.setCjsj(now);
            jsGn.setFwdm(function.getFwdm());
            jsGn.setFgndm(function.getFjd());
            jsGn.setId(genId());
            jsGns.add(jsGn);
        }
        jsGnMapper.insertList(jsGns);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<SysGn>> getRoleFunctions(String jsdm) {
        SimpleCondition condition = new SimpleCondition(SysJsGn.class);
        condition.eq(SysJsGn.InnerColumn.jsdm,jsdm);
        List<SysJsGn> roleFunctions = jsGnMapper.selectByExample(condition);
        if (roleFunctions.size() == 0){
            List<SysGn> gnList = new ArrayList<>();
            return ApiResponse.success(gnList);
        }
        List<String> gndms = roleFunctions.stream().map(SysJsGn::getGndm).collect(Collectors.toList());
        List<SysGn> functions = gnService.findIn(SysGn.InnerColumn.gndm,gndms);
        return ApiResponse.success(functions);
    }

    private List<Menu> convertToMenus(List<SysGn> functions){
        List<Menu> menuList = new ArrayList<>();
        for (SysGn function : functions) {
            Menu menu = new Menu(function);
            menuList.add(menu);
        }
        return menuList;
    }


    @Override
    public List<Menu> getMenuTree(SysYh user) {
        log.debug("getMenuTree");
        List<SysGn> functions = getUserFunctions(user);
        if (functions == null || functions.size() == 0)return new ArrayList<>();
        log.debug("function size:"+functions.size());
        functions.sort(Comparator.comparing(SysGn::getPx));
        List<Menu> menuList = convertToMenus(functions);
        return buildMenuTree(menuList);
    }

    @Override
    public ApiResponse<String> initMenu(List<Menu> menus) {
        List<SysGn> functions = new ArrayList<>();
        addToMenuList(menus,functions);
        for (SysGn function : functions) {
            SysGn exist = gnMapper.selectByPrimaryKey(function.getGndm());
            if (exist == null){
                gnService.save(function);
            }else{
                gnService.update(function);
            }
        }
        return ApiResponse.success();
    }

    private void addToMenuList(List<Menu> menuList,List<SysGn> functionList){
        List<SysGn> functions = convertToFunctionList(menuList);
        functionList.addAll(functions);
        for (Menu menu : menuList) {
            if (menu.getChildren() != null){
                addToMenuList(menu.getChildren(),functionList);
            }
        }
    }

    private List<SysGn> convertToFunctionList(List<Menu> menus){
        List<SysGn> list = new ArrayList<>();
        String creartor = "初始导入";
        Date now = new Date();
        for (Menu menu : menus) {
            SysGn function = new SysGn();
            function.setFwdm("1");
            function.setZt("00");
            function.setCjr(creartor);
            function.setCjsj(now);
            function.setFjd(menu.getPid());
            function.setGndm(menu.getName());
            function.setUrl(menu.getName());
            function.setGnmc(menu.getTitle());
            function.setTb(menu.getIcon());
            list.add(function);
        }
        return list;
    }
    private List<Menu> buildMenuTree(List<Menu> menuList){
        Map<String,Menu> menuIdMap = menuList.stream().collect(Collectors.toMap(Menu::getId,p->p));
        List<Menu> root = new ArrayList<>();
        for (Menu menu : menuList) {
            if (StringUtils.isEmpty(menu.getPid())){
                root.add(menu);
                continue;
            }
            Menu father = menuIdMap.get(menu.getPid());
            if (father == null)continue;
            if (father.getChildren() == null){
                List<Menu> children = new ArrayList<>();
                children.add(menu);
                father.setChildren(children);
            }else{
                father.getChildren().add(menu);
            }
        }
        return root;
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
        if ("su".equals(user.getLx())){
            return findAll();
        }
        List<String> functionCodes = getUserFunctionCodes(user);
        if (functionCodes.size() == 0)return new ArrayList<>();
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
        if (jsdms == null || jsdms.size() == 0)return new ArrayList<>();
        List<String> functionCodes = getRolesFunctionCodes(jsdms);
        if (functionCodes.size() == 0)return new ArrayList<>();
        return findIn(SysGn.InnerColumn.gndm,functionCodes);
    }

    @Override
    public List<String> getRolesFunctionCodes(List<String> jsdms) {
        if (jsdms == null || jsdms.size() == 0)return new ArrayList<>();
        SimpleCondition condition = new SimpleCondition(SysJsGn.class);
        condition.in(SysJsGn.InnerColumn.jsdm,jsdms);
        List<SysJsGn> roleFunctions = jsGnMapper.selectByExample(condition);
        if (roleFunctions.size() == 0)return new ArrayList<>();
        return roleFunctions.stream().map(SysJsGn::getGndm).collect(Collectors.toList());
    }

    private List<SysFw> getAllPermissionTreeWithChecked(List<SysFw> services,List<SysGn> functions){
        List<SysGn> allFunctions = gnService.findAll();
        List<SysFw> allServices = fwService.findAll();

        List<String> functionCodes = functions.stream().map(SysGn::getGndm).collect(Collectors.toList());

        Map<String,SysFw> serviceMap = allServices.stream().collect(Collectors.toMap(SysFw::getFwdm,p->p));
        Map<String,SysGn> functionMap = allFunctions.stream().collect(Collectors.toMap(SysGn::getGndm, p->p));

//        List<String> serviceCodes = new ArrayList<>();
//        for (SysGn function : functions) {
//            String serviceCode = function.getFwdm();
//            if (StringUtils.isEmpty(serviceCode))continue;
//            if (serviceMap.containsKey(serviceCode))continue;
//            serviceCodes.add(serviceCode);
//        }
//        if (serviceCodes.size() != 0){
//            List<SysFw> addServices = fwService.findIn(SysFw.InnerColumn.fwdm,serviceCodes);
//            services.addAll(addServices);
//        }
        for (SysGn function : allFunctions) {
            if (functionCodes.contains(function.getGndm())){
                function.setChecked("true");
            }
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
        return allServices;

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

    @Override
    public List<SysFw> getRolePermissionTree(String jsdm) {
        List<SysGn> functions = getRolesFunctions(Collections.singletonList(jsdm));
        List<SysFw> services;
        if (functions.size() != 0){
            Set<String> serviceCodes = functions.stream().map(SysGn::getFwdm).collect(Collectors.toSet());
            services = fwService.findIn(SysFw.InnerColumn.fwdm,serviceCodes);
        }else{
            services = new ArrayList<>();
        }
        return getAllPermissionTreeWithChecked(services,functions);
    }


}
