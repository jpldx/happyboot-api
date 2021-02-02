package org.happykit.happyboot.filter;//package com.ltitframework.boot.filter;
//
//import com.alibaba.fastjson.JSONObject;
//
//import com.baomidou.mybatisplus.extension.api.R;
//import com.ltitframework.boot.entity.sys.SysAccountDO;
//
//import com.ltitframework.boot.service.sys.SysAccountService;
//
//import com.ltitframework.boot.util.HttpUtils;
//import com.ltitframework.boot.util.SignUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.SortedMap;
//
//@WebFilter(urlPatterns = "/*")
//@Slf4j
//public class SignAuthFilter implements Filter {
//
//	private static final String APP_KEY = "appKey";
//	private static final String SIGNATURE = "signature";
//
//	@Autowired
//	private SysAccountService sysAccountService;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		log.info("初始化 SignAuthFilter");
//	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//		// 防止流读取一次后就没有了, 所以需要将流继续写出去
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
//
//		String appKey = request.getHeader(APP_KEY);
//		// 通过appKey获取secretKey
//		String signature = request.getHeader(SIGNATURE);
//
//		if (StringUtils.isNotBlank(appKey) && StringUtils.isNotBlank(signature)) {
//			SysAccountDO sysAccount = sysAccountService.getByAppKey(appKey);
//
//			log.info("get appKey is ---> " + appKey);
//
//			log.info("get signature is ---> " + signature);
//			if (sysAccount != null) {
//				log.info("get secretKey is ---> " + sysAccount.getSecretKey());
//			}
//			// 获取全部参数(包括URL和body上的)
//			SortedMap<String, String> allParams = HttpUtils.getAllParams(requestWrapper);
//			// 生成签名
//			String sign = SignUtils.getSign(allParams, sysAccount.getSecretKey());
//			// 比较签名
//			if (signature.equals(sign)) {
//				log.info("签名通过");
//				filterChain.doFilter(requestWrapper, response);
//				return;
//			}
//		}
//
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json;charset=utf-8");
//		PrintWriter printWriter = response.getWriter();
//		String json = JSONObject.toJSONString(R.failed("请求失败"));
//		printWriter.write(json);
//	}
//
//	@Override
//	public void destroy() {
//		log.info("销毁 SignAuthFilter");
//	}
//}
