package com.bsoft.xnsmservice.config;

/**
 * @ClassName SMSendType
 * @Description TODO
 * Created by blackchen on 2020/9/24 17:06
 */
public enum SMSendType {
	Unknown(0, "未知"),

	Normal(100, "普通短信"),

	Model(200, "模板短信");

	private int code;
	private String type;

	private SMSendType(int code, String type){
		this.code = code;
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
