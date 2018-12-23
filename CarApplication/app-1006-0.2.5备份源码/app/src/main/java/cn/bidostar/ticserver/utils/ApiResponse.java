package cn.bidostar.ticserver.utils;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * 接口返回对象封装bean结构
 * @author 李彬彬
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@HttpResponse(parser = JsonResponseParser.class)
public class ApiResponse<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1272926047811431195L;
	//成功
	public final static int SUCCESS = 200;
	//失败
	public final static int FAILED = 500;
	//参数错误
	public final static int PARAM_ERROR = 400;
	//授权认证失败
	public final static int AUTH_FAILED = 999;
	// 无权限访问
	public final static int FORBIDDEN = 403;
	// 未找到资源
	public final static int NOT_FOUND = 404;

	private int code = SUCCESS;
	//返回消息内容
	private String message = "请求成功！";
	//返回结果对象
	private T result;

	public ApiResponse() {
	}

	@Override
	public String toString() {
		return "ApiResponse{" +
				"code=" + getCode() +
				", message='" + getMessage() + '\'' +
				", result=" + getResult() +
				'}';
	}

	/**
	 * 返回消息code编码
	 * 200：表示接口请求成功
	 * 201：表示接口请求成功，但是有新的版本更新，可做更新操作，会返回请求数据
	 * 202：表示接口请求成功，但是有新的版本更新，必须做版本更新操作，不会返回请求数据
	 * 999：表示用户身份验证失败，需要重新登陆授权
	 * 500和其它值：表示接口请求失败
	 *
	 */
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
