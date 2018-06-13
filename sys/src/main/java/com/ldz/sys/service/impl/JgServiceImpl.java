package com.ldz.sys.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ldz.sys.bean.TreeNode;
import com.ldz.sys.model.SysGn;
import com.ldz.sys.model.SysYh;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.constant.Dict;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.sys.mapper.SysPtjgMapper;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.OrgUtil;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;

import tk.mybatis.mapper.common.Mapper;

/**
 * auther chenwei create at 2018/2/26
 */
@Service
public class JgServiceImpl extends BaseServiceImpl<SysJg, String> implements JgService {
	@Autowired
	private SysPtjgMapper ptjgMapper;

	@Autowired
	private JgService jgService;

	@Override
	protected Mapper<SysJg> getBaseMapper() {
		return ptjgMapper;
	}

	@Override
	public List<SysJg> getOrgTree(List<SysJg> orgList) {
		Map<String, SysJg> orgIdMap = orgList.stream().collect(Collectors.toMap(SysJg::getJgdm, p -> p));
		List<SysJg> root = new ArrayList<>();
		for (SysJg org : orgList) {
			if (StringUtils.isEmpty(org.getFjgdm()) || !orgIdMap.containsKey(org.getFjgdm())) {
				root.add(org);
				continue;
			}
			SysJg father = orgIdMap.get(org.getFjgdm());
			if (father == null)
				continue;
			if (father.getChildren() == null || father.getChildren().size() == 0) {
				List<SysJg> children = new ArrayList<>();
				children.add(org);
				father.setChildren(children);
			} else {
				father.getChildren().add(org);
			}
		}
		return root;
	}

	@Override
	public List<SysJg> getOrgTreeByOrgCodes(List<String> orgCodes) {
		List<SysJg> orgs = findIn(SysJg.InnerColumn.jgdm, orgCodes);
		return getOrgTree(orgs);
	}

	/**
	 * 根据机构层级获取机构列表
	 *
	 * @param level
	 *            机构层级
	 * @return 机构列表
	 */
	@Override
	public List<SysJg> findByLevel(Integer level) {
		RuntimeCheck.ifNull(level, "机构层级不能为空");
		return findEq(SysJg.InnerColumn.jgdj, level);
	}

	/**
	 * 新增机构
	 *
	 * @param entity
	 *            参数
	 * @return 操作结果
	 */
	@Override
	public ApiResponse<String> saveEntity(SysJg entity) {
		if (StringUtils.isEmpty(entity.getJglx())) {
			entity.setJglx("10");
		}
		Dict.OrgType orgType = Dict.OrgType.toEnum(entity.getJglx());
		RuntimeCheck.ifNull(orgType, "机构类型不存在");

		entity.setCjr(getOperateUser());
		entity.setCjsj(new Date());
		entity.setZt(Dict.CommonStatus.VALID.getCode());
		String orgCode = genOrgCode(entity);
		entity.setJgdm(orgCode);
		if (StringUtils.isEmpty(entity.getFjgdm())) { // 一级机构
			entity.setJgdj(1);
		} else {
			String fatherCode = entity.getFjgdm();
			int fatherLevel = OrgUtil.getLevel(fatherCode);
			entity.setJgdj(fatherLevel + 1);
		}
		ptjgMapper.insertSelective(entity);
		return ApiResponse.success();
	}

	private String genOrgCode(SysJg org) {
		String fatherCode = org.getFjgdm();
		if (fatherCode == null)
			fatherCode = "";
		int level = fatherCode.length() / 3 + 1;
		SimpleCondition condition = new SimpleCondition(SysJg.class);
		condition.eq(SysJg.InnerColumn.jgdj, level);
		condition.setOrderByClause(SysJg.InnerColumn.jgdm.desc());
		List<SysJg> orgs = findByCondition(condition);
		if (orgs.size() == 0)
			return fatherCode + "001";
		String orgCode = orgs.get(0).getJgdm();
		String maxJgdm = orgCode.substring(orgCode.length() - 3);
		int max = Integer.parseInt(maxJgdm) + 1;
		return fatherCode + StringUtils.leftPad("" + max, 3, "0");
	}

