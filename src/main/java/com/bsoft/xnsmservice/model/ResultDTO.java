package com.bsoft.xnsmservice.model;

import com.alibaba.fastjson.JSON;

/**
 * 
 * author:caoyuxiang 2020年8月6日 下午1:55:34
 */
public class ResultDTO {
	private String code;
	private String message;
	private Object data;

	/**
	 * 返回失败数组样式字符串
	 */
	public String returnErrorSring (String code, String errMsg){
		this.setCode(code);
		this.setMessage(errMsg);

		return returntoString();
	}
	/**
	 * 返回成功字符串
	 */
	public String returntoString (){
		return JSON.toJSONString(this);
	}

	/*
	 * 设置默认值 
	 */
	public ResultDTO(){
		code = "200";
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	/**
	 * 100 => 'Continue', //继续
	 * 101 => 'Switching Protocols', //分组交换协议
	 * 102 => 'Processing', // RFC2518
	 * 200 => 'OK',
	 * 201 => 'Created', //被创建
	 * 202 => 'Accepted', //被采纳
	 * 203 => 'Non-Authoritative Information', //非授权信息
	 * 204 => 'No Content', //无内容
	 * 205 => 'Reset Content', //重置内容
	 * 206 => 'Partial Content', //部分内容
	 * 207 => 'Multi-Status', // RFC4918
	 * 208 => 'Already Reported', // RFC5842
	 * 226 => 'IM Used', // RFC3229
	 * 300 => 'Multiple Choices', //多选项
	 * 301 => 'Moved Permanently', //永久地传送
	 * 302 => 'Found', //找到
	 * 303 => 'See Other', //参见其他
	 * 304 => 'Not Modified', //未改动
	 * 305 => 'Use Proxy', //使用代理
	 * 307 => 'Temporary Redirect', //暂时重定向
	 * 308 => 'Permanent Redirect', // RFC7238
	 * 400 => 'Bad Request', //错误请求
	 * 401 => 'Unauthorized', //未授权
	 * 402 => 'Payment Required', //要求付费
	 * 403 => 'Forbidden', //禁止
	 * 404 => 'Not Found', //未找到
	 * 405 => 'Method Not Allowed', //不允许的方法
	 * 406 => 'Not Acceptable', //不被采纳
	 * 407 => 'Proxy Authentication Required', //要求代理授权
	 * 408 => 'Request Timeout', //请求超时
	 * 409 => 'Conflict', //冲突
	 * 410 => 'Gone', //过期的
	 * 411 => 'Length Required', //要求的长度
	 * 412 => 'Precondition Failed', //前提不成立
	 * 413 => 'Payload Too Large', //请求实例太大
	 * 414 => 'URI Too Long', //请求URI太大
	 * 415 => 'Unsupported Media Type', //不支持的媒体类型
	 * 416 => 'Range Not Satisfiable', //无法满足的请求范围
	 * 417 => 'Expectation Failed', //失败的预期
	 * 418 => 'I'm a teapot', // RFC2324
	 * 421 => 'Misdirected Request', // RFC7540
	 * 422 => 'Unprocessable Entity', // RFC4918,资源错误
	 * 423 => 'Locked', // RFC4918
	 * 424 => 'Failed Dependency', // RFC4918
	 * 425 => 'Reserved for WebDAV advanced collections expired proposal', // RFC2817
	 * 426 => 'Upgrade Required', // RFC2817
	 * 428 => 'Precondition Required', // RFC6585
	 * 429 => 'Too Many Requests', // RFC6585
	 * 431 => 'Request Header Fields Too Large', // RFC6585
	 * 451 => 'Unavailable For Legal Reasons', // RFC7725
	 * 500 => 'Internal Server Error', //内部服务器错误
	 * 501 => 'Not Implemented', //未被使用
	 * 502 => 'Bad Gateway', //网关错误
	 * 503 => 'Service Unavailable', //不可用的服务/维护
	 * 504 => 'Gateway Timeout', //网关超时
	 * 505 => 'HTTP Version Not Supported', //HTTP版本未被支持
	 * 506 => 'Variant Also Negotiates (Experimental)', // RFC2295
	 * 507 => 'Insufficient Storage', // RFC4918
	 * 508 => 'Loop Detected', // RFC5842
	 * 510 => 'Not Extended', // RFC2774
	 * 511 => 'Network Authentication Required'
	 */
}
