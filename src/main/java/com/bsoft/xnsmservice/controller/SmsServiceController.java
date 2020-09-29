package com.bsoft.xnsmservice.controller;

import com.alibaba.fastjson.JSON;
import com.bsoft.xnsmservice.config.SMServiceType;
import com.bsoft.xnsmservice.config.SmsInfoConfig;
import com.bsoft.xnsmservice.model.ResultDTO;
import com.bsoft.xnsmservice.model.SMSFilterDTO;
import com.bsoft.xnsmservice.service.CMSMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 短信服务
 * Created by blackchen on 2020/8/26 09:36
 */
@Api(value = "短信服务")
@Controller
public class SmsServiceController {
	@Autowired
	private CMSMService cmsmService;
	/**
	 * 发送普通短信 一对一 一对多 多对多
	 * 步骤:参数校验-发送-接收发送结果-入库-返数据
	 *
	 * @param smsFilter 入参
	 * @return 返回发送结果
	 */
	@ApiOperation(value = "sendSms", notes = "发送普通短信", response = ResultDTO.class)
	@PostMapping("/sendSms")
	@ResponseBody
	public String sendSms(@Valid SMSFilterDTO smsFilter, BindingResult bindingResult){
		ResultDTO resultDTO = new ResultDTO();
		//在这里，我们判断参数是否通过校验
		System.out.println("接收到"+smsFilter.toStr());
		if (bindingResult.hasErrors()) {
//			FieldError error = (FieldError) bindingResult.getAllErrors().get(0);//返回一个
//			resultDTO.returnError("400", error.getDefaultMessage());

			Map<String,String> errorMsg = new HashMap<>();//返回所有
			for (FieldError item:bindingResult.getFieldErrors()) {
				errorMsg.put(item.getField(),item.getDefaultMessage());
			}
			resultDTO.returnError("-1", JSON.toJSONString(errorMsg));
			return resultDTO.returntoString();
		}

		try {
			SMServiceType type = smsFilter.getServiceType();
			SmsInfoConfig.ConfigSmsInfo(type);//配置平台信息

			switch (type){
				case CMCC_TZPH_Normal: {
					//发送短信
					String result = cmsmService.sendNormalMsg(smsFilter);

					if (result.equals("success")) {
						resultDTO.setMessage("发送成功");
						return resultDTO.returntoString();
					} else {
						resultDTO.returnError("-1", "发送失败:" + result);
					}
				}break;

				case CTCC_Normal:
					sendCTSms(smsFilter, resultDTO);
					break;

				default: {
					resultDTO.returnError("-1","服务未建立: "+ type.getCode());
				}break;

			}
		} catch (SQLException e) {
			resultDTO.returnError("-1","数据库错误!");
		} catch (NullPointerException e){
			resultDTO.returnError("-1","请检查数据!");

		} catch (Exception e){
			resultDTO.returnError("-1","服务器异常!");
		}

		return resultDTO.returntoString();
	}

	/**
	 * 发送电信信息
	 */
	private void sendCTSms(SMSFilterDTO smsFilter, ResultDTO resultDTO) {
		resultDTO.returnError("-1","服务未建立!");
	}


	// 测试 main函数
	public static void main(String[] args){

	}
}
