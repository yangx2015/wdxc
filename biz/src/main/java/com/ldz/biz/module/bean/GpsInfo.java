package com.ldz.biz.module.bean;

import lombok.Getter;
import lombok.Setter;

/*
 *  用于处理tic-server向业务系统发送gps,云视屏信息模型
 */
@Getter
@Setter
public class GpsInfo {
	private String deviceId;//设备id（每次都必须上传）
	private String channelId;//用于推送（每次都必须上传）
	private String deviceTag;//设备tag主要也是 用于推送（每次都必须上传）
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String longitude;//经度（参数几乎每个接口都会上传）
	private String latitude;//纬度（参数几乎每个接口都会上传）
	private Integer speed;//速度
	private String eventType;//事件 10急加速，20急刹车，30急转弯 ，40超速，50点火，60熄火,70不在电子围栏范围,04 GPS，05 上传图片，06上传视频， 07上传图片（突发事件记录），08上传视频（突发事件记录）
	private String filePath;//文件相对路径(上传视频或者图片才会使用的参数)
	private String fileLocalPath;//文件本地绝对路径(上传视频或者图片才会使用的参数)
	private String fileRealName;//上传的文件在设备中的名称(上传视频或者图片才会使用的参数)
	private String fileSize;//文件大小(上传视频或者图片才会使用的参数)
	private String filePostfix;//文件后缀（可以用于文件类型）
	private String taskId;//任务id（用于上传服务器下载命令之后，终端上传之后回调给服务器之后的数据使用）
	private String cmdType;//命令类型（推送消息时使用）   01：超速设定 02:灵敏度设定   11:拍视频 12:拍图片
	/**
	  cmdType 02:  
	    设置加减速监测灵敏等级，level 1-6，默认为 2 :
         1. 速度从零加到百公里约58秒内，能够被检测到急加速;
         2. 速度从零加到百公里约29秒内，能够被检测到急加速;
         3. 速度从零加到百公里约19秒内，能够被检测到急加速;
         4. 速度从零加到百公里约14秒内，能够被检测到急加速;
         5. 速度从零加到百公里约11秒内，能够被检测到急加速;
         6. 速度从零加到百公里约9秒内，能够被检测到急加速
       01:   值不能大于120且不能小于10
	 */
	private String cmd;//具体命令（推送消息时使用）   
	private String cmdParams;//其它参数（推送消息时使用）
	private String fxj;//方向角
	private String gpsjd;//GPS精度
}
