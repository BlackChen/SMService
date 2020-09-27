package com.bsoft.xnsmservice.model;

/**
 * Description: nethospital-parent
 * Created by blackchen on 2020/8/26 09:32
 */
public class CMSMSResult extends SMSFilterDTO {
	enum Rescod {
		IllegalMac,//	mac校验不通过。
		IllegalSignId,//	无效的签名编码。
		InvalidMessage,//	非法消息，请求数据解析失败。
		InvalidUsrOrPwd,//	非法用户名/密码。
		NoSignId,//	未匹配到对应的签名信息。
		success,//	数据验证通过。
		TooManyMobiles,//	手机号数量超限（>5000），应≤5000。
	}

	private String rspcod;//	String	响应状态，详见下表。

	private String mgsGroup;//	String	消息批次号，由云MAS平台生成，用于关联短信发送请求与状态报告，注：若数据验证不通过，该参数值为空。
	private Boolean success;//	boolean	数据校验结果。


	public CMSMSResult appDTOValue(SMSFilterDTO dto){
		CMSMSResult result = (CMSMSResult)dto;
		result.setMgsGroup(this.mgsGroup);
		result.setSuccess(this.success);
		result.setRspcod(this.rspcod);
		return result;
	}

	public String getRspcod() {
		return rspcod;
	}

	public void setRspcod(String rspcod) {
		this.rspcod = rspcod;
	}

	public String getMgsGroup() {
		return mgsGroup;
	}

	public void setMgsGroup(String mgsGroup) {
		this.mgsGroup = mgsGroup;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
