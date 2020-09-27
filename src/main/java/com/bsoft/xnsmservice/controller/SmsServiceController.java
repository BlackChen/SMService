package com.bsoft.xnsmservice.controller;

import com.bsoft.xnsmservice.config.SMServiceType;
import com.bsoft.xnsmservice.model.CMSMSFilter;
import com.bsoft.xnsmservice.model.CMSMSResult;
import com.bsoft.xnsmservice.model.ResultDTO;
import com.bsoft.xnsmservice.model.SMSFilterDTO;
import com.bsoft.xnsmservice.service.CMSMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * Description: 短信服务
 * Created by blackchen on 2020/8/26 09:36
 */
@Api(value = "短信服务")
@Controller
public class SmsServiceController {

	/**
	 * 发送普通短信 一对一 一对多 多对多
	 * @param smsFilter 入参
	 * @return 返回发送结果
	 */
	@ApiOperation(value = "sendSms", notes = "发送普通短信", response = CMSMSResult.class)
	@PostMapping("/sendSms")
	@ResponseBody
	public String sendSms(SMSFilterDTO smsFilter){
		ResultDTO resultDTO = new ResultDTO();
//1.确认发送类型
		if (smsFilter.getsType()>200 || smsFilter.getsType()<100)
			return resultDTO.returnErrorSring("404", "类型错误");

		try {
			SMServiceType type = smsFilter.getServiceType();

			switch (type){
				case CMCC_TZPH_Normal:
					sendCMSms(smsFilter, resultDTO);
					break;

				case CTCC_Normal:
					sendCTSms(smsFilter, resultDTO);
					break;

				default: {
					resultDTO.setCode("400");
					resultDTO.setMessage(smsFilter.getsType() + "服务未建立: "+ type.getCode());
				}break;

			}
		} catch (NullPointerException e){
			resultDTO.returnErrorSring("502", "空指针:"+e.getMessage());
		} catch (SQLException e) {
			resultDTO.returnErrorSring("503", "数据库错误:"+e.getMessage());
		} catch (Exception e){
			resultDTO.returnErrorSring("504", e.getMessage());
		}

		return resultDTO.returntoString();

	}

	/**
	 * 发送移动普通短信 一对一or多,多对多
	 */
	private void sendCMSms(SMSFilterDTO smsFilter, ResultDTO resultDTO) throws SQLException,Exception{
		CMSMSFilter CMSMSFilter = new CMSMSFilter(smsFilter);
		//发送短信
			String result = CMSMService.sendNormalMsg(smsFilter);

			if (result.equals("success")){
				resultDTO.setMessage("发送成功");
				resultDTO.returntoString();
			} else {
				resultDTO.returnErrorSring("501", "返回码:"+result);
			}
	}

	/**
	 * 发送电信信息
	 */
	private void sendCTSms(SMSFilterDTO smsFilter, ResultDTO resultDTO) {
		resultDTO.setCode("400");
		resultDTO.setMessage("服务未建立!");
	}

	/**
	 * 发送移动模板短信
	 * @param mobiles
	 * @return 返回发送结果
	 */
	@ApiOperation(value = "sendCMVeritySms", notes = "发送移动模板短信")
	@PostMapping("/sendCMVeritySms")
	@ResponseBody
	public String sendVeritySms(String mobiles){
		ResultDTO resultDTO = new ResultDTO();
		CMSMSResult sendRes = new CMSMSResult();

		SMServiceType type = SMServiceType.getSMSType(200);

		resultDTO.setData(type);

		return resultDTO.returntoString();
	}

	// 测试 main函数
	public static void main(String[] args){
		System.out.println("短信服务");
	}
}
