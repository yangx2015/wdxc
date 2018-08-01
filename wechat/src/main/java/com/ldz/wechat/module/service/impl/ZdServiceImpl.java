package com.ldz.wechat.module.service.impl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.base.LimitedCondition;
import com.ldz.wechat.module.mapper.ClZdMapper;
import com.ldz.wechat.module.model.ClXl;
import com.ldz.wechat.module.model.ClXlzd;
import com.ldz.wechat.module.model.ClZd;
import com.ldz.wechat.module.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZdServiceImpl extends BaseServiceImpl<ClZd,String> implements ZdService {
    @Autowired
    private ClZdMapper entityMapper;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClyxjlService clyxjlService;
    @Autowired
    private ZdService zdService;
    @Autowired
    private XlService xlService;

    @Override
    protected Mapper<ClZd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZd.class;
    }



    private Map<String,Object> stationToMap(ClZd zd,Double distance){
        Map<String,Object> station = new HashMap<>();
        station.put("id",zd.getId());
        station.put("xlId",zd.getXlId());
        station.put("name",zd.getMc());
        station.put("distance",distance.intValue());
        station.put("lng",zd.getJd());
        station.put("lat",zd.getWd());
        station.put("routerList",new ArrayList());
        return station;
    }

    private Map<String,Object> routerToMap(ClXl xl){
        Map<String,Object> router = new HashMap<>();
        router.put("id",xl.getId());
        router.put("name",xl.getXlmc());
        router.put("startTime",xl.getYxkssj());
        router.put("endTime",xl.getYxjssj());
        router.put("endStation",getEndStation(xl.getId()));
        router.put("direct",xl.getYxfs());
        return router;
    }

    private void setStationOrder(ClZd station) {
        if (StringUtils.isEmpty(station.getId()) || StringUtils.isEmpty(station.getXlId())){
            return;
        }
        SimpleCondition condition = new SimpleCondition(ClXlzd.class);
        condition.eq(ClXlzd.InnerColumn.zdId,station.getId());
        condition.eq(ClXlzd.InnerColumn.xlId,station.getXlId());
        List<ClXlzd> xlzds = xlzdService.findByCondition(condition);
        if (xlzds.size() == 0)return;
        station.setRouteOrder(xlzds.get(0).getXh());
    }

    private Short getStationOrder(String xlId,String zdId){
        if (StringUtils.isEmpty(xlId) || StringUtils.isEmpty(zdId)){
            return null;
        }
        SimpleCondition condition = new SimpleCondition(ClXlzd.class);
        condition.eq(ClXlzd.InnerColumn.zdId,zdId);
        condition.eq(ClXlzd.InnerColumn.xlId,xlId);
        List<ClXlzd> xlzds = xlzdService.findByCondition(condition);
        if (xlzds.size() == 0)return null;
        return xlzds.get(0).getXh();
    }
    private ClZd getEndStation(String xlId){
        List<ClXlzd> xlzds = xlzdService.findEq(ClXlzd.InnerColumn.xlId,xlId);
        if (xlzds.size() == 0)return null;
        List<String> zdIds = xlzds.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
        List<ClZd> zds = zdService.findIn(ClZd.InnerColumn.id,zdIds);
        Map<String,ClZd> zdMap = zds.stream().collect(Collectors.toMap(ClZd::getId,p->p));
        if (zds.size() == 0)return null;
        for (ClXlzd xlzd : xlzds) {
            String zdId = xlzd.getZdId();
            if (StringUtils.isEmpty(zdId))continue;
            ClZd zd = zdMap.get(zdId);
            if (zd == null)continue;
            zd.setXlId(xlzd.getXlId());
        }
        for (ClZd zd : zds) {
            setStationOrder(zd);
        }
        zds.sort(Comparator.comparingInt(p-> (int)p.getRouteOrder()));
        return zds.get(zds.size() - 1);
    }

    @Override
    public ApiResponse<Map<String, Object>> getStationInfo(String lng, String lat) {
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> nearbyStations = new ArrayList<>(); // 附近站点
        List<Map<String,Object>> nearbyRouters = new ArrayList<>();
        List<Map<String,Object>> otherRouters = new ArrayList<>(); // 其他线路
        resultMap.put("nearbyStations",nearbyStations);
        resultMap.put("otherRouters",otherRouters);

        // 查找附近站点
        if (StringUtils.isNotEmpty(lng) && StringUtils.isNotEmpty(lat)){
            LimitedCondition condition = new LimitedCondition(ClZd.class);
            List<ClZd> stationList = zdService.findByCondition(condition);
            Map<String,Double> distanceMap = new HashMap<>(stationList.size());
            double maxDistance = 100D;
            for (ClZd zd : stationList) {
                setStationOrder(zd);
                double distance = DistanceUtil.getShortDistance(zd.getJd(),zd.getWd(),new Double(lng),new Double(lat));
                distanceMap.put(zd.getId(),distance);
                if (distance < maxDistance){
                    Map<String,Object> station = stationToMap(zd, distance);
                    nearbyStations.add(station);
                }
            }

            // 如果没有附近站点，则选取最近的一个站点
            if (nearbyStations.size() == 0){
                Map<String,ClZd> stationMap = stationList.stream().collect(Collectors.toMap(ClZd::getId,p->p));
                List<Map.Entry<String,Double>> entryList = new ArrayList<>(distanceMap.entrySet());
                entryList.sort((o1, o2) -> {
                    Double v1 = o1.getValue();
                    Double v2 = o2.getValue();
                    return new Double(v1 - v2).intValue();
                });
                String id = entryList.get(0).getKey();
                Map<String,Object> station = stationToMap(stationMap.get(id),distanceMap.get(id));
                nearbyStations.add(station);
            }
        }

        List<String> nearbyStationIds = new ArrayList<>();
        for (Map<String, Object> station : nearbyStations) {
            String id = (String) station.get("id");
            nearbyStationIds.add(id);
        }

        // 获取线路信息
        LimitedCondition condition1 = new LimitedCondition(ClXl.class);
        List<ClXl> allOrgRouters = xlService.findByCondition(condition1);
        List<ClXlzd> xlzds;
        if (nearbyStationIds.size() == 0){
            xlzds = new ArrayList<>();
        }else{
            xlzds = xlzdService.findIn(ClXlzd.InnerColumn.zdId,nearbyStationIds);
        }
        List<String> nearbyRouterIds;
        if (xlzds.size() != 0){
            nearbyRouterIds = xlzds.stream().map(ClXlzd::getXlId).collect(Collectors.toList());
            setRouterIds(nearbyStations,xlzds);
        }else{
            nearbyRouterIds = new ArrayList<>();
        }
        for (ClXl router : allOrgRouters) {
            Map<String,Object> map = routerToMap(router);
            if (nearbyRouterIds.contains(router.getId())){
                nearbyRouters.add(map);
            }else{
                otherRouters.add(map);
            }
        }
        for (Map<String, Object> nearbyRouter : nearbyRouters) {
            String routerId = (String) nearbyRouter.get("id");
            if (StringUtils.isEmpty(routerId))continue;
            Map<String,Object> station = getStationByXlId(routerId,nearbyStations);
            if (station == null)continue;
            Short order = getStationOrder(((String) nearbyRouter.get("id")),station.get("id").toString());
            nearbyRouter.put("order",order);
            List<Map<String,Object>> routerList = (List<Map<String, Object>>) station.get("routerList");
            ApiResponse<List<Integer>> nsRes = xlService.getNextCars(routerId,station.get("id").toString());
            if (nsRes.isSuccess() && nsRes.getResult() != null){
                nearbyRouter.put("nextBus",nsRes.getResult());
            }
            routerList.add(nearbyRouter);
        }
        return ApiResponse.success(resultMap);
    }
    private Map<String,Object> getStationByXlId(String xlId,List<Map<String,Object>> nearbyStations){
        for (Map<String, Object> station : nearbyStations) {
            String xlid = (String) station.get("xlId");
            if (xlId.equals(xlid)){
                return station;
            }
        }
        return null;
    }

    private void setRouterIds(List<Map<String,Object>> list,List<ClXlzd> xlzds){
        for (Map<String, Object> map : list) {
            String zdId = map.get("id").toString();
            String xlId = getXlId(xlzds,zdId);
            map.put("xlId",xlId);
        }
    }

    private String getXlId(List<ClXlzd> xlzds,String zdId){
        for (ClXlzd xlzd : xlzds) {
            if (zdId.equals(xlzd.getZdId())){
                return xlzd.getXlId();
            }
        }
        return null;
    }
}

