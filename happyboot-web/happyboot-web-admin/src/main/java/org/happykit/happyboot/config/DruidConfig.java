package org.happykit.happyboot.config;//package com.ltitframework.boot.config;
//
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author shaoqiang
// * @version 1.0 2020/3/30
// */
//@Configuration
//public class DruidConfig {
//
//	/**
//	 * 注册一个StatViewServlet
//	 *
//	 * @return servlet registration bean
//	 */
//	@Bean
//	public ServletRegistrationBean druidStatViewServlet() {
//		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
//				new StatViewServlet(), "/druid/*");
//
//		servletRegistrationBean.addInitParameter("loginUsername", "admin");
//		servletRegistrationBean.addInitParameter("loginPassword", "123456");
//		servletRegistrationBean.addInitParameter("resetEnable", "false");
//		return servletRegistrationBean;
//	}
//
//	/**
//	 * 注册一个：filterRegistrationBean
//	 *
//	 * @return filter registration bean
//	 */
//	@Bean
//	public FilterRegistrationBean druidStatFilter() {
//
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
//				new WebStatFilter());
//
//		// 添加过滤规则.
//		filterRegistrationBean.addUrlPatterns("/*");
//
//		// 添加不需要忽略的格式信息.
//		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//		return filterRegistrationBean;
//	}
//
//}
