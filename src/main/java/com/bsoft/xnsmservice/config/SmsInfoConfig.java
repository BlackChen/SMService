package com.bsoft.xnsmservice.config;

/**
 * @ClassName SmsInfoConfig
 * @Description 短信平台信息配置
 * Created by blackchen on 2020/9/24 10:27
 */

public class SmsInfoConfig {
	private static String ecName;//	String	企业名称。
	private static String apId;//	String	接口账号用户名。
	private static String secretKey;// String  接口账号用户密码.
	private static String params;//	String	模板变量。格式：[“param1”,“param2”]，无变量模板填[""]。
	private static String templateId;//	String	模板ID。在云MAS平台创建模板，路径：『短信』→『模板短信』→『模板管理』，创建后提交审核，审核通过将获得模板ID。
	private static String sign;//	String	签名编码。在云MAS平台『管理』→『接口管理』→『短信接入用户管理』获取。
	private static String addSerial;//	String	扩展码。依据申请开户的服务代码匹配类型而定，如为精确匹配，此项填写空字符串（""）；如为模糊匹配，此项可填写空字符串或自定义的扩展码，注：服务代码加扩展码总长度不能超过20位。
	private static String mac;//	String;	参数校验序列，生成方法：将ecName、apId、secretKey、mobiles、content、sign、addSerial按序拼接（无间隔符），通过MD5（32位小写）计算得出值。
	private static String serviceID;//	String	服务代码

	public static void ConfigSmsInfo(SMServiceType type){

		switch (type){
			case CMCC_TZPH_Normal:{
				ecName = "天柱县人民医院";
				apId = "tzph";
				secretKey = "tzph123456";
				sign = "kGrOgmchu";
				addSerial = "";
				serviceID = "1069090727550027";

			}break;
			case CMCC_TZPH_Model:{
				ecName = "天柱县人民医院";
				apId = "tzph";
				secretKey = "tzph123456";
				sign = "kGrOgmchu";
				addSerial = "";
				serviceID = "";

			}break;

			default:
				throw new IllegalStateException("Unexpected value: " + type);
		}
	}

	public static String getEcName() {
		return ecName;
	}

	public static void setEcName(String ecName) {
		SmsInfoConfig.ecName = ecName;
	}

	public static String getApId() {
		return apId;
	}

	public static void setApId(String apId) {
		SmsInfoConfig.apId = apId;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public static void setSecretKey(String secretKey) {
		SmsInfoConfig.secretKey = secretKey;
	}

	public static String getParams() {
		return params;
	}

	public static void setParams(String params) {
		SmsInfoConfig.params = params;
	}

	public static String getTemplateId() {
		return templateId;
	}

	public static void setTemplateId(String templateId) {
		SmsInfoConfig.templateId = templateId;
	}

	public static String getSign() {
		return sign;
	}

	public static void setSign(String sign) {
		SmsInfoConfig.sign = sign;
	}

	public static String getAddSerial() {
		return addSerial;
	}

	public static void setAddSerial(String addSerial) {
		SmsInfoConfig.addSerial = addSerial;
	}

	public static String getMac() {
		return mac;
	}

	public static void setMac(String mac) {
		SmsInfoConfig.mac = mac;
	}

	public static String getServiceID() {
		return serviceID;
	}

	public static void setServiceID(String serviceID) {
		SmsInfoConfig.serviceID = serviceID;
	}
}
