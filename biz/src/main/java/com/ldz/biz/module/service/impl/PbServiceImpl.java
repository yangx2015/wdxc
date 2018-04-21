package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.bean.*;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClPbMapper;
import com.ldz.biz.module.mapper.PbInfoMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClPb;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.PbService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PbServiceImpl extends BaseServiceImpl<ClPb, String> implements PbService {
	@Autowired
	private ClPbMapper entityMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private ClService clService;
	@Autowired
	private PbInfoMapper pbinfomapper;
	@Autowired
	private ClClMapper clclmapper;

	@Override
	protected Mapper<ClPb> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClPb.class;
	}

	@Override
	public ApiResponse<String> saveEntity(ClPb entity) {
		RuntimeCheck.ifNull(entity,"当前选择的排班车辆有误，请核实！");
		RuntimeCheck.ifBlank(entity.getDate(),"排线时间不能为空");
		RuntimeCheck.ifBlank(entity.getClId(),"车辆ID不能为空");
		RuntimeCheck.ifBlank(entity.getXlId(),"线路ID不能为空");
		Date pbDate=null;
		try {
			pbDate=DateUtils.getDate(entity.getDate(),"yyyy-MM-dd");
		} catch (ParseException e) {}
		if(pbDate==null){
			RuntimeCheck.ifFalse(false,"排班时间格式异常");
		}

		clpbInfo clpbInfo = new clpbInfo();
		clpbInfo.setDate(pbDate);
		clpbInfo.setClid(entity.getClId());

		ClCl clCl=clService.findByOrgCode(entity.getClId());
		RuntimeCheck.ifNull(clCl,"车辆信息有误，请核实！");
		String sjId=clCl.getSjId();
		RuntimeCheck.ifBlank(sjId,"该车辆未绑定司机，无法进行排班");
		String clZt=clCl.getZt();
		if(!StringUtils.equals(clZt,"00")){
			RuntimeCheck.ifBlank(sjId,"该车辆状态异常，无法进行排班");
		}


		// 通过车辆id找到当天是否有排班线路信息
		List<PbInfo> findXlCl = pbinfomapper.findXlCl(clpbInfo);
		if (CollectionUtils.isEmpty(findXlCl)) {
			SysYh user = getCurrentUser();
			SysJg org = jgService.findByOrgCode(user.getJgdm());
			entity.setCjr(getOperateUser());
			entity.setId(genId());
			entity.setJgdm(user.getJgdm());
			entity.setJgmc(org.getJgmc());
			entity.setCjsj(new Date());
			entity.setCph(clCl.getCph());//车牌号码
			entity.setPbsj(pbDate);//排班时间
			entity.setSj(clCl.getSjId());//司机ID
			entity.setSjxm(clCl.getSjxm());//司机姓名
			save(entity);
			return ApiResponse.saveSuccess();
		}

		List<String> xlidList = findXlCl.stream().map(PbInfo::getXlId).collect(Collectors.toList());

		if (!xlidList.contains(entity.getXlId())) {
			SysYh user = getCurrentUser();
			SysJg org = jgService.findByOrgCode(user.getJgdm());
			entity.setCjr(getOperateUser());
			entity.setId(genId());
			entity.setJgdm(user.getJgdm());
			entity.setJgmc(org.getJgmc());
			entity.setCjsj(new Date());
			entity.setCph(clCl.getCph());//车牌号码
			entity.setPbsj(pbDate);//排班时间
			entity.setSj(clCl.getSjId());//司机ID
			entity.setSjxm(clCl.getSjxm());//司机姓名
			save(entity);
			return ApiResponse.saveSuccess();
		}else{
			return ApiResponse.fail("车辆与线路已经关联，需求重启关联");
		}
	}

	@Override
	public ApiResponse<String> updateEntity(ClPb entity) {
		ClPb findById = findById(entity.getId());
		RuntimeCheck.ifNull(findById, "未找到记录");
		entity.setXgr(getOperateUser());
		entity.setXgj(new Date());
		update(entity);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<PbInfo>> getPbInfo(PbClXlmodel pbclxlmodel) {

		ApiResponse<List<PbInfo>> pbinflist = new ApiResponse<>();
		// 获取该日车辆已排班信息
		List<PbInfo> selectBydate = pbinfomapper.selectBydate(pbclxlmodel);

		pbinflist.setResult(selectBydate);

		return pbinflist;
	}

	@Override
	public ApiResponse<List<XbXlPb>> getAllPbInfo(PbClXlmodel pbclxlmodel) {

		ApiResponse<List<XbXlPb>> pbinflist = new ApiResponse<>();
		// 获取指定线路下面的排班信息
		List<XbXlPb> selectXbPb = pbinfomapper.selectXbPb(pbclxlmodel);

		for (XbXlPb xbXlPb : selectXbPb) {

			if (StringUtils.isNotEmpty(xbXlPb.getClidlist())) {
				// 获取车辆id集合
				List<String> clidlist = Arrays.asList(xbXlPb.getClidlist().split(","));
				List<ClCl> allClInfo = clclmapper.getAllClInfo(clidlist);
				if (StringUtils.isNotEmpty(pbclxlmodel.getClcx())) {
					// 过滤指定车型的车辆信息
					List<ClCl> collect = allClInfo.stream().filter(s -> s.getCx().equals(pbclxlmodel.getClcx()))
							.collect(Collectors.toList());
					xbXlPb.setClList(collect);
				} else {
					xbXlPb.setClList(allClInfo);
				}

			}

		}

		pbinflist.setResult(selectXbPb);

		return pbinflist;
	}

	@Override
	public ApiResponse<String> deleteByXlAndCl(String xlId, String clId,String date) {
		RuntimeCheck.ifBlank(xlId,"线路ID不能为空");
		RuntimeCheck.ifBlank(clId,"车辆ID不能为空");
		RuntimeCheck.ifBlank(date,"排班时间不能为空");

		Date pbDate=null;
		try {
			pbDate=DateUtils.getDate(date,"yyyy-MM-dd");
		} catch (ParseException e) {}
		if(pbDate==null){
			RuntimeCheck.ifFalse(false,"排班时间格式异常");
		}

		int i=0;
		clpbInfo clpbInfo = new clpbInfo();
		clpbInfo.setDate(pbDate);
		clpbInfo.setClid(clId);
		clpbInfo.setXlid(xlId);
		List<PbInfo> findXlCl = pbinfomapper.findXlCl(clpbInfo);
		if(findXlCl!=null&&findXlCl.size()==1&&findXlCl.get(0).getId()!=null){
			i=entityMapper.deleteByPrimaryKey(findXlCl.get(0).getId());
		}
		if(i==0){
			return ApiResponse.fail();
		}else{
			return ApiResponse.success();
		}
	}

	public String getNowDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		return date;
	}
	@Override
	public ApiResponse<List<ClClModel>> getAllNotPbClList(String xlId, String date){
		RuntimeCheck.ifBlank(xlId,"线路ID不能为空");
		RuntimeCheck.ifBlank(date,"排班时间不能为空");
		Date pbDate=null;
		try {
			pbDate=DateUtils.getDate(date,"yyyy-MM-dd");
		} catch (ParseException e) {}
		if(pbDate==null){
			RuntimeCheck.ifFalse(false,"排班时间格式异常");
		}
		List<ClClModel> clClList=clclmapper.getAllNotPbClList(xlId,pbDate);
		return ApiResponse.success(clClList);
	}
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		System.out.println(date);

		String aString = "sasd";
		String bString = "s1asd";

		if (!aString.equals(bString)) {
			System.out.println("sadsadwsdasd");
		}

		List<String> ss = new ArrayList<>();

		ss.add("苹果");
		ss.add("香蕉");

		String sdString = "苹果";
		System.out.println(ss.contains(sdString));
	}
}
