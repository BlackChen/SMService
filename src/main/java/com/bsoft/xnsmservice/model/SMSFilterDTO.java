package com.bsoft.xnsmservice.model;

import com.bsoft.xnsmservice.config.SMServiceType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @ClassName SMSFilterDTO
 * @Description 入参转换类
 * Created by blackchen on 2020/9/24 15:47
 */
public class SMSFilterDTO {
	/**
	 * 手机号
	 */
	private String mobiles;
	/**
	 * 内容或者电话和内容
	 */
	@NotEmpty(message = "发送内容不能为空")
	private String content;
	private String ipv4;
	/**
	 * 服务类型代码
	 */
	@Size(max = 1000, min = 0, message = "类型不存在")
	private int sType;//传入的发送类型
	/**
	 * 通过sType获得的短信服务类型
	 */
	private SMServiceType serviceType;

	public SMServiceType getServiceType() throws NullPointerException{
		return serviceType == null ? SMServiceType.getSMSType(sType) : serviceType;
	}

	public void setServiceType(SMServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public int getsType() {
		return sType;
	}

	public void setsType(int sType) {
		this.sType = sType;

		setServiceType(SMServiceType.getSMSType(sType));
	}
}
