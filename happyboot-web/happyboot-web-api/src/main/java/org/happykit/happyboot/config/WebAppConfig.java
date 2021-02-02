package org.happykit.happyboot.config;//package com.ltitframework.boot.config;
//
//import com.ltitframework.boot.interceptor.ApiSignInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * 拦截器配置
// *
// * @author shaoqiang
// * @version 1.0 2020/3/5
// */
//@Configuration
//public class WebAppConfig extends WebMvcConfigurationSupport {
//
//	@Override
//	protected void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(getApiSignInterceptor()).addPathPatterns("/**");
//	}
//
//
//	@Bean
//	public ApiSignInterceptor getApiSignInterceptor() {
//		return new ApiSignInterceptor();
//	}
//}
