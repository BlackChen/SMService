package com.bsoft.xnsmservice.model;

import com.alibaba.fastjson.JSON;
import com.bsoft.xnsmservice.config.SMSendType;
import com.bsoft.xnsmservice.config.SmsInfoConfig;
import com.bsoft.xnsmservice.util.MD5Util;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Description: nethospital-parent
 * Created by blackchen on 2020/8/26 09:30
 */
public class CMSMSFilter {

	private String ecName;//	String	企业名称。
	private String apId;//	String	接口账号用户名。
	private String secretKey;// String  接口账号用户密码.
	private String mobiles;//	String	收信手机号码。英文逗号分隔，每批次限5000个号码，例：“13800138000,13800138001,13800138002”。
	private String content;//	String	短信内容。如content中存在双引号，请务必使用转义符\在报文中进行转义（使用JSON转换工具转换会自动增加转义符），否则会导致服务端解析报文异常。
	private String params;//	String	模板变量。格式：[“param1”,“param2”]，无变量模板填[""]。
	private String templateId;//	String	模板ID。在云MAS平台创建模板，路径：『短信』→『模板短信』→『模板管理』，创建后提交审核，审核通过将获得模板ID。
	private String sign;//	String	签名编码。在云MAS平台『管理』→『接口管理』→『短信接入用户管理』获取。
	private String addSerial;//	String	扩展码。依据申请开户的服务代码匹配类型而定，如为精确匹配，此项填写空字符串（""）；如为模糊匹配，此项可填写空字符串或自定义的扩展码，注：服务代码加扩展码总长度不能超过20位。
	private String mac;//	String;	参数校验序列，生成方法：将ecName、apId、secretKey、mobiles、content、sign、addSerial按序拼接（无间隔符），通过MD5（32位小写）计算得出值。

	public CMSMSFilter(SMSFilterDTO smsFilter){//默认配置
		SmsInfoConfig.ConfigSmsInfo(smsFilter.getServiceType());

		this.content = smsFilter.getContent();
		this.mobiles = smsFilter.getMobiles();

		this.ecName = SmsInfoConfig.getEcName();
		this.apId = SmsInfoConfig.getApId();
		this.secretKey = SmsInfoConfig.getSecretKey();
		this.sign = SmsInfoConfig.getSign();
		this.addSerial = SmsInfoConfig.getAddSerial();

		appMacStr(smsFilter.getServiceType().getProvider().getSendtype());
	}
	public String encode() {
		String reqText = JSON.toJSONString(this);
		System.out.println("json:"+reqText);

		String encode = Base64.encodeBase64String(reqText.getBytes());
		System.out.println("base64:"+encode);

		return encode;
	}

	private void appMacStr (SMSendType sendType){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(this.getEcName());
		stringBuffer.append(this.getApId());
		stringBuffer.append(this.getSecretKey());

		if (sendType == SMSendType.Model){
			stringBuffer.append(this.templateId);
		}
		stringBuffer.append(this.getMobiles());

		if (sendType == SMSendType.Model){
			stringBuffer.append(this.params);
		}
		if (sendType == SMSendType.Normal) {
			stringBuffer.append(this.getContent());
		}

		stringBuffer.append(this.getSign());
		stringBuffer.append(this.getAddSerial());
		System.out.println("拼接字符串:"+ stringBuffer);

		this.setMac(MD5Util.MD5(stringBuffer.toString()));
		System.out.println("mac:"+this.getMac());
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getEcName() {
		return ecName;
	}

	public void setEcName(String ecName) {
		this.ecName = ecName;
	}

	public String getApId() {
		return apId;
	}

	public void setApId(String apId) {
		this.apId = apId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAddSerial() {
		return addSerial;
	}

	public void setAddSerial(String addSerial) {
		this.addSerial = addSerial;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
}
