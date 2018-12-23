package cn.bidostar.ticserver.model;

/**
 * Created by admins on 2018/1/28.
 */

public class AuthModel {
    private String deviceId;
    private String username;
    private String password;
    private String f;
    private int t;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getF() {
        return f;
    }
    public void setF(String f) {
        this.f = f;
    }
    public int getT() {
        return t;
    }
    public void setT(int t) {
        this.t = t;
    }
}
