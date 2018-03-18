package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.mapper.SysClkPtjsMapper;
import com.ldz.sys.mapper.SysJsGnMapper;
import com.ldz.sys.mapper.SysUserMapper;
import com.ldz.sys.mapper.SysYhJsMapper;
import com.ldz.sys.model.SysJs;
import com.ldz.sys.model.SysJsGn;
import com.ldz.sys.model.SysUser;
import com.ldz.sys.model.SysYhJs;
import com.ldz.sys.service.JsService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsServiceImpl extends BaseServiceImpl<SysJs, String> implements JsService {

    @Autowired
    private SysClkPtjsMapper roleMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysYhJsMapper userRoleMapper;
    @Autowired
    private SysJsGnMapper roleResourceMapper;
    @Override
    protected Mapper<SysJs> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public ApiResponse<String> saveEntity(SysJs entity) {
        entity.setCjsj(new Date());
        roleMapper.insert(entity);
        return ApiResponse.success();
    }

    @Override
    public List<String> getUserRoleIds(String yhid) {
        Example userRoleExample = new Example(SysYhJs.class);
        userRoleExample.and().andEqualTo(SysYhJs.InnerColumn.yhId.name(),yhid);
        List<SysYhJs> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (userRoles.size() == 0) return new ArrayList<>();
        return userRoles.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
    }

    /**
     * 由于iview模板功能限制
     * 返回系统所有角色，并把用户拥有的角色 _checked属性标记为true，以便前台修改用户角色
     * @param yhid
     * @return
     */
    @Override
    public List<SysJs> getUserRolesWithChecked(String yhid) {
        List<SysJs> allRoles = roleMapper.selectAll();
        List<String> jsIds = getUserRoleIds(yhid);
        if (jsIds.size() == 0)return allRoles;
        for (SysJs role : allRoles) {
            if (jsIds.contains(role.getJsId())){
                role.set_checked(true);
            }
        }
        return allRoles;
    }

    @Override
    public List<SysJs> getUserRoles(String yhid) {
        List<String> jsIds = getUserRoleIds(yhid);
        if (jsIds.size() == 0) return new ArrayList<>();
        Example roleExample = new Example(SysJs.class);
        roleExample.and().andIn(SysJs.InnerColumn.jsId.name(), jsIds);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public List<SysJs> findByRoleIds(Iterable<String> jsIds) {
        Example roleExample = new Example(SysJs.class);
        roleExample.and().andIn(SysJs.InnerColumn.jsId.name(),jsIds);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public ApiResponse<String> modifyUserRoles(String yhid, List<String> jsIds) {
        // 检查用户是否存在
        SysUser user = userMapper.selectByPrimaryKey(yhid);
        if (user == null) return ApiResponse.fail("用户不存在");

        // 删除就数据
        Example userRoleExample = new Example(SysYhJs.class);
        userRoleExample.and().andEqualTo(SysYhJs.InnerColumn.yhId.name(),yhid);
        userRoleMapper.deleteByExample(userRoleExample);

        // 插入新数据
        for (String jsId : jsIds) {
            SysYhJs userRole = new SysYhJs();
            userRole.setJsId(jsId);
            userRole.setYhId(yhid);
            userRoleMapper.insert(userRole);
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> modifyRolePermission(String jsId, List<String> bizIds, List<String> gndms) {
        // 检查角色是否存在
        SysJs role = roleMapper.selectByPrimaryKey(jsId);
        if (role == null) return ApiResponse.fail("角色不存在");

        // 删除旧数据

        SysJsGn roleResourceExample = new SysJsGn();
        roleResourceExample.setJsdm(jsId);
        roleResourceMapper.deleteByExample(roleResourceExample);

        // 插入新数据

        for (String gndm : gndms) {
            SysJsGn roleResource = new SysJsGn();
            roleResource.setJsdm(jsId);
            roleResource.setGndm(gndm);
            roleResourceMapper.insert(roleResource);
        }
        return ApiResponse.success();
    }
}
