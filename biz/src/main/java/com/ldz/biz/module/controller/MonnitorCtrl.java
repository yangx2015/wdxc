package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.*;
import com.ldz.biz.module.service.*;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("monitor")
@RestController
public class MonnitorCtrl {

    @Autowired
    private ZdglService zdglService;
    @Autowired
    private PbService pbService;
    @Autowired
    private XlService xlService;
    @Autowired
    private ZdService zdService;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private ClService clService;
    /**
     * 校巴监控初始化数据
     * 1.获取所有设备信息
     * 2.获取线路信息
     * 3.获取站点信息
     * @return
     */
    @GetMapping("initBusInfo")
    public ApiResponse<Map<String,Object>> initBusInfo(){
        Map<String,Object> resultMap = new HashMap<>();
        List<ClZdgl> deviceList = new ArrayList<>();
        SimpleCondition pbCondition = new SimpleCondition(ClPb.class);
        pbCondition.and().andCondition(" to_char(pbsj,'yyyy-MM-dd') like '" + DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + "'");
        List<ClPb> clPbs = pbService.findByCondition(pbCondition);
        if (clPbs.size() != 0){
            List<String> collect = clPbs.stream().map(ClPb::getClId).collect(Collectors.toList());
            List<ClCl> cls = clService.findIn(ClCl.InnerColumn.clId,collect);
            List<String> zdbhs = cls.stream().map(ClCl::getZdbh).collect(Collectors.toList());

            SimpleCondition deviceCondition = new SimpleCondition(ClZdgl.class);
            deviceCondition.in(ClZdgl.InnerColumn.zdbh,zdbhs);
            deviceList = zdglService.findByCondition(deviceCondition);
        }
        resultMap.put("deviceList",deviceList);

        List<ClXl> busRouteList = xlService.findEq(ClXl.InnerColumn.lx,"30");
        List<ClXl> routeList = new ArrayList<>(busRouteList.size());
        if (busRouteList.size() != 0){
            List<String> routeIds = busRouteList.stream().map(ClXl::getId).collect(Collectors.toList());
            List<ClXlzd> routeStationList = xlzdService.findIn(ClXlzd.InnerColumn.xlId,routeIds);
            if (routeStationList.size() != 0){
                List<String> stationIds = routeStationList.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
                List<ClZd> allStationList = zdService.findIn(ClZd.InnerColumn.id,stationIds);

                for (ClXl route : busRouteList) {
                    List<ClXlzd> routeStations = routeStationList.stream().filter(p->p.getXlId().equals(route.getId())).collect(Collectors.toList());
                    List<String> stationIdList = routeStationList.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
                    Map<String,Short> orderMap = routeStations.stream().collect(Collectors.toMap(ClXlzd::getZdId,ClXlzd::getXh));
                    List<ClZd> stationList = allStationList.stream().filter(p->stationIdList.contains(p.getId())).collect(Collectors.toList());
                    if(StringUtils.equals(route.getId(),"435390953470033920")){//工学部增加两个标点
                        ClZd zd1=new ClZd();//辅助点
                        zd1.setId("5023163305573224641");
                        zd1.setMc("辅助点-0001");
                        zd1.setJd(114.372848);
                        zd1.setWd(30.54922);
                        zd1.setJgdm("100");
                        zd1.setJgmc("武汉大学");
                        zd1.setZt("00");
                        zd1.setLx("30");
                        zd1.setRouteOrder((short) 10);
                        ClZd zd2=new ClZd();//辅助点
                        zd2.setId("5023163305573224642");
                        zd2.setMc("辅助点-0002");
                        zd2.setJd(114.370386);
                        zd2.setWd(30.548991);
                        zd2.setJgdm("100");
                        zd2.setJgmc("武汉大学");
                        zd2.setZt("00");
                        zd2.setLx("30");
                        zd2.setRouteOrder((short) 11);
                        for (ClZd station : stationList) {
                            Short seq =orderMap.get(station.getId());
                            if(seq!=null&&seq>9){
                                int i=seq+2;
                                station.setRouteOrder((short) i);
                            }else{
                                station.setRouteOrder(seq);
                            }
                        }
                        stationList.add(zd1);
                        stationList.add(zd2);
                    }else{
                        for (ClZd station : stationList) {
                            station.setRouteOrder(orderMap.get(station.getId()));
                        }
                    }
                    stationList.sort(Comparator.comparingInt(ClZd::getRouteOrder));
                    route.setStationList(stationList);
                    routeList.add(route);
                }
            }
        }
        resultMap.put("routeList",routeList);
        return ApiResponse.success(resultMap);
    }
}
