package com.bsoft.xnsmservice.config;

/**
 * @ClassName SMSProvider
 * @Description TODO
 * Created by blackchen on 2020/9/24 17:01
 */
public enum SMSProvider{
	Unknown(0,"未知服务", SMSendType.Unknown, ""),

	CMCC(100, "中国移动普通短信", SMSendType.Normal, "http://112.35.1.155:1992/sms/norsubmit"),

	CMCC_Model(120, "中国移动模板短信", SMSendType.Model, "http://112.35.1.155:1992/sms/tmpsubmit"),

	CTCC(200, "中国电信", SMSendType.Unknown, "");

	private int code;
	private String name;
	private SMSendType sendtype;//类型
	private String serviceURL;


	private SMSProvider(int code, String name, SMSendType type, String url){
		this.code = code;
		this.name = name;
		this.sendtype = type;
		this.serviceURL = url;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public SMSendType getSendtype() {
		return sendtype;
	}

	public void setSendtype(SMSendType sendtype) {
		this.sendtype = sendtype;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
};
