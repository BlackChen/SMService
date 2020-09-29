package com.bsoft.xnsmservice.model;

import com.bsoft.xnsmservice.config.SMServiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName SMSFilterDTO
 * @Description 入参转换类
 * Created by blackchen on 2020/9/24 15:47
 */
@ApiModel(description = "短信入参")
public class SMSFilterDTO {
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号,一对一或多是必传")
	private String mobiles;
	/**
	 * 内容或者电话和内容
	 */
	@ApiModelProperty(value = "必传,多对多时传Json字符串,含手机号和内容")
	@NotBlank(message = "请检查发送内容.")
	private String content;
	@ApiModelProperty(value = "IP地址")
	private String ipv4;
	/**
	 * 服务类型代码
	 */
	@ApiModelProperty(value = "必传,发送类型")
	@Max(value = 1000, message = "请确认服务类型!")
	@NotNull(message = "类型不能为空!")
	private Integer sType;//传入的发送类型
	/**
	 * 通过sType获得的短信服务类型
	 */
	private SMServiceType serviceType;

	public String toStr(){
		return mobiles + content + ipv4 + sType;
	}

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

	public Integer getsType() {
		return sType;
	}

	public void setsType(Integer sType) {
		this.sType = sType;

		setServiceType(SMServiceType.getSMSType(sType));
	}
}