	@Override
	public SysJg findByOrgCode(String orgCode) {
		List<SysJg> jgs = findEq(SysJg.InnerColumn.jgdm, orgCode);
		if (jgs.size() == 0)
			return null;
		return jgs.get(0);
	}

	/**
	 * 获取机构下面的所有子机构
	 *
	 * @param orgCode
	 * @return
	 */
	@Override
	public List<SysJg> findAllSubOrg(String orgCode,String jgmc) {
		SimpleCondition condition = new SimpleCondition(SysJg.class);
		condition.startWith(SysJg.InnerColumn.jgdm,orgCode);
		if (StringUtils.isNotEmpty(jgmc)){
			condition.like(SysJg.InnerColumn.jgmc,jgmc);
		}
		return ptjgMapper.selectByExample(condition);
	}

	@Override
	public ApiResponse<List<SysJg>> getOrgTree(String jgmc) {
		ApiResponse<List<SysJg>> response = new ApiResponse<>();
		SysYh user = getCurrentUser();
		List<SysJg> orgs = jgService.findAllSubOrg(user.getJgdm(),jgmc);
		List<SysJg> orgTree = jgService.getOrgTree(orgs);
		response.setResult(orgTree);
		return response;
	}

	/**
	 * 根据机构代码获取当前机构的所有父机构
	 * @param orgCode
	 * @return
	 */
	//@Override
	public ApiResponse<List<SysJg>> getOrgPath(String orgCode,JgServiceImpl jgService) {
		SysJg function = jgService.findById(orgCode);
		RuntimeCheck.ifNull(function,"未找到记录");
		List<SysJg> list = new ArrayList<>();
		list.add(function);
		findParent(function,list,jgService);
		// System.out.println("排序前： " + list);

		List<SysJg> sysJgs = list.stream().sorted((o1, o2) -> o2.getJgdj().compareTo(o1.getJgdj())).collect(Collectors.toList());
		// System.out.println("排序后： " + sysJgs);
		return ApiResponse.success(sysJgs);
	}


	private void findParent(SysJg function,List<SysJg> result,JgServiceImpl jgService){
		if (function == null){
			return;
		}
		if (StringUtils.isEmpty(function.getFjgdm())){
			return;
		}
		SysJg father = jgService.findById(function.getFjgdm());
		if (father == null){
			return;
		}
		result.add(father);
		findParent(father,result,jgService);
	}

	@Override
	public ApiResponse<List<TreeNode>> getTree() {
		SysYh user = getCurrentUser();
		List<SysJg> orgs = jgService.findAllSubOrg(user.getJgdm(),null);
		List<TreeNode> treeNodes = convertToTreeNodeList(orgs);
		treeNodes = buildTree(treeNodes);
		return ApiResponse.success(treeNodes);
	}



	private List<TreeNode> buildTree(List<TreeNode> list){
		Map<String,TreeNode> nodeMap = list.stream().collect(Collectors.toMap(TreeNode::getValue,p->p));
		List<TreeNode> root = new ArrayList<>();
		for (TreeNode node : list) {
			if (StringUtils.isEmpty(node.getFather())){
				root.add(node);
				continue;
			}
			TreeNode father = nodeMap.get(node.getFather());
			if (father == null)continue;
			if (father.getChildren() == null){
				List<TreeNode> children = new ArrayList<>();
				children.add(node);
				father.setChildren(children);
			}else{
				father.getChildren().add(node);
			}
		}
		return root;
	}

	private List<TreeNode> convertToTreeNodeList(List<SysJg> orgList){
		List<TreeNode> treeNodes = new ArrayList<>(orgList.size());
		for (SysJg jg : orgList) {
			treeNodes.add(convertToTreeNode(jg));
		}
		return treeNodes;
	}
	private TreeNode convertToTreeNode(SysJg org){
		TreeNode node = new TreeNode();
		node.setLabel(org.getJgmc());
		node.setValue(org.getJgdm());
		node.setFather(org.getFjgdm());
		return node;
	}

	@Override
	public ApiResponse<List<SysJg>> getOrgPath(String orgCode) {
		return null;
	}
}
