package com.ldz.autoupdate;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * auther chenwei
 * create at 2018/9/23
 */

@Component
public class Config {
    private List<String> decviceIds;
    private List<DeviceItem> deviceItemList;
    private Map<String,String> configMap;

    public List<String> getDecviceIds() {
        return decviceIds;
    }

    public void setDecviceIds(List<String> decviceIds) {
        this.decviceIds = decviceIds;
    }

    public List<DeviceItem> getDeviceItemList() {
        return deviceItemList;
    }

    public void setDeviceItemList(List<DeviceItem> deviceItemList) {
        this.deviceItemList = deviceItemList;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }
}
