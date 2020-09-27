package com.bsoft.xnsmservice.config;

/**
 * @ClassName SMServiceType
 * @Description 短信服务类型
 * Created by blackchen on 2020/9/24 10:58
 */
public enum SMServiceType {//服务商+使用组织+类型+序号/编码(多号段多模板编码/使用方式等)
	Unknown_SMS(SMSProvider.Unknown, SMSCustomer.Unknown,  0),
	//CMCC 中国移动 100+
	CMCC_TZPH_Normal(SMSProvider.CMCC, SMSCustomer.TZPH,  0),//中国移动_天柱县人民医院_普通短信 100

	CMCC_TZPH_Model(SMSProvider.CMCC_Model, SMSCustomer.TZPH, 0),//中国移动_天柱县人民医院_模板短信 120
	//CTCC 中国电信 200+
	CTCC_Normal(SMSProvider.CTCC, SMSCustomer.Unknown, 0);


	//成员变量
	private int code;//唯一编码
	private SMSProvider provider;//服务商
	private SMSCustomer customer;//服务对象

	private int index;//业务序号
	//构造方法
	private SMServiceType(SMSProvider provider, SMSCustomer customer, int index){
		this.code = provider.getCode();
		this.provider = provider;
		this.customer = customer;
		this.index = index;
	}

	public static SMServiceType getSMSType(int tt) throws NullPointerException{
		for (SMServiceType e : SMServiceType.values()) {
			if (e.getCode() == tt)
		    	return e;
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public SMSProvider getProvider() {
		return provider;
	}

	public void setProvider(SMSProvider provider) {
		this.provider = provider;
	}

	public SMSCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(SMSCustomer customer) {
		this.customer = customer;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
