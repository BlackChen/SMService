package com.bsoft.xnsmservice.service;

import com.alibaba.fastjson.JSON;
import com.bsoft.xnsmservice.config.SMServiceType;
import com.bsoft.xnsmservice.dao.SMSDao;
import com.bsoft.xnsmservice.entity.SmsSendHistory;
import com.bsoft.xnsmservice.model.CMSMSFilter;
import com.bsoft.xnsmservice.model.CMSMSResult;
import com.bsoft.xnsmservice.model.SMSFilterDTO;
import com.bsoft.xnsmservice.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * @ClassName CMSMService
 * @Description 中国移动短信
 * Created by blackchen on 2020/9/24 09:50
 */
@Service
public class CMSMService {
	  @Autowired
	  private SMSDao smsDao;
	/**
	 * 发送普通短信 一对一 一对多 多对多
	 *
	 * @param smsFilter
	 * @return
	 */
	@Transactional
	public String sendNormalMsg(SMSFilterDTO smsFilter) throws Exception {
		CMSMSFilter filter = new CMSMSFilter(smsFilter);

//		System.out.println("sssssss" +smsFilter.getServiceType().getProvider().getServiceURL());
		String resStr = HttpUtil.sendPost(smsFilter.getServiceType().getProvider().getServiceURL(), filter.encode());

//		System.out.print("发送短信结果："+resStr);

		CMSMSResult sendRes = JSON.parseObject(resStr, CMSMSResult.class);

		SmsSendHistory his = new SmsSendHistory();

//		如果是多对多发送短信,分开存储
		if (smsFilter.getMobiles().isEmpty() ){
			Map<String, String> msgs = (Map) JSON.parseObject(smsFilter.getContent());

			for (String e : msgs.keySet()) {
				filter.setMobiles(e);
				filter.setContent( msgs.get(e));
				his.transCM(filter, sendRes.getRspcod(), smsFilter);

				smsDao.save(his);

			}
		} else {
			his.transCM(filter, sendRes.getRspcod(), smsFilter);

			smsDao.save(his);
		}
		return sendRes.getRspcod();
	}

	/**
	 * 发送模板短信
	 *
	 * @param mobiles
	 * @param params
	 * @param type 服务类型
	 * @return
	 */
	public static boolean sendModelMsg(String mobiles, String params, SMServiceType type){
		CMSMSFilter filter = new CMSMSFilter(new SMSFilterDTO());
		filter.setMobiles(mobiles);
		filter.setParams(params);

		String resStr = HttpUtil.sendPost(" ", filter.encode());

		System.out.print("发送短信结果："+resStr);

		CMSMSResult sendRes = JSON.parseObject(resStr, CMSMSResult.class);

		if(sendRes.getSuccess() && !"".equals(sendRes.getMgsGroup()) && "success".equals(sendRes.getRspcod())){
			return true;
		}
		return false;
	}



}
