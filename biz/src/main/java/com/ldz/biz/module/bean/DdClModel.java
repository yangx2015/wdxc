package com.ldz.biz.module.bean;

import java.util.List;

/**
 * 校巴监控-校巴定位信息
 * Created by Administrator on 2018/3/29.
 */
public class DdClModel {

    private String zdId;//站点ID
    private String zdName;//站点名称
    private long vehicleCount;//该站点下车辆的数量
    private long vehicleScope;//站点范围
    private List<ClClyxjlModel> vehicleList;//当前站点的车辆节点


    public String getZdId() {
        return zdId;
    }

    public void setZdId(String zdId) {
        this.zdId = zdId;
    }

    public String getZdName() {
        return zdName;
    }

    public void setZdName(String zdName) {
        this.zdName = zdName;
    }

    public long getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public List<ClClyxjlModel> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<ClClyxjlModel> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public long getVehicleScope() {
        return vehicleScope;
    }

    public void setVehicleScope(long vehicleScope) {
        this.vehicleScope = vehicleScope;
    }
}
