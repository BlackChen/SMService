package com.bsoft.xnsmservice.entity;

import com.bsoft.xnsmservice.config.SmsInfoConfig;
import com.bsoft.xnsmservice.model.CMSMSFilter;
import com.bsoft.xnsmservice.model.SMSFilterDTO;
import com.bsoft.xnsmservice.util.NetworkUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.net.SocketException;

/**
 * 短信发送记录(SmsSendHistory)实体类
 *
 * @author blackchen
 * @since 2020-09-25 11:01:56
 */
@Entity
@Table(name = "sms_send_history")
public class SmsSendHistory implements Serializable {
	private static final long serialVersionUID = 786751066642524623L;
	/**
	 * 企业名称
	 */
	private String ecname;
	/**
	 * 接口账号用户名
	 */
	private String apid;
	/**
	 * 收信手机号码
	 */
	private String mobiles;
	/**
	 * 短信内容
	 */
	private String content;
	/**
	 * 模板变量
	 */
	private String params;
	/**
	 * 模板id
	 */
	private String templateid;
	/**
	 * 签名编码
	 */
	private String sign;
	/**
	 * 扩展码
	 */
	private String addserial;
	/**
	 * 返回码:
	 * IllegalMac,//	mac校验不通过。
	 * IllegalSignId,//	无效的签名编码。
	 * InvalidMessage,//	非法消息，请求数据解析失败。
	 * InvalidUsrOrPwd,//	非法用户名/密码。
	 * NoSignId,//	未匹配到对应的签名信息。
	 * success,//	数据验证通过。
	 * TooManyMobiles,//	手机号数量超限（>5000），应≤5000。
	 */
	private String rspcod;
	/**
	 * ipv4地址
	 */
	private String ipv4;
	/**
	 * MAC地址
	 */
	private String macid;
	/**
	 * 发送时间
	 */
	private String sendtime;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sId ;
	/**
	 * 服务代码
	 */
	private String serid = SmsInfoConfig.getServiceID();

	public SmsSendHistory() throws SocketException {
	}

	public void transCM (CMSMSFilter rec, String rspcod, SMSFilterDTO dto){
		this.sign = rec.getSign();
		this.templateid = getTemplateid();
		this.params = rec.getParams();
		this.addserial = rec.getAddSerial();
		this.content = rec.getContent();
		this.mobiles = rec.getMobiles();
		this.ecname = rec.getEcName();
		this.apid = rec.getApId();

		this.rspcod = rspcod;

		String ip = dto.getIpv4();
		this.ipv4 = ip;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public String getEcname() {
		return ecname;
	}

	public void setEcname(String ecname) {
		this.ecname = ecname;
	}

	public String getApid() {
		return apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAddserial() {
		return addserial;
	}

	public void setAddserial(String addserial) {
		this.addserial = addserial;
	}

	public String getRspcod() {
		return rspcod;
	}

	public void setRspcod(String rspcod) {
		this.rspcod = rspcod;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getMacid() throws SocketException{
		return macid.isEmpty() || macid.equals("") ? NetworkUtil.getLocalMac() : macid;
	}

	public void setMacid(String macid){
		this.macid = macid;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public Integer getSId() {
		return sId;
	}

	public void setSId(Integer sId) {
		this.sId = sId;
	}

	public String getSerid() {
		return serid;
	}

	public void setSerid(String serid) {
		this.serid = serid;
	}

}