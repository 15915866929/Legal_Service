package com.core.protocol;

import java.io.Serializable;

public class BaseResponse <T> implements Serializable {
	private String msg; // 返回说明
	private String errorCode; // 状态码
	private Boolean succ;
	private Object result; // 返回信息

	public BaseResponse() {
		super();
		this.errorCode = ReturnCode.RECODE_SUCCESS; // 默认成功
		this.msg = null;
		this.result = null;
		this.succ = true;
	}

	public BaseResponse(String retCode, String msg, Object result,Boolean succ) {
		super();
		this.errorCode = retCode;
		this.msg = msg;
		this.result = result;
		this.succ = succ;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Boolean getSucc() {
		return succ;
	}

	public void setSucc(Boolean succ) {
		this.succ = succ;
	}

	@Override
	public String toString() {
		return "BaseResponse [msg=" + msg + ", errorCode=" + errorCode + ", result=" + result + "]";
	}

}
