package cn.bidostar.ticserver.utils;

/**
 * Created by admins on 2018/1/28.
 */

public class AppConsts {

    public static final String PUSH_KEY = "ScePwSdAgonrPsPSTlef7hRR";

    /**
     * 设置加减速监测灵敏等级缓存，level 1-6，默认为 2 :
     */
    public static final String Violent_Set_Parma_Key = "Violent_Set_Parma_Value";

    /**
     * GPS定时心跳的key
     */
    public static final String UPLOAD_GPS_TIMER = "CAR_GPS_TIMER_KEY";

    /**
     * 上传视频的模式存储key
     */
    public static final String CAR_UPLOAD_MP4_MODEL = "CAR_UPLOAD_MP4_MODEL_KEY";

    /**
     * 车辆是否运行状态存储的key，防止意外退出app之后重新进入值还原
     */
    public static final String CAR_ON_RUN_FLAG = "CAR_ON_RUN_FLAG";
    /**
     * 是否在上传中的队列标识
     */
    public static final String CAR_UPLOAD_QUEE = "CAR_UPLOAD_QUEE";


    /**
     * 没有网络和没有GPS信号时的gps临时存储标识  00 正常  10 有数据存储，需要去遍历一下
     */
    public static final String CAR_GPS_NO_NETWORK_DATA = "CAR_GPS_NO_NETWORK_DATA";

    /**
     * 上传得服务器url
     */
    public static final String CAR_BASE_SERVER_URL = "CAR_BASE_SERVER_URL_KEY";

    /**
     * 存储纬度
     */
    public static final String CAR_LOCAL_LAT = "CAR_LOCAL_LAT_KEY";//存储纬度

    /**
     * 存储经度
     */
    public static final String CAR_LOCAL_LNG = "CAR_LOCAL_LONG_KEY";//存储经度

    /**
     * 定位精度
     */
    public static final String CAR_LOCAL_PRE = "CAR_LOCAL_PRE_KEY";

    /**
     * 方向角
     */
    public static final String CAR_LOCAL_DIRECTION  = "CAR_LOCAL_DIRECTION_KEY";

    /**
     * 速度
     */
    public static final String CAR_LOCAL_SPEED = "CAR_LOCAL_SPEED_KEY";


    /**
     * 车辆超速Key
     */
    public static final String CAR_SPEED_KEY = "CAR_SPEED_KEY";

    /**
     * 主机地址
     */
    public static final String HOST = "192.168.1.185";
    /**
     * 监听连接端口
     */
    public static final int TCP_PORT = 9000;

    /**
     * tic-server 的base_url地址
     */
    public static final String BASE_URL = "http://47.98.39.45:8080/tic-server/api";

    /**
     * 上传文件接口
     */
    public static  final String API_UPLOAD =  "/file/upload";

    /**
     * 批量上传附件接口
     */
    public static final String API_UPLOADS = "/file/batch/upload";

    /**
     * 上传GPS信息
     */
    public static final String API_GPSINFO = "/device/gps";

    /**
     * 批量上传本地的gps信息
     */
    public static final String API_GPSINFOALL = "/device/dbgps";

    //10急加速，20急刹车，30急转弯 ，40超速，50点火，60熄火,70 运行时GPS上传  71 熄火之后的GPS上传
    public static final String CAR_JSPEED = "10";
    public static final String CAR_JDOWN = "20";
    public static final String CAR_LORR = "30";
    public static final String CAR_CSPEED = "40";
    public static final String CAR_ON = "50";
    public static final String CAR_OFF = "60";
    public static final String CAR_RUN_GPS = "70";
    public static final String CAR_OFF_GPS = "71";



}
