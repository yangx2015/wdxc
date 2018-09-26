package com.ldz.autoupdate;

/**
 * auther chenwei
 * create at 2018/9/22
 */
public class DeviceItem {
    private String deviceId;

    private String targetVersion;

    public DeviceItem(String[] row) {
        this.deviceId = row[0];
        this.targetVersion = row[1];
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }
}
