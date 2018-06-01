package com.ldz.biz.bean;

import com.ldz.biz.module.bean.GpsInfo;

public class SendGpsEvent {

    private GpsInfo gpsInfo;

    public SendGpsEvent(GpsInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public GpsInfo getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(GpsInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }
}
