package com.ldz.wechat.module.service.impl;


import com.github.pagehelper.PageInfo;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MathUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.util.gps.LatLonUtil;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.base.LimitedCondition;
import com.ldz.wechat.module.mapper.*;
import com.ldz.wechat.module.model.*;
import com.ldz.wechat.module.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class DdServiceImpl extends BaseServiceImpl<ClDd,String> implements DdService {

    @Autowired
    private ClDdMapper entityMapper;
    @Autowired
    private ClJsyService jsyService;
    @Autowired
    private DdrzService ddrzService;
    @Autowired
    private ClClMapper clMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClGpsLsMapper gpsLsMapper;
    @Autowired
    private ClyyMapper clyyMapper;
    @Autowired
    private ClYyService clYyService;
    @Autowired
    private SysHsgsMapper hsgsMapper;

    @Autowired
    private SysRlbMapper rlbMapper;


    @Autowired
    private YhService yhService;
    @Autowired
    private ClDdrzMapper ddrzMapper;//历史订单明细表
    @Autowired
    private SysJzgxxService jzgxxService;
    @Override
    protected Mapper<ClDd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    public boolean fillCondition(LimitedCondition condition){
        String userId = getCurrentUser(true);
        condition.eq(ClDd.InnerColumn.ckCjl,userId);
        condition.setOrderByClause("cjsj desc");
        return true;
    }

    @Override
    public void afterPager(PageInfo<ClDd> pageInfo){
        List<String> driverIds = pageInfo.getList().stream().map(ClDd::getSj).collect(Collectors.toList());
        if (driverIds.size() == 0)return;
        List<ClJsy> drivers =  jsyService.findIn(ClJsy.InnerColumn.sfzhm,driverIds);
        if (drivers.size() == 0)return;
        Map<String,ClJsy> driverMap = drivers.stream().collect(Collectors.toMap(ClJsy::getSfzhm,p->p));
        for (ClDd dd : pageInfo.getList()) {
            //计算出GPS的距离
            BigDecimal originLat=dd.getOriginLat();//起始纬度
            BigDecimal originLng=dd.getOriginLng();//起始经度
            BigDecimal destinationLat=dd.getDestinationLat();//结束点经度
            BigDecimal destinationLng=dd.getDestinationLng();//结束点纬度
            if(originLat!=null&&originLng!=null&&destinationLat!=null&&destinationLng!=null){
                double gpsDistance= LatLonUtil.getDistance(originLng.doubleValue(),originLat.doubleValue(),destinationLng.doubleValue(),destinationLat.doubleValue());
                if(gpsDistance>0){
                    dd.setGpsDistance(String.valueOf(gpsDistance));
                }else{
                    dd.setGpsDistance("0");
                }
            }else{
//                dd.setGpsDistance("0");
            }

            String driverId = dd.getSj();
            if (StringUtils.isEmpty(driverId))continue;
            ClJsy driver = driverMap.get(driverId);
            if (driver == null)continue;
            dd.setSjdh(driver.getSjh());


        }
    }


    public ApiResponse<String> saveEntity(ClDd entity, String userId){
        SysJzgxx clJsy= jzgxxService.findById(userId);
        RuntimeCheck.ifNull(clJsy, "未找到记录");
        RuntimeCheck.ifBlank(entity.getHcdz(),"候车地址不能为空");
        RuntimeCheck.ifBlank(entity.getMdd(),"目的地不能为空");
        RuntimeCheck.ifBlank(entity.getCllx(),"车辆车型不能为空");
        RuntimeCheck.ifNull(entity.getZws(),"乘客人数不能为空");
        RuntimeCheck.ifNull(entity.getYysj(),"乘客预车时间不能为空");


		RuntimeCheck.ifFalse(entity.getOriginLat()!=null, "起始纬度不能为空");
		RuntimeCheck.ifFalse(entity.getOriginLng()!=null, "起始经度不能为空");
		RuntimeCheck.ifFalse(entity.getDestinationLat()!=null, "结束点纬度不能为空");
		RuntimeCheck.ifFalse(entity.getDestinationLng()!=null, "结束点经度不能为空");

        String orderId=genId();
        entity.setId(orderId);
//        entity.setCjr(userId);
        entity.setCkCjl(userId);//乘客创建人
        entity.setCk(clJsy.getXm());
        entity.setJgdm(clJsy.getJgdm());
        entity.setJgmc(clJsy.getJdmc());
        if (StringUtils.isEmpty(entity.getCklxdh())){
            if(clJsy.getSjhm()!=null){
                entity.setCklxdh(clJsy.getSjhm());
            }
        }
        RuntimeCheck.ifBlank(entity.getCklxdh(),"该职工未预留手机号码，所以暂不能约车");
        entity.setCjsj(new Date());
        entity.setFkzt("00"); // 未付款
        entity.setDdzt("10");//10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机确认(行程结束)；30-队长确认
        int i=entityMapper.insertSelective(entity);
        RuntimeCheck.ifTrue(i==0,"订单入库失败");

        ddrzService.log(entity);
        return ApiResponse.success();
    }

  public   ApiResponse<String> updateOrder(ClDd entity,String userId){

//        1、查找该ID是否存在
      ClDd clDd=findById(entity.getId());
      RuntimeCheck.ifNull(clDd,"未找到订单记录");
      String ddzt=clDd.getDdzt();
//        订单状态  10-订单创建；11-订单确认(待派单)；12-订单驳回；13-已派单；20-司机确认(行程结束)；30-队长确认; 40-财务已收

      if(StringUtils.equals(ddzt,"13")){
          ClJsy clJsy= jsyService.findById(userId);
          RuntimeCheck.ifFalse(StringUtils.equals(clDd.getSj(),clJsy.getSfzhm()),"您不能对非本人的订单进行操作");
      }else{
          RuntimeCheck.ifTrue(true,"订单状态异常，不能进行订单编辑操作");
      }

      ClDd newClDd=new ClDd();
      newClDd.setId(clDd.getId());
      newClDd.setSj(userId);//修改人
      newClDd.setXgsj(new Date());//修改时间
      newClDd.setGlf(entity.getGlf());//过路费
      newClDd.setGqf(entity.getGqf());//过桥费
      newClDd.setSy(entity.getSy());//事由
      newClDd.setSc(entity.getSc());//时长
      newClDd.setDj(entity.getDj());//单价
      newClDd.setLc(entity.getLc());//里程
      newClDd.setScf(entity.getScf());//时长费
      newClDd.setLcf(entity.getLcf());//里程费
      newClDd.setFkzt("00"); // 未付款
      newClDd.setDdzt("20");//订单状态
      newClDd.setSjqrsj(new Date());

      Map<String,Double>  retMap = overWorkMoney(newClDd);
      newClDd.setDj(retMap.get("dj"));//单价
      newClDd.setLcf(retMap.get("lcf"));//里程费
      newClDd.setJbfdj(String.valueOf(retMap.get("jbfdj")));//加班费单价
//			newClDd.setJbsc((short) retMap.get("jbsc").intValue());//加班时长(小时)
      newClDd.setJbf(String.valueOf(retMap.get("jbf")));//加班费
      newClDd.setJjrdj(String.valueOf(retMap.get("jjrdj")));//节假日单价
      newClDd.setJjrsc(String.valueOf(retMap.get("jjrsc")));//节假日时长
      newClDd.setJjrjl(String.valueOf(retMap.get("jjrjl")));//节假日金额


      Double zj=retMap.get("lcf")+retMap.get("jbf")+retMap.get("jjrjl")+(entity.getGlf()==null?0:entity.getGlf())+(entity.getGqf()==null?0:entity.getGqf());
      newClDd.setZj(zj);


//      double amount = MathUtil.mul(entity.getLc(),entity.getDj());
//      amount = MathUtil.add(amount,entity.getGqf());
//      amount = MathUtil.add(amount,entity.getGlf());
//      newClDd.setZj(amount);

      int i=update(newClDd);
      if(i==0){
          RuntimeCheck.ifFalse(false,"修改订单失败");
          return ApiResponse.fail("操作数据库失败");
      }else{
          ClDdrz clDdrz=new ClDdrz();
          clDdrz.setId(genId());//主键ID
          clDdrz.setDdId(clDd.getId());//订单ID
          clDdrz.setCjsj(new Date());//创建时间
          clDdrz.setCjr(userId);//创建人
          clDdrz.setJgdm(clDd.getJgdm());//机构代码
          clDdrz.setJgmc(clDd.getJgmc());//机构名称
          clDdrz.setDdzt(newClDd.getDdzt());//订单状态
          i=ddrzMapper.insertSelective(clDdrz);

          return ApiResponse.success();
      }
  }




    /**
     * 教职工 订单查询
     * @return
     */
    public ApiResponse<List<ClDd>> getOrderWorkersList(String userId){
        ApiResponse<List<ClDd>> result = new ApiResponse<List<ClDd>>();
        SimpleCondition condition = new SimpleCondition(ClDd.class);
        condition.eq(ClDd.InnerColumn.ckCjl.name(),userId);
        condition.setOrderByClause(ClDd.InnerColumn.yysj.desc());
        List<ClDd> orders = findByCondition(condition);
        List<String> carIds = orders.stream().map(ClDd::getClId).collect(Collectors.toList());
        condition = new SimpleCondition(ClCl.class);
        condition.in(ClCl.InnerColumn.clId,carIds);
        List<ClCl> cars = clMapper.selectByExample(condition);
        Map<String,ClCl> carMap = cars.stream().collect(Collectors.toMap(ClCl::getClId,p->p));
        for (ClDd order : orders) {
            String carId = order.getClId();
            if (StringUtils.isEmpty(carId))continue;
            ClCl car = carMap.get(carId);
            if (car == null)continue;
            order.setScs(car.getScs());
            order.setXh(car.getXh());
        }
        result.setResult(orders);
        return result;
    }
    /**
     * 列表 订单查询
     * @param type  2、待确认  3、历史单据
     * @return
     */
    public ApiResponse<List<ClDd>> getOrderDriverList(String userId, String type){
        ApiResponse<List<ClDd>> result = new ApiResponse<List<ClDd>>();
        SimpleCondition condition = new SimpleCondition(ClDd.class);
        condition.eq(ClDd.InnerColumn.sj.name(),userId);

        if(StringUtils.equals(type,"2")) {//待确认
            // 10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机完成行程(行程结束)；30-队长确认
            condition.eq(ClDd.InnerColumn.ddzt.name(),"13");
           condition.setOrderByClause(ClDd.InnerColumn.yysj.asc());
        }else if(StringUtils.equals(type,"3")) {//历史单据
            // 10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机完成行程(行程结束)；30-队长确认
            List<String> list= new ArrayList<String>();
            list.add("13");
            condition.notIn(ClDd.InnerColumn.ddzt.name(),list);
           condition.setOrderByClause(ClDd.InnerColumn.yysj.desc());
        }else{
            RuntimeCheck.ifTrue(true, "未找到记录");
        }


        List<ClDd> orgs = findByCondition(condition);

        result.setResult(orgs);
        return result;
    }

    @Override
    public ApiResponse<String> evaluate(String orderId, String grade,String pjnr) {
        RuntimeCheck.ifBlank(orderId,"请选择订单");
        RuntimeCheck.ifBlank(grade,"请输入评分");
        ClDd order = findById(orderId);
        RuntimeCheck.ifNull(order,"未找到订单");
        String userId = getCurrentUser(true);
        RuntimeCheck.ifFalse(StringUtils.equals(order.getCkCjl(),userId),"订单有误，稍后请重新尝试");
        ClDd newOrder = new ClDd();
        newOrder.setId(orderId);
        newOrder.setPjdj(new Short(grade));
        if(StringUtils.isNotEmpty(pjnr)){
            newOrder.setPjnr(pjnr);
        }
        entityMapper.updateByPrimaryKeySelective(newOrder);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Map<String, Object>> getStartPointAndEndPoint(String orderId) {
        RuntimeCheck.ifBlank(orderId,"请选择订单");
        ClDd order = entityMapper.selectByPrimaryKey(orderId);
        RuntimeCheck.ifNull(order,"订单不存在");
        String zdbh = order.getZdbm();
        RuntimeCheck.ifBlank(zdbh,"未指派车辆");
        SimpleCondition condition = new SimpleCondition(ClGpsLs.class);
        condition.eq(ClGpsLs.InnerColumn.zdbh,zdbh);
        condition.gte(ClGpsLs.InnerColumn.cjsj,order.getYysj());
        condition.setOrderByClause("cjsj asc");
        Map<String,Object> map = new HashMap<>();
        List<ClGpsLs> gpsLs1 = gpsLsMapper.selectByExampleAndRowBounds(condition,new RowBounds(0,1));
        if (gpsLs1.size() != 0){
            map.put("ksjd",gpsLs1.get(0).getBdjd());
            map.put("kswd",gpsLs1.get(0).getBdwd());
        }

        condition = new SimpleCondition(ClGpsLs.class);
        condition.eq(ClGpsLs.InnerColumn.zdbh,zdbh);
        condition.setOrderByClause("cjsj asc");
        condition.gte(ClGpsLs.InnerColumn.cjsj,order.getSjqrsj());
        List<ClGpsLs> gpsLs2 = gpsLsMapper.selectByExampleAndRowBounds(condition,new RowBounds(0,1));
        if (gpsLs2.size() != 0){
            map.put("jsjd",gpsLs2.get(0).getBdjd());
            map.put("jswd",gpsLs2.get(0).getBdwd());
        }

        if (map.size() >= 4){
            double d = DistanceUtil.getLongDistance(gpsLs1.get(0).getBdjd().doubleValue(),gpsLs1.get(0).getBdwd().doubleValue(),gpsLs2.get(0).getBdjd().doubleValue(),gpsLs2.get(0).getBdwd().doubleValue());
            map.put("distance",d);
            map.put("centerJd",(gpsLs1.get(0).getBdjd().add(gpsLs2.get(0).getBdjd())).divide(new BigDecimal(2),10,BigDecimal.ROUND_HALF_UP));
            map.put("centerWd",(gpsLs1.get(0).getBdwd().add(gpsLs2.get(0).getBdwd())).divide(new BigDecimal(2),10,BigDecimal.ROUND_HALF_UP));
        }
        return ApiResponse.success(map);
    }
    /**
     * 获取当前订单的订单  车辆GPS列表
     * @param orderId
     * @return
     */
    @Override
    public ApiResponse<List<Clyy>> getOrderGpsList(String orderId){
        RuntimeCheck.ifBlank(orderId,"请选择订单");
        ClDd clDd = entityMapper.selectByPrimaryKey(orderId);
        RuntimeCheck.ifNull(clDd,"订单不存在");

        RuntimeCheck.ifNull(clDd.getZdbm(),"暂无行程记录");

        RuntimeCheck.ifNull(clDd.getYysj(),"该行程还未开始");
        RuntimeCheck.ifNull(clDd.getSjqrsj(),"该行程还未结束");


        SimpleCondition condition = new SimpleCondition(Clyy.class);

        condition.and().andEqualTo(Clyy.InnerColumn.zdbh.name() , clDd.getZdbm());
        if(clDd.getYysj()!=null){
            String strinDate=DateUtils.getDateStr(clDd.getYysj(),"yyyy-MM-dd HH:mm:ss");
            condition.and().andGreaterThanOrEqualTo(Clyy.InnerColumn.loc_time.name() , strinDate);
        }
        if(clDd.getSjqrsj()!=null){
            String StrinDate= DateUtils.getDateStr(clDd.getSjqrsj(),"yyyy-MM-dd HH:mm:ss");
            condition.and().andLessThanOrEqualTo(Clyy.InnerColumn.loc_time.name() ,  StrinDate);
        }
        condition.setOrderByClause(" id ASC");

        List<Clyy> gpsList = clYyService.findByCondition(condition);

        RuntimeCheck.ifFalse(gpsList!=null&&gpsList.size()>0,"暂无行程记录");

        ApiResponse<List<Clyy>> ret=new ApiResponse<>();
        ret.setResult(gpsList);
        return ret;
    }



    /**
     * 获取出 该订单 里程单价[dj]、里程费[lcf]、加班单价[jbfdj]、加班时长(小时)[jbsc]、加班费[jbf]、 节假日单价[jjrdj]、节假日(天)[jjrsc] 、节假日金额()[jjrjl]
     * @param order
     * @return
     * dj		里程单价
     * lcf		里程费
     * jbfdj	加班单价
     * jbsc		加班时长(小时)
     * jbf		加班费
     * jjrdj	节假日单价
     * jjrsc	节假日
     * jjrjl	节假日金额
     */
    private Map<String,Double>  overWorkMoney(ClDd order){
        Map<String,Double> retMap=new HashMap<>();
        SimpleCondition condition = new SimpleCondition(SysHsgs.class);
        condition.eq(SysHsgs.InnerColumn.cx,order.getCllx());
        List<SysHsgs> hsgsList = hsgsMapper.selectByExample(condition);
        RuntimeCheck.ifTrue(hsgsList.size() == 0,"未找到核算公式");
        BigDecimal lcPrice= BigDecimal.valueOf(0);//里程单价
        BigDecimal festivalHlidayPrice= BigDecimal.valueOf(0);//节假日单价
        BigDecimal duration= BigDecimal.valueOf(0);//加班单价
        String nr = "";
        for(SysHsgs pric:hsgsList){
//			'00', '里程'   '10', '加班   20', '节假日'
            if(StringUtils.equals(pric.getLx(),"00")){//和里程参数计算
                lcPrice=pric.getJe();
            }else if(StringUtils.equals(pric.getLx(),"10")){//加班时间按小时
                duration=pric.getJe();
                nr=pric.getNr();
            }else if(StringUtils.equals(pric.getLx(),"20")){//节假日按天算
                festivalHlidayPrice=pric.getJe();
            }
        }
        RuntimeCheck.ifTrue(StringUtils.equals(order.getCllx(),"10") && lcPrice.compareTo(BigDecimal.ZERO)==0,"您好，请配置小车的里程单价");
        long durationMs = 0;//加班秒数
        long festivalHlidayDay=0;//节假日的天数
        List<Map<String,Date>> dateList = splitDate(order.getYysj(),order.getSjqrsj());
        for (Map<String, Date> map : dateList) {
            Date startTime = map.get("startTime");
            String startTimeStr = DateUtils.getDateStr(startTime,"yyyy-MM-dd");
            SimpleCondition rlCond = new SimpleCondition(SysRlb.class);
            rlCond.eq(SysRlb.InnerColumn.rq,startTimeStr);
            List<SysRlb> rlbs = rlbMapper.selectByExample(rlCond);
            RuntimeCheck.ifEmpty(rlbs,"未找到日历");
            SysRlb rlb = rlbs.get(0);
            //判断是否为节假日
            if (!"1".equals(rlb.getZt().trim())){
                festivalHlidayDay++;
                continue;
            }
            durationMs += getExtraTime(nr,map.get("startTime"),map.get("endTime"));
        }
        order.setJbsc(new BigDecimal(durationMs/(1000*60*60)).shortValue());//加班时长
        entityMapper.updateByPrimaryKeySelective(order);

        retMap.put("dj",lcPrice.doubleValue());//里程单价
        BigDecimal amount = lcPrice.multiply(new BigDecimal(order.getLc()));
        retMap.put("lcf",amount.doubleValue());//里程费
        retMap.put("jbfdj",duration.doubleValue());//加班单价
        retMap.put("jbsc", Double.valueOf(order.getJbsc()));//加班时长(小时)
        try {
            retMap.put("jbf",duration.doubleValue()*Double.valueOf(order.getJbsc()));
        }catch (Exception e){
            retMap.put("jbf",0D);//加班费
        }

        retMap.put("jjrdj",festivalHlidayPrice.doubleValue());//节假日单价
        retMap.put("jjrsc", Double.valueOf(festivalHlidayDay));//节假日

        try {
            retMap.put("jjrjl",festivalHlidayPrice.doubleValue()*festivalHlidayDay);//节假日金额
        }catch (Exception e){
            retMap.put("jjrjl",0d);
        }
        return retMap;
    }

    /**
     *	将时间段拆分为天数  这个有问题。跨度时间过长， 2018-09-23 到 2018-09-24 只计算出两天-BUG。todo
     * @param startTime
     * @param endTime
     * @return
     */
    private static List<Map<String,Date>> splitDate(Date startTime,Date endTime){
        int startDate = startTime.getDate();//获取天数
        int endDate = endTime.getDate();//
        List<Map<String,Date>> list = new ArrayList<>();
        if (startDate == endDate){
            Map<String,Date> map = new HashMap<>();
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            list.add(map);
            return list;
        }
        for (int i = startDate; i <= endDate; i++){
            Map<String,Date> map = new HashMap<>();
            Date sTime = null;
            Date eTime = null;
            if (i == startDate){
                sTime = startTime;
            }else if (i == endDate){
                eTime = endTime;
            }
            if (sTime == null){
                sTime = new Date();
                sTime.setDate(i);
                sTime.setHours(0);
                sTime.setMinutes(0);
                sTime.setSeconds(0);
            }
            if (eTime == null){
                eTime = new Date();
                eTime.setDate(i);
                eTime.setHours(23);
                eTime.setMinutes(59);
                eTime.setSeconds(59);
            }
            map.put("startTime",sTime);
            map.put("endTime",eTime);
            list.add(map);
        }
        return list;
    }

    private static long getExtraTime(String workTimeStr,Date orderStartTime,Date orderEndTime){
        String[] times = workTimeStr.split(",");
        String time0 = times[0];
        Date midTime = new Date();
        String midEndTime = time0.split("-")[1];
        int midEndHour = Integer.parseInt(midEndTime.split(":")[0]);
        int midEndMinute = Integer.parseInt(midEndTime.split(":")[1]);
        midTime.setHours(midEndHour);
        midTime.setMinutes(midEndMinute);
        midTime.setSeconds(0);
        long duration = 0;
        if (orderEndTime.getTime() - midTime.getTime() > 0){
            duration += getPartExtraTime(times[0],orderStartTime,midTime,0);
            duration += getPartExtraTime(times[1],midTime,orderEndTime,1);
        }else{
            duration += getPartExtraTime(times[0],orderStartTime,midTime,0);
        }
        return duration;
    }

    private static long getPartExtraTime(String workTimeStr,Date orderStartTime,Date orderEndTime,int i){
        String[] workTimeArray = workTimeStr.split("-");
        String startWorkTime = workTimeArray[0];
        String endWorkTime = workTimeArray[1];

        String[] startWorkTimeArr = startWorkTime.split(":");
        int workStartHour = Integer.parseInt(startWorkTimeArr[0]);
        int workStartMinute = Integer.parseInt(startWorkTimeArr[1]);

        long duration = 0;

        Date workStartTime = new Date(orderStartTime.getTime());
        workStartTime.setHours(workStartHour);
        workStartTime.setMinutes(workStartMinute);
        workStartTime.setSeconds(0);
        long duration1 = workStartTime.getTime() - orderStartTime.getTime();
        if (duration1 > 0)duration += duration1;

        if (i == 1){
            String[] endWorkTimeArr = endWorkTime.split(":");
            int workEndHour = Integer.parseInt(endWorkTimeArr[0]);
            int workEndMinute = Integer.parseInt(endWorkTimeArr[1]);
            Date workEndTime = new Date(orderEndTime.getTime());
            workEndTime.setHours(workEndHour);
            workEndTime.setMinutes(workEndMinute);
            workEndTime.setSeconds(0);
            long duration2 = orderEndTime.getTime() - workEndTime.getTime();
            if (duration2 > 0){
                duration += duration2;
            }
        }
        return duration;
    }



}
