package org.happykit.happyboot.filter;//package com.ltitframework.boot.filter;
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * @author shaoqiang
// * @version 1.0 2020/3/5
// */
//@WebFilter(urlPatterns = "/*")
//@Slf4j
//public class HttpServletRequestWrapperFilter implements Filter {
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		log.info("初始化 HttpServletRequestWrapperFilter");
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		log.info(">>>HttpServletRequestWrapperFilter>>>>>>>doFilter在请求处理之前进行调用（Controller方法调用之前）");
//		ServletRequest requestWrapper = null;
//		if (request instanceof HttpServletRequest) {
//			HttpServletRequest httpRequest = (HttpServletRequest) request;
//			log.info("请求路径: [{}], 请求的全局id: [{}]", httpRequest.getServletPath(), UUID.randomUUID().toString());
//			requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
//		}
//		if (null == requestWrapper) {
//			chain.doFilter(request, response);
//		} else {
//			chain.doFilter(requestWrapper, response);
//		}
//	}
//
//	@Override
//	public void destroy() {
//		log.info("销毁 HttpServletRequestWrapperFilter");
//	}
//}
