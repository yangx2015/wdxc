package com.ldz.util.bean;

/**
 * 鹰眼轨迹模型
 */
public class TrackPoint {

    /**
     * 用户的ak，授权使用 (必填)
     */
    private String ak;  //2pVOrCuBldNDOgDtwaYSP8gpQ2VQdZY9

    /**
     * servicede ID，作为其唯一标识 (必填)
     */
    private String service_id;  //200383

    /**
     * entity唯一标识 (必填)
     */
    private String entity_name; //CWB201805181744

    /**
     * 纬度 (必填)
     */
    private String latitude;    //30.5427744502

    /**
     * 经度 (必填)
     */
    private String longitude;   //114.2618477088

    /**
     * 定位时设备的时间 (必填)  Unix 时间戳
     * 输入的loc_time不能大于当前服务端时间10分钟以上，即不支持存未来的轨迹点。
     */
    private String loc_time;    //1526637383

    /**
     * 坐标类型 (坐标类型)  (必填)
     * 默认值：bd09ll
     * 该字段用于描述上传的坐标类型。可选值为：
     * wgs84：GPS 坐标
     * gcj02：国测局加密坐标
     * bd09ll：百度经纬度坐标
     */
    private String coord_type_input;    //wgs84

    /**
     * 速度
     * 单位：km/h
     */
    private String speed;   //30.25

    /**
     * 方向
     * 范围为[0,359]，0度为正北方向，顺时针
     */
    private String direction;   //12
    /**
     * 高度
     * 米
     */
    private String height;  //113.76

    /**
     * 定位精度，GPS或定位SDK返回的值
     */
    private String radius;  //3
    /**
     *
     */
    private String _object_key; //liu_201805181744
    private String city;    //hubei
    private String province;    //wuhan

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(String loc_time) {
        this.loc_time = loc_time;
    }

    public String getCoord_type_input() {
        return coord_type_input;
    }

    public void setCoord_type_input(String coord_type_input) {
        this.coord_type_input = coord_type_input;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String get_object_key() {
        return _object_key;
    }

    public void set_object_key(String _object_key) {
        this._object_key = _object_key;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
