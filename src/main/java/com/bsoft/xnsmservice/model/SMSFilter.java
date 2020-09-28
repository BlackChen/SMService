package com.bsoft.xnsmservice.model;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName SMSFilter
 * @Description TODO
 * Created by blackchen on 2020/9/27 16:41
 */
/**
 * Servlet3.0特性
 * urlPatterns:过滤的url地址
 * filterName:过滤器名称
 */
@WebFilter(urlPatterns="/sendSms", filterName="SMSFilter")
public class SMSFilter implements Filter{
	/*
	 * 容器加载完成调用
	 * */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("filter init...");
	}

	/*
	 *  请求被过滤的时候调用
	 * */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("过滤...");
//
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpServletResponse resp = (HttpServletResponse)response;
//
//		Map<String, String[]> username = req.getParameterMap();
//
//		try {
//			if (username.containsKey("sType") || username.containsKey("content")) {
//				String v = req.getParameterValues("sType")[0];
//				System.out.println(v);
//				if (v.equals("100") || v.equals("101")){
//
//				} else {
//
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		chain.doFilter(request, response);

	}

	/*
	 * 容器被销毁的时候调用
	 * */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("销毁...");
	}
}
