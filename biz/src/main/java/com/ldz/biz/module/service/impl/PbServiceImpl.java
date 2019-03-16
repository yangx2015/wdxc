package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.bean.*;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClClyxjlMapper;
import com.ldz.biz.module.mapper.ClPbMapper;
import com.ldz.biz.module.mapper.PbInfoMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClClyxjl;
import com.ldz.biz.module.model.ClPb;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.PbService;
import com.ldz.biz.module.service.XlService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.model.SysZdxm;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.ZdxmService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.CopyObjectUtils;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class PbServiceImpl extends BaseServiceImpl<ClPb, String> implements PbService {

	Logger errorLog = LoggerFactory.getLogger("error_info");
	Logger accessLog = LoggerFactory.getLogger("access_info");

	@Autowired
	private ClPbMapper entityMapper;
	@Autowired
	private ClClyxjlMapper clClyxjlMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private ClService clService;
	@Autowired
	private PbInfoMapper pbinfomapper;
	@Autowired
	private ClClMapper clclmapper;
	@Autowired
	private XlService xlService;
	@Value("${znzpurl}")
	private String znzpUrl;

	@Autowired
	private RedisTemplateUtil redisTemplate;
	@Autowired
	private ZdxmService zdxmService;


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
		RuntimeCheck.ifNull(entity, "当前选择的排班车辆有误，请核实！");
		RuntimeCheck.ifBlank(entity.getDate(), "排线时间不能为空");
		RuntimeCheck.ifBlank(entity.getClId(), "车辆ID不能为空");
		RuntimeCheck.ifBlank(entity.getXlId(), "线路ID不能为空");
		RuntimeCheck.ifBlank(entity.getCx(), " 车型不能为空");
		Date pbDate = null;
		try {
			pbDate = DateUtils.getDate(entity.getDate(), "yyyy-MM-dd");
		} catch (ParseException e) {
		}
		if (pbDate == null) {
			RuntimeCheck.ifFalse(false, "排班时间格式异常");
		}

		clpbInfo clpbInfo = new clpbInfo();
		clpbInfo.setDate(pbDate);
		clpbInfo.setClid(entity.getClId());

		ClCl clCl = clService.findByOrgCode(entity.getClId());
		RuntimeCheck.ifNull(clCl, "车辆信息有误，请核实！");
		String sjId = clCl.getSjId();
		RuntimeCheck.ifBlank(sjId, "该车辆未绑定司机，无法进行排班");
		String clZt = clCl.getZt();
		if (!StringUtils.equals(clZt, "00")) {
			RuntimeCheck.ifBlank(sjId, "该车辆状态异常，无法进行排班");
		}
		if (!StringUtils.equals(entity.getCx(), clCl.getCx())) {
			RuntimeCheck.ifBlank(sjId, "该车辆车型异常，无法进行排班");
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
			entity.setCph(clCl.getCph());// 车牌号码
			entity.setPbsj(pbDate);// 排班时间
			entity.setSj(clCl.getSjId());// 司机ID
			entity.setSjxm(clCl.getSjxm());// 司机姓名
			entity.setEnable("1");
			save(entity);
			return ApiResponse.saveSuccess();
		}else{
			return ApiResponse.fail("车辆与线路已经关联，需求重启关联");
		}

//		List<String> xlidList = findXlCl.stream().map(PbInfo::getXlId).collect(Collectors.toList());
//
//		if (!xlidList.contains(entity.getXlId())) {
//			SysYh user = getCurrentUser();
//			SysJg org = jgService.findByOrgCode(user.getJgdm());
//			entity.setCjr(getOperateUser());
//			entity.setId(genId());
//			entity.setJgdm(user.getJgdm());
//			entity.setJgmc(org.getJgmc());
//			entity.setCjsj(new Date());
//			entity.setCph(clCl.getCph());// 车牌号码
//			entity.setPbsj(pbDate);// 排班时间
//			entity.setSj(clCl.getSjId());// 司机ID
//			entity.setSjxm(clCl.getSjxm());// 司机姓名
//			save(entity);
//			return ApiResponse.saveSuccess();
//		} else {
//			return ApiResponse.fail("车辆与线路已经关联，需求重启关联");
//		}
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

if(selectXbPb!=null&&selectXbPb.size()>0){

		for (XbXlPb xbXlPb : selectXbPb) {
			Map<String,String> clclasseslistMap=new HashMap<>();
			if (StringUtils.isNotEmpty(xbXlPb.getClidlist())) {
				// 获取车辆id集合
				List<String> clidlist = Arrays.asList(xbXlPb.getClidlist().split(","));
				if(StringUtils.isNotEmpty(xbXlPb.getClclasseslist())){
					String[] aaa = xbXlPb.getClclasseslist().split(",");
					int seq=0;
					for(String clId:clidlist){
						if(!StringUtils.equals(aaa[seq],"0")){
							String val=clclasseslistMap.get(clId);
							if(StringUtils.isEmpty(val)){
								val="";
							}
							val+=aaa[seq]+",";
							clclasseslistMap.put(clId,val);
						}
						seq++;
					}
				}

				List<String> clPbList = Arrays.asList(xbXlPb.getClclasseslist().split(","));
				SimpleCondition pbCondition = new SimpleCondition(ClPb.class);
				pbCondition.in(ClPb.InnerColumn.id,clPbList);
				List<ClPb> allPbList = this.findByCondition(pbCondition);

				Map<String,ClPb> pbListMap = allPbList.stream().collect(Collectors.toMap(ClPb::getId,p->p));


				List<ClCl> allClInfo = clclmapper.getAllClInfo(clidlist);
				List<ClCl> copyCollect=new ArrayList<>();
				if (StringUtils.isNotEmpty(pbclxlmodel.getClcx())) {
					// 过滤指定车型的车辆信息
					List<ClCl> collect = allClInfo.stream().filter(s -> s.getCx().equals(pbclxlmodel.getClcx())).collect(Collectors.toList());
					for (ClCl cl:collect){
						String lists=clclasseslistMap.get(cl.getClId());
						if(StringUtils.isNotEmpty(lists)){
							List<String> clClassesList = Arrays.asList(clclasseslistMap.get(cl.getClId()).split(","));
							if(clClassesList!=null&&clClassesList.size()>0){
								for(String classes:clClassesList){
									ClCl copyCl= CopyObjectUtils.deepCopy(cl);
                                    ClPb pb=pbListMap.get(classes);
                                    if(pb!=null){
                                        copyCl.setPbId(pb.getId());
                                        copyCl.setPbbx(pb.getClasses());
                                        copyCl.setStartTime(pb.getStartTime());
                                        copyCl.setEndTime(pb.getEndTime());
                                    }
									copyCollect.add(copyCl);
								}
							}else{
								copyCollect.add(cl);
							}
						}else{
							copyCollect.add(cl);
						}
					}

				} else {
					if(allClInfo!=null&&allClInfo.size()>0){
						for (ClCl cl:allClInfo){
							String lists=clclasseslistMap.get(cl.getClId());
							if(StringUtils.isNotEmpty(lists)){
								List<String> clClassesList = Arrays.asList(clclasseslistMap.get(cl.getClId()).split(","));
								if(clClassesList!=null&&clClassesList.size()>0){
									for(String classes:clClassesList){
										ClCl copyCl= CopyObjectUtils.deepCopy(cl);
                                        ClPb pb=pbListMap.get(classes);
                                        if(pb!=null){
                                            copyCl.setPbId(pb.getId());
                                            copyCl.setPbbx(pb.getClasses());
                                            copyCl.setStartTime(pb.getStartTime());
                                            copyCl.setEndTime(pb.getEndTime());
                                        }
										copyCollect.add(copyCl);
									}
								}else{
									copyCollect.add(cl);
								}
							}else{
								copyCollect.add(cl);
							}
						}
					}
				}
			//将LIST排序，先按车牌号排序。再按排班的班形排序
			copyCollect.sort(new Comparator<ClCl>() {
				@Override
				public int compare(ClCl o1, ClCl o2) {

					String o1Pbbx=o1.getPbbx();
					if(StringUtils.isBlank(o1Pbbx)){
						o1Pbbx="";
					}
					String o2Pbbx=o2.getPbbx();
					if(StringUtils.isBlank(o2Pbbx)){
						o2Pbbx="";
					}
					int i=o1Pbbx.compareTo(o2Pbbx);
					return i>0?1:i==0?0:-1;
				}
			});

			xbXlPb.setClList(copyCollect);
				Map<String,List<ClCl>> mapClList=new HashMap<>();
				mapClList.put("bx1",new ArrayList<ClCl>());//早班
				mapClList.put("bx2",new ArrayList<ClCl>());//中班
				mapClList.put("bx7",new ArrayList<ClCl>());//全天
				if(copyCollect!=null&&copyCollect.size()>0){
//					List<ClCl> copyCollect=
					for(ClCl l:copyCollect){
						String pbbx=l.getPbbx();
						if(StringUtils.isNotBlank(pbbx)){
							if(StringUtils.indexOf("127",pbbx)>-1){
								List<ClCl> clList=mapClList.get("bx"+pbbx);
								if(clList==null){
									clList=new ArrayList<ClCl>();
								}
								String startTime=l.getStartTime();
								String endTime=l.getEndTime();
								startTime=StringUtils.substring(startTime,0,5);
								endTime=StringUtils.substring(endTime,0,5);
								l.setStartTime(startTime);
								l.setEndTime(endTime);
								clList.add(l);
								mapClList.put("bx"+pbbx,clList);
							}
						}
					}
				}
				Map<String,List<ClCl>> mapClListCopy=new HashMap<>();
				mapClListCopy.put("早班",mapClList.get("bx1"));
				mapClListCopy.put("中班",mapClList.get("bx2"));
				mapClListCopy.put("全天",mapClList.get("bx7"));
				xbXlPb.setClMapList(mapClListCopy);
			}
		}
    }
		pbinflist.setResult(selectXbPb);

		return pbinflist;
	}

	@Override
	public ApiResponse<String> deleteByXlAndCl(String xlId, String clId, String date) {
		RuntimeCheck.ifBlank(xlId, "线路ID不能为空");
		RuntimeCheck.ifBlank(clId, "车辆ID不能为空");
		RuntimeCheck.ifBlank(date, "排班时间不能为空");
//
		Date pbDate = null;
		try {
			pbDate = DateUtils.getDate(date, "yyyy-MM-dd");
		} catch (ParseException e) {
		}
		if (pbDate == null) {
			RuntimeCheck.ifFalse(false, "排班时间格式异常");
		}

		int i = 0;
		clpbInfo clpbInfo = new clpbInfo();
		clpbInfo.setDate(pbDate);
		clpbInfo.setClid(clId);
		clpbInfo.setXlid(xlId);
		List<PbInfo> findXlCl = pbinfomapper.findXlCl(clpbInfo);
		if (findXlCl != null && findXlCl.size() == 1 && findXlCl.get(0).getId() != null) {
			i = entityMapper.deleteByPrimaryKey(findXlCl.get(0).getId());
		}
		try {
			ClClyxjl clClyxjl=new ClClyxjl();
			clClyxjl.setClId(clId);
			clClyxjlMapper.delete(clClyxjl);
		}catch (Exception e){}

		if (i == 0) {
			return ApiResponse.fail();
		} else {
			try {
				redisTemplate.delete("ZNZP_PB_"+DateUtils.getToday("yyyyMMdd")+"_"+clId);
			}catch (Exception e){e.printStackTrace();}
			updateRouteInfo(xlId);
			return ApiResponse.success();
		}
	}

	/**
	 * 通知一条线路
	 * @param xlId
	 */
	private void updateRouteInfo(String xlId){
		accessLog.debug("通知一条线路"+xlId);
		if(StringUtils.isNotEmpty(xlId)){
			redisTemplate.convertAndSend("bizUpdateRouteInfo", xlId);
		}
	}

	@Override
	public ApiResponse<List<ClClModel>> getAllNotPbClList(String xlId, String date, String cx) {
		RuntimeCheck.ifBlank(xlId, "线路ID不能为空");
		RuntimeCheck.ifBlank(date, "排班时间不能为空");
		RuntimeCheck.ifBlank(cx, "车型不能为空");
		Date pbDate = null;
		try {
			pbDate = DateUtils.getDate(date, "yyyy-MM-dd");
		} catch (ParseException e) {
		}
		if (pbDate == null) {
			RuntimeCheck.ifFalse(false, "排班时间格式异常");
		}
		List<ClClModel> clClList = clclmapper.getAllNotPbClList(xlId, pbDate, cx);
		if(clClList!=null&&clClList.size()>0){
			List<String> clList = clClList.stream().map(ClClModel::getClId).collect(Collectors.toList());
			SimpleCondition condition = new SimpleCondition(ClPb.class);
			condition.in(ClPb.InnerColumn.clId,clList);
			condition.eq(ClPb.InnerColumn.pbsj,pbDate);
			List<ClPb> pbList = this.findByCondition(condition);
			Map<String,String> pbClasses= new HashMap<>();
			if (pbList.size() > 0){
				for(ClPb pb:pbList){
					if(StringUtils.isNotEmpty(pb.getClasses())){
						String classes=pbClasses.get(pb.getClId());
						if(StringUtils.isEmpty(classes)){
							classes="";
						}
						classes+=pb.getClasses()+",";
						pbClasses.put(pb.getClId(),classes);
					}
				}
			}
			for(ClClModel cl:clClList){
				String[] valueArray = StringUtils.split(pbClasses.get(cl.getClId()), ",");
				if(valueArray!=null&&valueArray.length>0){
					cl.setClassesList(Arrays.asList(valueArray));
				}else{
					cl.setClassesList(new ArrayList<String>());
				}
			}
		}
		return ApiResponse.success(clClList);
	}

	@Override
	public ApiResponse<List<PbInfo>> bancheTj(PbClXlmodel pbclxlmodel) {
		ApiResponse<List<PbInfo>> response = new ApiResponse<>();

		SysYh currentUser = getCurrentUser();
		String jgdm = currentUser.getJgdm();
		pbclxlmodel.setJgdm(jgdm);

		if (StringUtils.isEmpty(pbclxlmodel.getKssj()) || StringUtils.isEmpty(pbclxlmodel.getJssj())) {
			String kssj = DateUtils.getToday() + " 00:00:00";
			String jssj = DateUtils.getToday() + " 23:59:59";
			pbclxlmodel.setKssj(kssj);
			pbclxlmodel.setJssj(jssj);
		}

		List<PbInfo> bancheTj = pbinfomapper.bancheTj(pbclxlmodel);

		response.setResult(bancheTj);
		return response;
	}

	@Override
	public ApiResponse<JrXbKb> xbkb(PbClXlmodel pbclxlmodel) {
		SysYh currentUser = getCurrentUser();
		String jgdm = currentUser.getJgdm();
		pbclxlmodel.setJgdm(jgdm);

		// 校巴车型
		pbclxlmodel.setClcx("30");
		ApiResponse<JrXbKb> apiResponse = new ApiResponse<>();
		JrXbKb jrXbKb = new JrXbKb();
		List<String> xlmcList = new ArrayList<>();
		List<Integer> count = new ArrayList<>();

		if (StringUtils.isEmpty(pbclxlmodel.getKssj()) || StringUtils.isEmpty(pbclxlmodel.getJssj())) {
			String kssj = DateUtils.getToday() + " 00:00:00";
			String jssj = DateUtils.getToday() + " 23:59:59";
			pbclxlmodel.setKssj(kssj);
			pbclxlmodel.setJssj(jssj);
		}

		List<PbInfo> bancheTj = pbinfomapper.bancheTj(pbclxlmodel);

		Map<String, List<PbInfo>> collect = bancheTj.stream().filter(ws -> StringUtils.isNotEmpty(ws.getXlId()))
				.collect(Collectors.groupingBy(PbInfo::getXlId));

		Iterator<Entry<String, List<PbInfo>>> it = collect.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, List<PbInfo>> next = it.next();

			List<PbInfo> value = next.getValue();
			xlmcList.add(value.get(0).getClXl().getXlmc());
			count.add(value.size());
		}
		jrXbKb.setXlmcList(xlmcList);
		jrXbKb.setCount(count);
		apiResponse.setResult(jrXbKb);
		return apiResponse;
	}

	/**
	 * 批量排班
	 * @param entity 排班模型
	 * @return
	 */
    @Override
    public ApiResponse<Map<String,String>> savePbList(ClPb entity) {
		SysYh currentUser = getCurrentUser();
		RuntimeCheck.ifBlank(entity.getClId(), "请选择车辆");
    	RuntimeCheck.ifBlank(entity.getXlId(), "请选择线路");
    	RuntimeCheck.ifBlank(entity.getCx(), "请选择车型");
		RuntimeCheck.ifBlank(entity.getClasses(), "排班班次为空，无法进行排班");
		ApiResponse<Map<String,String>> res = new ApiResponse<>();

		if(StringUtils.containsNone(entity.getClasses(), new char[]{'1', '2',  '7'})){
			res.setCode(500);
			res.setMessage("请输入正确的排班属性");
			return res;
		}


		SysJg org = jgService.findByOrgCode(currentUser.getJgdm());
    	if(StringUtils.isBlank(entity.getKssj())){
    		entity.setKssj(DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}else{
    		if(entity.getKssj().compareTo(DateUtils.getToday("yyyy-MM-dd")) < 0 ){
				res.setCode(500);
				res.setMessage("排班开始时间需要大于或等于当前时间");
				return res;
			}
		}
		Date kssj=null;
		try {
			kssj=DateUtils.getDate(entity.getKssj(),"yyyy-MM-dd");
		} catch (ParseException e) {
			res.setCode(500);
			res.setMessage("请输入正确的排班开始日期 日期格式为：yyyy-MM-dd");
			return res;
		}
        int pbts=-1;//批量排班天数
        if(StringUtils.isNotBlank(entity.getPbts())){
        	try {
				pbts=Integer.parseInt(entity.getPbts());
			}catch (Exception e){}
        }
        if(pbts>1){
//        	确定结束时间
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(kssj);
			calendar.add(Calendar.DATE, pbts);// 把日期往后增加指定月份.整数往后推,负数往前移动
			entity.setJssj(DateUtils.getDateStr(calendar.getTime(),"yyyy-MM-dd"));
		}else{
			if(StringUtils.isBlank(entity.getJssj())){
				entity.setJssj(DateUtils.getToday("yyyy-MM-dd"));
			}else{
				if(entity.getJssj().compareTo(DateUtils.getToday("yyyy-MM-dd")) < 0){
					res.setCode(500);
					res.setMessage("排班结束时间需要大于或等于当前时间");
					return res;
				}
			}
		}

		ClCl clCl = clService.findByOrgCode(entity.getClId());
		RuntimeCheck.ifNull(clCl, "车辆信息有误，请核实！");
		String sjId = clCl.getSjId();
		RuntimeCheck.ifBlank(sjId, "该车辆未绑定司机，无法进行排班");

		String clZt = clCl.getZt();
		if (!StringUtils.equals(clZt, "00")) {
			RuntimeCheck.ifBlank(sjId, "该车辆状态异常，无法进行排班");
		}
		if (!StringUtils.equals(entity.getCx(), clCl.getCx())) {
			RuntimeCheck.ifBlank(sjId, "该车辆车型异常，无法进行排班");
		}

		List<Date> dates = new ArrayList<>();
		try {
			dates = DateUtils.createDateList(entity.getKssj(),entity.getJssj());
		} catch (ParseException e) {
			res.setCode(500);
			res.setMessage("时间格式异常");
			return res;
		}
		Map<String,String> retMap=new HashMap<>();
		Map<String,String> classesDescribe=new HashMap<>();
        classesDescribe.put("1","早班");
        classesDescribe.put("2","午班");
        classesDescribe.put("3","早班、午班");
        classesDescribe.put("4","下午");
        classesDescribe.put("5","早班、下午");
        classesDescribe.put("6","午班、下午");
        classesDescribe.put("7","全班");

		// 根据开始时间和结束时间生成List
		List<ClPb> clPbs = new ArrayList<>();
        String errorMessage="";
		if(CollectionUtils.isNotEmpty(dates)){
			SimpleCondition condition = new SimpleCondition(ClPb.class);
			condition.eq(ClPb.InnerColumn.clId, entity.getClId());
			if(!StringUtils.equals(entity.getClasses(),"7")){
				condition.eq(ClPb.InnerColumn.classes, entity.getClasses());
			}
			condition.lte(ClPb.InnerColumn.pbsj, dates.get(dates.size()-1));
			condition.gte(ClPb.InnerColumn.pbsj, dates.get(0));
			// 首先删除掉当前车辆和线路上已经存在的排班
			int i = entityMapper.deleteByExample(condition);

			for (Date date : dates) {
				ClPb clPb = new ClPb();

				clPb.setEnable("1");
				clPb.setPbsj(date);
				clPb.setClId(entity.getClId());
				clPb.setXlId(entity.getXlId());
				clPb.setId(genId());
				clPb.setCjr(getOperateUser());
				clPb.setCjsj(new Date());
				clPb.setCph(clCl.getCph());
				clPb.setJgdm(currentUser.getJgdm());
				clPb.setJgmc(org.getJgmc());
				clPb.setSj(clCl.getSjId());
				clPb.setSjxm(clCl.getSjxm());
				clPb.setClasses(entity.getClasses());

				String startTime="";
				String endTime="";
				try {
					//字典项如果不存在，也不比较
					SimpleCondition zdcondition = new SimpleCondition(SysZdxm.class);
					zdcondition.eq(SysZdxm.InnerColumn.zdlmdm,"ZDCLK0055");
					zdcondition.eq(SysZdxm.InnerColumn.zddm,entity.getClasses());
					List<SysZdxm> zdxms = zdxmService.findByCondition(zdcondition);
					if (zdxms!=null&& zdxms.size() != 0){
						String value = zdxms.get(0).getZdmc();
						if(StringUtils.isNotEmpty(value)){
							String[] datas=value.split("-");
							DateUtils.getDate(datas[0],"HH:mm:ss");
							DateUtils.getDate(datas[1],"HH:mm:ss");
							startTime=datas[0];
							endTime=datas[1];
						}
					}
				}catch (Exception e){
					e.printStackTrace();
					RuntimeCheck.ifTrue(true, "获取排班时间异常，无法进行排班");
				}
				RuntimeCheck.ifBlank(startTime, "获取排班开始时间异常，无法进行排班");
				RuntimeCheck.ifBlank(endTime, "获取排班结束时间异常，无法进行排班");
				clPb.setStartTime(startTime);
				clPb.setEndTime(endTime);
				retMap.put("startTime",startTime);
				retMap.put("endTime",endTime);

				boolean type=true;
				String classesSum=entityMapper.getClPbClasses(entity.getClId(),DateUtils.getDateStr(new Date(),"yyyyMMdd"));
                if(StringUtils.isNotEmpty(StringUtils.trim(classesSum))){
                    Double classessum= Double.parseDouble(classesSum);
                    if(classessum>0&&classessum+Double.parseDouble(entity.getClasses())>7){
                        errorMessage+="车辆:"+clCl.getCph()+"在"+DateUtils.getDateStr(new Date(),"yyyyMMdd")+"已有排班次："+classesDescribe.get(classesSum) +" 本次排班失败\n\r";
                        type=false;
                    }
                }
                if(type){
                    clPbs.add(clPb);
                }
			}
			if(clPbs!=null&&clPbs.size()>0){
				entityMapper.insertList(clPbs);
				retMap.put("pbId",clPbs.get(0).getId());
			}
		}
		if(StringUtils.isNotEmpty(errorMessage)){
			res.setCode(500);
			res.setMessage(errorMessage);
			return res;
        }

		return ApiResponse.success(retMap);
    }


	/**
	 * 根据时间段和线路Id ， 返回当前线路，当前时间段的车辆排班信息
	 * @param entity
	 * @return
	 */
	@Override
	public ApiResponse<List<PbInfo>> checkPbCl(ClPb entity) {

		RuntimeCheck.ifBlank(entity.getXlId(), "请选择要查询的线路");
		if(StringUtils.isBlank(entity.getKssj())){
			entity.setKssj(DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(entity.getJssj())){
			entity.setJssj(DateUtils.getToday("yyyy-MM-dd"));
		}
		SimpleCondition simpleCondition = new SimpleCondition(ClPb.class);
		simpleCondition.eq(ClPb.InnerColumn.xlId,entity.getXlId());
		try {
			simpleCondition.lte(ClPb.InnerColumn.pbsj, DateUtils.getDate(entity.getJssj(),"yyyy-MM-dd"));
			simpleCondition.gte(ClPb.InnerColumn.pbsj, DateUtils.getDate(entity.getKssj(), "yyyy-MM-dd"));
		} catch (ParseException e) {
			ApiResponse<List<PbInfo>>  response = new ApiResponse<>();
			response.setCode(500);
			response.setResult(null);
			response.setMessage("时间格式异常");
			return response;
		}
		List<PbInfo> pbInfos = new ArrayList<>();
		List<ClPb> clPbs = findByCondition(simpleCondition);
		for (ClPb clPb : clPbs) {
			PbInfo pbInfo = new PbInfo();
			pbInfo.setId(clPb.getId());
			pbInfo.setClXl(xlService.findById(clPb.getXlId()));
			pbInfo.setXlId(clPb.getXlId());
			ClCl clCl = clService.findById(clPb.getClId());
			pbInfo.setClcl(clCl);
			pbInfo.setClId(clPb.getClId());
			pbInfo.setPbsj(clPb.getPbsj());
			pbInfo.setCph(clCl.getCph());
			pbInfo.setJgdm(clPb.getJgdm());
			pbInfo.setJgmc(clPb.getJgmc());
			pbInfo.setSj(clPb.getSj());
			pbInfo.setSjxm(clPb.getSjxm());
			pbInfos.add(pbInfo);
		}
		return ApiResponse.success(pbInfos);
	}

	/**
	 * 取消排班列表
	 * @param entity
	 * @return
	 */
	@Override
	public ApiResponse<String> delPbList(ClPb entity) {
		accessLog.debug("开启线程池，升级终端"+entity.getXlId());
		RuntimeCheck.ifBlank(entity.getXlId(), "请选择线路");
		RuntimeCheck.ifBlank(entity.getClId(), "请选择车辆");
		RuntimeCheck.ifBlank(entity.getClasses(), "请选择排班类型");
		RuntimeCheck.ifBlank(entity.getKssj(), "请输入开始时间");
		RuntimeCheck.ifBlank(entity.getJssj(), "请输入结束时间");
		if(StringUtils.containsNone(entity.getClasses(), new char[]{'1', '2', '7'})){
			return ApiResponse.fail("请输入正确的排班属性");
		}

		SimpleCondition simpleCondition = new SimpleCondition(ClPb.class);
		List<Date> dates = new ArrayList<>();
		try {
			dates = DateUtils.createDateList(entity.getKssj(),entity.getJssj());
		} catch (ParseException e) {
			return ApiResponse.fail("时间格式有误");
		}
		if(CollectionUtils.isNotEmpty(dates)){
			simpleCondition.eq(ClPb.InnerColumn.xlId, entity.getXlId());
			simpleCondition.eq(ClPb.InnerColumn.clId, entity.getClId());
			simpleCondition.lte(ClPb.InnerColumn.pbsj, dates.get(dates.size()-1));
			simpleCondition.gte(ClPb.InnerColumn.pbsj, dates.get(0));
			entityMapper.deleteByExample(simpleCondition);
		}

		try {
			ClClyxjl clClyxjl=new ClClyxjl();
			clClyxjl.setClId(entity.getClId());
			clClyxjlMapper.delete(clClyxjl);
		}catch (Exception e){}

		try {
			redisTemplate.delete("ZNZP_PB_"+DateUtils.getToday("yyyyMMdd")+"_"+entity.getClId());
		}catch (Exception e){e.printStackTrace();}


		accessLog.debug("要删除线路ID",entity.getXlId());
		updateRouteInfo(entity.getXlId());

		return ApiResponse.success();
	}

	/**
	 * 移除一组排班
	 * @param pbId  aaa,bbb,
	 */
	@Override
	public  void removePbList(String pbId){
		String time = DateUtils.getNowTime().substring(11);

		String xlIds="";//线路ID
		String clIds="";//需要重新加载的列表
		String delPbId="";
		if(StringUtils.isNotEmpty(pbId)){
			// 获取车辆id集合
			List<String> clidlist = Arrays.asList(pbId.split(","));
			SimpleCondition condition = new SimpleCondition(ClPb.class);
			condition.lte(ClPb.InnerColumn.endTime,time);
			condition.and().andCondition(" TO_CHAR (PBSJ, 'yyyy-MM-dd') <= ",DateUtils.getToday("yyyy-MM-dd"));
			condition.in(ClPb.InnerColumn.id,clidlist);
			List<ClPb> pbs = this.findByCondition(condition);
			if(pbs!=null&&pbs.size()>0){
				for(ClPb pb:pbs){
					delPbId+=pb.getId()+",";
					if(StringUtils.indexOf(xlIds,pb.getXlId())<0){
						xlIds+=pb.getXlId()+",";
					}
					if(StringUtils.indexOf(clIds,pb.getClId())<0){
						clIds+=pb.getClId()+",";
					}
				}
			}
		}

		if(StringUtils.isNotEmpty(delPbId)){
			List<String> delPb = Arrays.asList(delPbId.split(","));
			if(delPb!=null&&delPb.size()>0){
				entityMapper.updateRemovePb(delPb);//修改成逻辑删除
			}
		}
		if(StringUtils.isNotEmpty(clIds)){
			List<String> clId = Arrays.asList(clIds.split(","));
			if(clId!=null&&clId.size()>0){
                SimpleCondition condition = new SimpleCondition(ClClyxjl.class);
                condition.in(ClClyxjl.InnerColumn.clId,clId);
                clClyxjlMapper.deleteByExample(condition);

                try {
                    for(String clid:clId){
                        redisTemplate.delete("ZNZP_PB_"+DateUtils.getToday("yyyyMMdd")+"_"+clid);
                    }
                }catch (Exception e){e.printStackTrace();}
            }
		}
		if(StringUtils.isNotEmpty(xlIds)){

			updateRouteInfo(xlIds);
		}
	}

	/**
	 * 修改排班时间.
	 * id	排班ID
	 * kssj	开始时间
	 * jssj	结束时间
	 */
	@Override
	public ApiResponse<String> updPbTime(ClPb entity){
		RuntimeCheck.ifBlank(entity.getId(), "请选择排班信息");
		RuntimeCheck.ifBlank(entity.getStartTime(), "请输入开始时间");
		RuntimeCheck.ifBlank(entity.getEndTime(), "请输入结束时间");
		ClPb pb=this.findById(entity.getId());
		RuntimeCheck.ifNull(pb,"排班信息有误，请核实");
		RuntimeCheck.ifTrue(StringUtils.equals(pb.getEnable(),"0"),"该排班已过期，无需修改结束时间。");

		Map<String,String> classesDescribe=new HashMap<>();
		classesDescribe.put("1","早班");
		classesDescribe.put("2","午班");
		classesDescribe.put("3","早班、午班");
		classesDescribe.put("4","下午");
		classesDescribe.put("5","早班、下午");
		classesDescribe.put("6","午班、下午");
		classesDescribe.put("7","全班");


		Boolean pbTime=true;
		String startTime=entity.getStartTime();
		String endTime=entity.getEndTime();
		try {
			DateUtils.getDate(startTime,"HH:mm");
		}catch (Exception e){
			e.printStackTrace();
			RuntimeCheck.ifTrue(true, "开始时间格式不正确");
		}
		if(startTime.length()==5){
			startTime=startTime+":00";
		}
		try {
			DateUtils.getDate(endTime,"HH:mm");
		}catch (Exception e){
			e.printStackTrace();
			RuntimeCheck.ifTrue(true, "结束时间格式不正确");
		}
		if(endTime.length()==5){
			endTime=endTime+":00";
		}
		String bcName="";
		try {
			String clId=pb.getClId();
			Date pbsj=pb.getPbsj();
			ClPb obj=new ClPb();
			obj.setClId(clId);
			obj.setEnable("1");
			obj.setPbsj(pbsj);
			List<ClPb> pbList=this.findByEntity(obj);
			if(pbList!=null&&pbList.size()>0){
				for(ClPb l:pbList){
					if(!StringUtils.equals(l.getId(),entity.getId())){
						//判断开始时间是否在当前排班中
						Boolean type=DateUtils.hourMinuteBetween(startTime,l.getStartTime(),l.getEndTime());
						if(type){
							bcName=classesDescribe.get(l.getClasses());
							pbTime=false;
							break;
						}
						//判断结束时间是否在当前排班中
						type=DateUtils.hourMinuteBetween(endTime,l.getStartTime(),l.getEndTime());
						if(type){
							bcName=classesDescribe.get(l.getClasses());
							pbTime=false;
							break;
						}
						//判断
						type=DateUtils.hourMinuteBetween(l.getStartTime(),startTime,endTime);
						if(type){
							bcName=classesDescribe.get(l.getClasses());
							pbTime=false;
							break;
						}
						type=DateUtils.hourMinuteBetween(l.getEndTime(),startTime,endTime);
						if(type){
							bcName=classesDescribe.get(l.getClasses());
							pbTime=false;
							break;
						}
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			pbTime=false;
			RuntimeCheck.ifTrue(true, "排班时间统计失败");
		}
		if(pbTime){
			pb.setStartTime(startTime);
//			pb.setStartTime(entity.getStartTime());
//			pb.setEndTime(entity.getEndTime());
			pb.setEndTime(endTime);
			entityMapper.updateByPrimaryKey(pb);
		}else{
			return ApiResponse.fail("当前排班时间与班次："+bcName+"重叠，排班时间修改失败");
		}
		return ApiResponse.success();
	}
}
