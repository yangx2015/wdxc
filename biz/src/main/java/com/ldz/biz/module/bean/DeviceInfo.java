package com.ldz.biz.module.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceInfo {
    private String deviceId;
    private double lng;
    private double lat;
    private String status;
    private String createTime;
    private String updateTime;

    public DeviceInfo(GpsInfo gpsInfo){
        this.deviceId = gpsInfo.getDeviceId();
        this.lat = new Double(gpsInfo.getLatitude());
        this.lng = new Double(gpsInfo.getLongitude());
    }
}
