package cn.bidostar.ticserver.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 公共接收参数对象
 * @author wanggang
 *
 */
@Table(name="requestdtomodel")
public class RequestCommonParamsDto implements Serializable {
	@Column(name="id",isId = true)
	public int id;
	@Column(name="deviceId")
	private String deviceId;//设备id（每次都必须上传）
	@Column(name="channelId")
	private String channelId;//用于推送（每次都必须上传）
	@Column(name="deviceTag")
	private String deviceTag;//设备tag主要也是 用于推送（每次都必须上传）
	@Column(name="startTime")
	private String startTime;//开始时间
	private String endTime;//结束时间
	@Column(name="longitude")
	private String longitude;//经度（参数几乎每个接口都会上传）
	@Column(name="latitude")
	private String latitude;//纬度（参数几乎每个接口都会上传）
	@Column(name="speed")
	private String speed;//速度
	@Column(name="direction")
	private String direction;//方向
	@Column(name="eventType")
	private String eventType;//事件  00点火，01熄火，02急加速 ，04 GPS，05 上传图片，06上传视频， 07上传图片（突发事件记录），08上传视频（突发事件记录）
	private String filePath;//文件相对路径(上传视频或者图片才会使用的参数)
	private String fileLocalPath;//文件本地绝对路径(上传视频或者图片才会使用的参数)
	private String fileRealName;//上传的文件在设备中的名称(上传视频或者图片才会使用的参数)
	private String fileSize;//文件大小(上传视频或者图片才会使用的参数)
	private String filePostfix;//文件后缀（可以用于文件类型）
	private String taskId;//任务id（用于上传服务器下载命令之后，终端上传之后回调给服务器之后的数据使用）
	@Column(name="sczt")
	private String sczt;//汽车是在运行中还是已经熄火的数据上传标识
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
	@Column(name="dwjd")
	private String dwjd;//GPS定位角度
	@Column(name="gpsjd")
	private String gpsjd;//GPS精度
	@Column(name="fxj")
	private String fxj;//方向角
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getChannelId() {
        return channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getDeviceTag() {
        return deviceTag;
    }
    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileLocalPath() {
		return fileLocalPath;
	}
	public void setFileLocalPath(String fileLocalPath) {
		this.fileLocalPath = fileLocalPath;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePostfix() {
		return filePostfix;
	}
	public void setFilePostfix(String filePostfix) {
		this.filePostfix = filePostfix;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCmdType() {
		return cmdType;
	}
	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getCmdParams() {
		return cmdParams;
	}
	public void setCmdParams(String cmdParams) {
		this.cmdParams = cmdParams;
	}
	public String getDwjd() {
		return dwjd;
	}
	public void setDwjd(String dwjd) {
		this.dwjd = dwjd;
	}
	public String getGpsjd() {
		return gpsjd;
	}
	public void setGpsjd(String gpsjd) {
		this.gpsjd = gpsjd;
	}
	public String getFxj() {
		return fxj;
	}
	public void setFxj(String fxj) {
		this.fxj = fxj;
	}

	public String getSczt() {
		return sczt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RequestCommonParamsDto{" +
				"id=" + id +
				", deviceId='" + deviceId + '\'' +
				", channelId='" + channelId + '\'' +
				", deviceTag='" + deviceTag + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", longitude='" + longitude + '\'' +
				", latitude='" + latitude + '\'' +
				", speed='" + speed + '\'' +
				", direction='" + direction + '\'' +
				", eventType='" + eventType + '\'' +
				", filePath='" + filePath + '\'' +
				", fileLocalPath='" + fileLocalPath + '\'' +
				", fileRealName='" + fileRealName + '\'' +
				", fileSize='" + fileSize + '\'' +
				", filePostfix='" + filePostfix + '\'' +
				", taskId='" + taskId + '\'' +
				", sczt='" + sczt + '\'' +
				", cmdType='" + cmdType + '\'' +
				", cmd='" + cmd + '\'' +
				", cmdParams='" + cmdParams + '\'' +
				", dwjd='" + dwjd + '\'' +
				", gpsjd='" + gpsjd + '\'' +
				", fxj='" + fxj + '\'' +
				'}';
	}

	public void setSczt(String sczt) {
		this.sczt = sczt;
	}
}
