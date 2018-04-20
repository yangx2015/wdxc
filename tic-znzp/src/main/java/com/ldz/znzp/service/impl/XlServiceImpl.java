package com.ldz.znzp.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.bean.Bus;
import com.ldz.znzp.bean.Route;
import com.ldz.znzp.bean.RouteInfo;
import com.ldz.znzp.bean.Station;
import com.ldz.znzp.mapper.ClPbMapper;
import com.ldz.znzp.mapper.ClXlMapper;
import com.ldz.znzp.mapper.ClZnzpMapper;
import com.ldz.znzp.model.*;
import com.ldz.znzp.service.*;
import com.ldz.znzp.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class XlServiceImpl extends BaseServiceImpl<ClXl,String> implements XlService{
    @Autowired
    private ClXlMapper entityMapper;
    @Autowired
    private ClZnzpMapper znzpMapper;
    @Autowired
    private ZnzpService znzpService;
    @Autowired
    private ZpXlService zpXlService;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private XlService xlService;
    @Autowired
    private ZdService zdService;
    @Autowired
    private ClService clService;
    @Autowired
    private ClPbMapper clPbMapper;
    @Autowired
    private ClyxjlService clyxjlService;
    @Autowired
    private NettyUtil nettyUtil;

    @Override
    protected Mapper<ClXl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public List<ClXl> getByZdId(String zdId) {
        List<ClXlzd> xlzds = xlzdService.findEq(ClXlzd.InnerColumn.zdId,zdId);
        if (xlzds.size() == 0)return new ArrayList<>();
        List<String> xlIds = xlzds.stream().map(ClXlzd::getXlId).collect(Collectors.toList());
        return xlService.findIn(ClXl.InnerColumn.id,xlIds);
    }

    /**
     * 根据终端id获取 站点线路，线路站点
     * @param ctx
     * @param tid
     * @return
     */
	@Override
	public ApiResponse<String> getRouterInfo(ChannelHandlerContext ctx, String tid) {
        ApiResponse result = new ApiResponse();
        // 获取站牌信息
        ClZnzp zp = znzpMapper.selectByPrimaryKey(tid);
        if (zp == null){
            result = ApiResponse.fail("未找到站牌信息");
            nettyUtil.sendData(ctx,result);
            return result;
        }

        // 获取站点线路
        List<ClXl> xls = getByZdId(zp.getZdId());
        if (xls.size() == 0){
            result = ApiResponse.fail("未找到线路信息");
            nettyUtil.sendData(ctx,result);
            return result;
        }

        // 获取线路站点
        List<String> xlIds = xls.stream().map(ClXl::getId).collect(Collectors.toList());
        List<ClZd> zds = zdService.getByXlIds(xlIds);
        Map<String,List<Station>> xlZdMap = new HashMap<>(xlIds.size());
        for (ClZd clZd : zds) {
            zdService.setStationOrder(clZd);
            String xlId = clZd.getXlId();
            Station station = new Station(clZd);
            if (xlZdMap.containsKey(xlId)){
                xlZdMap.get(xlId).add(station);
            }else{
                List<Station> list = new ArrayList<>();
                list.add(station);
                xlZdMap.put(xlId,list);
            }
        }

        // 数据封装
        RouteInfo routeInfo = new RouteInfo();
        routeInfo.setTid(tid);
        List<Route> routes = new ArrayList<>(xlIds.size());
        for (ClXl xl : xls) {
            Route route = new Route(xl);
            routes.add(route);
            route.setStations(xlZdMap.get(xl.getId()));
            List<Bus> buses = getBusList(xl);
            route.setBuses(buses);

        }
        routeInfo.setRoutes(routes);
        nettyUtil.sendData(ctx,routeInfo);
		return ApiResponse.success(routeInfo.toString());
	}

	private List<Bus> getBusList(ClXl xl){
        SimpleCondition condition = new SimpleCondition(ClClyxjl.class);
        condition.eq(ClClyxjl.InnerColumn.xlId,xl.getId());
	    List<ClClyxjl> clClyxjls = clyxjlService.findByCondition(condition);
	    if (clClyxjls.size() == 0)return new ArrayList<>();

	    List<Bus> buses = new ArrayList<>();
        for (ClClyxjl clClyxjl : clClyxjls) {
            Bus bus = new Bus();
            bus.setId(clClyxjl.getClId());
            bus.setCurrentStationNo(""+clClyxjl.getZdbh());
            bus.setPlate(clClyxjl.getCphm());
            bus.setState(clClyxjl.getZt());
            buses.add(bus);
        }
        return buses;
    }

    @Override
    public ClXl getByCarId(String carId) {
        // 获取车辆当天线路信息
        SimpleCondition condition = new SimpleCondition(ClPb.class);
        condition.eq(ClPb.InnerColumn.clId,carId);
        List<ClPb> clPbs = clPbMapper.selectByExample(condition);
        if (clPbs.size() == 0){
            return null;
        }
        ClPb clPb = clPbs.get(0);
        String xlId = clPb.getXlId();
        return xlService.findById(xlId);
    }

}
