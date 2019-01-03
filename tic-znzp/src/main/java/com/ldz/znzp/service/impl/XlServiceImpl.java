package com.ldz.znzp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.bean.Bus;
import com.ldz.znzp.bean.Route;
import com.ldz.znzp.bean.RouteInfo;
import com.ldz.znzp.bean.Station;
import com.ldz.znzp.mapper.ClXlMapper;
import com.ldz.znzp.mapper.ClZnzpMapper;
import com.ldz.znzp.mapper.ClZpXlMapper;
import com.ldz.znzp.model.*;
import com.ldz.znzp.service.*;
import com.ldz.znzp.util.NettyUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XlServiceImpl extends BaseServiceImpl<ClXl, String> implements XlService {
    Logger errorLog = LoggerFactory.getLogger("error_info");
    @Autowired
    private ClXlMapper entityMapper;
    @Autowired
    private ClZnzpMapper znzpMapper;
    @Autowired
    private ZnzpService znzpService;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private XlService xlService;
    @Autowired
    private ZdService zdService;
    @Autowired
    private PbService pbService;
    @Autowired
    private ClyxjlService clyxjlService;
    @Autowired
    private ZdglService zdglService;
    @Autowired
    private NettyUtil nettyUtil;
    @Autowired
    private ClZpXlMapper zpXlMapper;
    @Autowired
    private RedisTemplateUtil redisDao;

    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    protected Mapper<ClXl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return ClXl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public List<ClXl> getByZdId(String zdId) {
        List<ClXlzd> xlzds = xlzdService.findEq(ClXlzd.InnerColumn.zdId, zdId);
        if (xlzds.size() == 0) return new ArrayList<>();
        List<String> xlIds = xlzds.stream().map(ClXlzd::getXlId).collect(Collectors.toList());
        return xlService.findIn(ClXl.InnerColumn.id, xlIds);
    }

    private List<ClXl> getXls(String zpId) {
        SimpleCondition condition = new SimpleCondition(ClZpXl.class);
        condition.eq(ClZpXl.InnerColumn.zpId, zpId);
        condition.setOrderByClause(ClZpXl.InnerColumn.id.asc());// 按ID排序
        List<ClZpXl> zpXlList = zpXlMapper.selectByExample(condition);
        if (zpXlList.size() == 0) return new ArrayList<>();
        List<String> xlIds = zpXlList.stream().map(ClZpXl::getXlId).collect(Collectors.toList());
        List<ClXl> list = xlService.findIn(ClXl.InnerColumn.id, xlIds);
        List<ClXl> newList = new ArrayList<>(list.size());
        if (list != null && list.size() > 0) {
            Map<String, ClXl> zdMap = list.stream().collect(Collectors.toMap(ClXl::getId, p -> p));
            for (ClZpXl obj : zpXlList) {
                newList.add(zdMap.get(obj.getXlId()));
            }
            return newList;
        }
        return list;
    }

    /**
     * 根据终端id获取 站点线路，线路站点
     *
     * @param ctx
     * @param tid
     * @return
     */
    @Override
    public ApiResponse<String> getRouterInfo(ChannelHandlerContext ctx, String tid) {
        ApiResponse result = new ApiResponse();
        // 获取站牌信息
        ClZnzp zp = znzpMapper.selectByPrimaryKey(tid);
        if (zp == null) {
            result = ApiResponse.fail("未找到站牌信息");
            nettyUtil.sendData(ctx, result);
            return result;
        }


        // 获取站点线路
        List<ClXl> xls = getXls(zp.getZdbh());
        if (xls.size() == 0) {
            result = ApiResponse.fail("未找到线路信息");
            nettyUtil.sendData(ctx, result);
            return result;
        }

        // 获取线路站点
        List<String> xlIds = xls.stream().map(ClXl::getId).collect(Collectors.toList());
        List<ClXlzd> xlzds = xlzdService.findIn(ClXlzd.InnerColumn.xlId, xlIds);

        Map<String, List<Station>> xlZdMap = null;
        if (xlIds.size() != 0) {
            xlZdMap = new HashMap<>(xlIds.size());
            List<String> zdIds = xlzds.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
            List<ClZd> zds = zdService.findIn(ClZd.InnerColumn.id, zdIds);
            Map<String, ClZd> zdMap = zds.stream().collect(Collectors.toMap(ClZd::getId, p -> p));

            for (ClXlzd xlzd : xlzds) {
                String xlId = xlzd.getXlId();
                String zdId = xlzd.getZdId();
                if (StringUtils.isEmpty(xlId) || StringUtils.isEmpty(zdId)) continue;
                ClZd zd = zdMap.get(zdId);
                zd.setXlId(xlId);
                zdService.setStationOrder(zd);

                String mc=zd.getMc();
                try {
                    mc=StringUtils.remove(mc,"-回行");
                }catch (Exception e){}
                zd.setMc(mc);
                Station station = new Station(zd);
                if (xlZdMap.containsKey(xlId)) {
                    xlZdMap.get(xlId).add(station);
                } else {
                    List<Station> list = new ArrayList<>();
                    list.add(station);
                    xlZdMap.put(xlId, list);
                }
            }
        }


        // 数据封装
        RouteInfo routeInfo = new RouteInfo();
        routeInfo.setTid(tid);

        String mc=zp.getMc();
        try {
            mc=StringUtils.remove(mc,"-回行");//
        }catch (Exception e){}

        routeInfo.setShowName(mc);
        List<Route> routes = new ArrayList<>(xlIds.size());
        for (ClXl xl : xls) {
            Route route = new Route(xl);
            routes.add(route);
            if (xlZdMap != null) {
                route.setStations(xlZdMap.get(xl.getId()));
            }
            List<Bus> buses = getBusList(xl);
            route.setBuses(buses);

        }
        routeInfo.setRoutes(routes);
        nettyUtil.sendData(ctx, routeInfo);
        return ApiResponse.success(routeInfo.toString());
    }

    public ApiResponse<String> getRouterInfo(Channel channel, String tid) {
        ApiResponse result = new ApiResponse();
        // 获取站牌信息
        ClZnzp zp = znzpMapper.selectByPrimaryKey(tid);
        if (zp == null) {
            result = ApiResponse.fail("未找到站牌信息");
            nettyUtil.sendData(channel, result);
            return result;
        }


        // 获取站点线路
        List<ClXl> xls = getXls(zp.getZdbh());
        if (xls.size() == 0) {
            result = ApiResponse.fail("未找到线路信息");
            nettyUtil.sendData(channel, result);
            return result;
        }

        // 获取线路站点
        List<String> xlIds = xls.stream().map(ClXl::getId).collect(Collectors.toList());
        List<ClXlzd> xlzds = xlzdService.findIn(ClXlzd.InnerColumn.xlId, xlIds);

        Map<String, List<Station>> xlZdMap = null;
        if (xlIds.size() != 0) {
            xlZdMap = new HashMap<>(xlIds.size());
            List<String> zdIds = xlzds.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
            List<ClZd> zds = zdService.findIn(ClZd.InnerColumn.id, zdIds);
            Map<String, ClZd> zdMap = zds.stream().collect(Collectors.toMap(ClZd::getId, p -> p));

            for (ClXlzd xlzd : xlzds) {
                String xlId = xlzd.getXlId();
                String zdId = xlzd.getZdId();
                if (StringUtils.isEmpty(xlId) || StringUtils.isEmpty(zdId)) continue;
                ClZd zd = zdMap.get(zdId);
                zd.setXlId(xlId);
                zdService.setStationOrder(zd);
                String mc=zd.getMc();
                try {
                    mc=StringUtils.remove(mc,"-回行");
                }catch (Exception e){}
                zd.setMc(mc);

                Station station = new Station(zd);
                if (xlZdMap.containsKey(xlId)) {
                    xlZdMap.get(xlId).add(station);
                } else {
                    List<Station> list = new ArrayList<>();
                    list.add(station);
                    xlZdMap.put(xlId, list);
                }
            }
        }


        // 数据封装
        RouteInfo routeInfo = new RouteInfo();
        routeInfo.setTid(tid);
        String mc=zp.getMc();
        try {
            mc=StringUtils.remove(mc,"-回行");
        }catch (Exception e){}

        routeInfo.setShowName(mc);
        List<Route> routes = new ArrayList<>(xlIds.size());
        for (ClXl xl : xls) {
            Route route = new Route(xl);
            routes.add(route);
            if (xlZdMap != null) {
                route.setStations(xlZdMap.get(xl.getId()));
            }
            List<Bus> buses = getBusList(xl);
            route.setBuses(buses);

        }
        routeInfo.setRoutes(routes);
        nettyUtil.sendData(channel, routeInfo);
        return ApiResponse.success(routeInfo.toString());
    }

    private List<Bus> getBusList(ClXl xl) {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        SimpleCondition condition = new SimpleCondition(ClClyxjl.class);
        condition.eq(ClClyxjl.InnerColumn.xlId, xl.getId());
        condition.gte(ClClyxjl.InnerColumn.cjsj, today);
        List<ClClyxjl> clClyxjls = clyxjlService.findByCondition(condition);
        if (clClyxjls.size() == 0) return new ArrayList<>();

        List<String> allZdbh = clClyxjls.stream().map(ClClyxjl::getSzdbh).collect(Collectors.toList());
        List<String> onlineZdbh = new ArrayList<>();
        SimpleCondition condition1 = new SimpleCondition(ClZdgl.class);
        condition1.in(ClZdgl.InnerColumn.zdbh, allZdbh);
        condition1.eq(ClZdgl.InnerColumn.zxzt, "00");
        List<ClZdgl> zdgls = zdglService.findByCondition(condition1);
        if (zdgls.size() == 0) {
            return new ArrayList<>();
        }
        onlineZdbh = zdgls.stream().map(ClZdgl::getZdbh).collect(Collectors.toList());

        List<Bus> buses = new ArrayList<>();
        for (ClClyxjl clClyxjl : clClyxjls) {
            // 排除不在线的车辆
            if (!onlineZdbh.contains(clClyxjl.getSzdbh())) continue;
            Bus bus = new Bus();
            bus.setId(clClyxjl.getClId());
            bus.setCurrentStationNo("" + clClyxjl.getZdbh());
            bus.setPlate(clClyxjl.getCphm());
            bus.setState(clClyxjl.getZt());
            buses.add(bus);
        }
        return buses;
    }

    @Override
    public ClXl getByCarId(ClPb clPb) {
        String xlId = clPb.getXlId();
        return xlService.findById(xlId);
    }

    @Override
    public void checkRouteInfo(String xlId) {
        errorLog.error("收到消息:线路ID为："+xlId);

        SimpleCondition condition = new SimpleCondition(ClClyxjl.class);
        condition.eq(ClClyxjl.InnerColumn.xlId,xlId);
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        condition.gte(ClClyxjl.InnerColumn.cjsj,today);
        condition.eq(ClClyxjl.InnerColumn.xlId,xlId);
        condition.and().andNotEqualTo(ClClyxjl.InnerColumn.zt.name(),"off");
        condition.setOrderByClause(ClClyxjl.InnerColumn.cjsj.asc());
        List<ClClyxjl> clClyxjls = clyxjlService.findByCondition(condition);

        String carNumStr = "";
        try {
            carNumStr = (String) redisDao.boundValueOps("xlCarNum-" + xlId).get();
        } catch (Exception e) {
            errorLog.error("报错啦");
            e.printStackTrace();
        }
        errorLog.error("收到消息:2："+carNumStr);
        int carNum = StringUtils.isEmpty(carNumStr) ? 0 : Integer.parseInt(carNumStr);
        errorLog.error("收到消息:3："+clClyxjls.size());
        if (carNum != clClyxjls.size()) {
            List<ClZnzp> znzps = znzpService.getByXlId(xlId);
            if (znzps.size() == 0) return;
            List<String> zpIds = znzps.stream().map(ClZnzp::getZdbh).collect(Collectors.toList());
            Map<String, Object> channelMap = nettyUtil.getChannelByTids(zpIds);
            if(channelMap!=null){
                for (Map.Entry<String, Object> entry : channelMap.entrySet()) {
                    Channel channel = (Channel) entry.getValue();
                    xlService.getRouterInfo(channel, entry.getKey());
                }
            }
        }
        redisDao.boundValueOps("xlCarNum-" + xlId).set(clClyxjls.size()+"");
    }

    /**
     * "ZNZP_XL_{{xlId}}"
     * 通过线路ID获取线路明细
     * @param xlId
     * @return
     */
    @Override
    public ClXl getCarXlfindById(String xlId){
        ClXl clXl =null;
        String redisValue="";
        try {
            redisValue= (String) redisDao.boundValueOps("ZNZP_XL_"+xlId).get();
            if(StringUtils.equals(redisValue,"-")){
                return null;
            }
            try {
                clXl= mapper.readValue(redisValue,ClXl.class);
            } catch (Exception e) {
//                loge.error("ZNZP_XL_json转换异常",e);
                e.printStackTrace();
            }
            if(clXl==null){
                clXl= findById(xlId);
                try {
                    if(clXl!=null){
                        redisValue= mapper.writeValueAsString(clXl);
                    }
                } catch (JsonProcessingException e) {
                }

                if(StringUtils.isNotEmpty(redisValue)){
                    String endTime =DateUtils.getToday("yyyy-MM-dd")+" 23:59:59";//拼接出失效时间
                    long interval = (DateUtils.getDate(endTime,"yyyy-MM-dd HH:mm:ss").getTime() - new Date().getTime())/1000;
                    redisDao.boundValueOps("ZNZP_XL_"+xlId).set(redisValue,interval, TimeUnit.SECONDS);//当天23.59.59秒失效
                }else{
                    redisDao.boundValueOps("ZNZP_XL_"+xlId).set("-",1*60,TimeUnit.SECONDS);//当没有查询到这条记录。系统默认1分钟后再查
                }
                log.info("通过线路ID获取线路明细(ZNZP_XL_{{xlId}})_从数据库中获取"+redisValue+" 。");
            }else{
                log.info("通过线路ID获取线路明细(ZNZP_XL_{{xlId}})_从redis中获取"+redisValue+" 。");
            }
        }catch (Exception e){}
        return clXl;
    }

}
