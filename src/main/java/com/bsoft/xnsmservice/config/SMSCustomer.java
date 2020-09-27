package com.bsoft.xnsmservice.config;

/**
 * @ClassName SMSCustomer
 * @Description TODO
 * Created by blackchen on 2020/9/24 17:02
 */
public enum SMSCustomer {
	Unknown(0, "未知"),//未知
	TZPH(1, "天柱县人民医院");//天柱县人民医院

	private int code;
	private String ecName;
	private SmsInfoConfig config;

	private SMSCustomer(int code, String ecName){
		this.code = code;
		this.ecName = ecName;
//		this.config = config;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
