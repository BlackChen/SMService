package com.bsoft.xnsmservice.controller;

import com.alibaba.fastjson.JSON;
import com.bsoft.xnsmservice.config.DBInfoConfig;
import com.bsoft.xnsmservice.config.SMServiceType;
import com.bsoft.xnsmservice.config.SmsInfoConfig;
import com.bsoft.xnsmservice.model.CMSMSFilter;
import com.bsoft.xnsmservice.model.CMSMSResult;
import com.bsoft.xnsmservice.model.ResultDTO;
import com.bsoft.xnsmservice.model.SMSFilterDTO;
import com.bsoft.xnsmservice.service.CMSMService;
import com.bsoft.xnsmservice.util.DBConnectionPool;
import com.bsoft.xnsmservice.util.DBConnectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 短信服务
 * Created by blackchen on 2020/8/26 09:36
 */
@Api(value = "短信服务")
@Controller
public class SmsServiceController {
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
		if (bindingResult.hasErrors()) {
//			FieldError error = (FieldError) bindingResult.getAllErrors().get(0);//返回一个
//			resultDTO.returnError("400", error.getDefaultMessage());

			Map<String,String> errorMsg = new HashMap<>();//返回所有
			for (FieldError item:bindingResult.getFieldErrors()) {
				errorMsg.put(item.getField(),item.getDefaultMessage());
			}
			resultDTO.returnError("400", JSON.toJSONString(errorMsg));
			return resultDTO.returntoString();
		}

		try {
			SMServiceType type = smsFilter.getServiceType();
			SmsInfoConfig.ConfigSmsInfo(type);//配置平台信息
			DBInfoConfig.ConfigDBInfo(type);//配置数据库信息

			switch (type){
				case CMCC_TZPH_Normal:
					sendCMSms(smsFilter, resultDTO);
					break;

				case CTCC_Normal:
					sendCTSms(smsFilter, resultDTO);
					break;

				default: {
					resultDTO.returnError("401","服务未建立: "+ type.getCode());
				}break;

			}
		} catch (NullPointerException e){
			resultDTO.returnError("402","请检查数据!");

		} catch (Exception e){
			resultDTO.returnError("500","服务器异常!");
		}

		return resultDTO.returntoString();
	}

	/**
	 * 发送移动普通短信 一对一or多,多对多
	 */
	private void sendCMSms(SMSFilterDTO smsFilter, ResultDTO resultDTO) throws Exception{
		CMSMSFilter CMSMSFilter = new CMSMSFilter(smsFilter);
		//发送短信
		String result = CMSMService.sendNormalMsg(smsFilter);

		if (result.equals("success")){
			resultDTO.setMessage("发送成功");
		} else {
			resultDTO.returnError("501","信息校验不通过:"+result);
		}
	}

	/**
	 * 发送电信信息
	 */
	private void sendCTSms(SMSFilterDTO smsFilter, ResultDTO resultDTO) {
		resultDTO.returnError("401","服务未建立!");
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

	/**
	 * 测试连接池
	 * @param type
	 * @return 返回发送结果
	 */
	@ApiOperation(value = "testPool", notes = "发送移动模板短信")
	@GetMapping("/testPool")
	@ResponseBody
	public String testPool(int type){
		ResultDTO resultDTO = new ResultDTO();

		DBInfoConfig.ConfigDBInfo(SMServiceType.getSMSType(type));//初始化数据库信息

		try {
			/*使用连接池创建100个连接的时间*/
            /*// 创建数据库连接库对象
            ConnectionPool connPool = new ConnectionPool("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test", "root", "123456");
            // 新建数据库连接库
            connPool.createPool();*/
			DBConnectionPool connPool = DBConnectionUtil.GetPoolInstance();//单例模式创建连接池对象
			// SQL测试语句
			String sql = "Select * from "+DBInfoConfig.getTablename();
			// 设定程序运行起始时间
			long start = System.currentTimeMillis();
			// 循环测试100次数据库连接
			for (int i = 0; i < 100; i++) {
				Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					String name = rs.getString("ecName");
					System.out.println("查询结果" + name);
				}
				rs.close();
				stmt.close();
				connPool.returnConnection(conn);// 连接使用完后释放连接到连接池
			}
			System.out.println("经过100次的循环调用，使用连接池花费的时间:"+ (System.currentTimeMillis() - start) + "ms");
			// connPool.refreshConnections();//刷新数据库连接池中所有连接，即不管连接是否正在运行，都把所有连接都释放并放回到连接池。注意：这个耗时比较大。
			connPool.closeConnectionPool();// 关闭数据库连接池。注意：这个耗时比较大。
			// 设定程序运行起始时间
//			start = System.currentTimeMillis();
//
//			/*不使用连接池创建100个连接的时间*/
//			// 导入驱动
//			Class.forName(DBInfoConfig.getDriver());
//			for (int i = 0; i < 100; i++) {
//				// 创建连接
//				Connection conn = DriverManager.getConnection(DBInfoConfig.getUrl(), DBInfoConfig.getUsername(), DBInfoConfig.getPassword());
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql);
//				while (rs.next()) {
//					String name = rs.getString("ecName");
//					System.out.println("查询结果--" + name);
//				}
//				rs.close();
//				stmt.close();
//				conn.close();// 关闭连接
//			}
//			System.out.println("经过100次的循环调用，不使用连接池花费的时间:" + (System.currentTimeMillis() - start) + "ms");
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultDTO.returntoString();
	}

	// 测试 main函数
	public static void main(String[] args){
		System.out.println("短信服务");
	}
}
