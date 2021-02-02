package org.happykit.happyboot.interceptor;//package com.ltitframework.boot.interceptor;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.extension.api.R;
//import com.ltitframework.boot.filter.BodyReaderHttpServletRequestWrapper;
//import com.ltitframework.boot.sys.entity.SysAccountDO;
//import com.ltitframework.boot.sys.service.SysAccountService;
//import com.ltitframework.boot.util.HttpUtils;
//import com.ltitframework.boot.util.SignUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.SortedMap;
//
///**
// * @author shaoqiang
// * @version 1.0 2020/3/5
// */
//@Slf4j
//@Component
//public class ApiSignInterceptor implements HandlerInterceptor {
//
//	/**
//	 * 签名超时时长，默认时间为5分钟，ms
//	 */
//	private static final int SIGN_EXPIRED_TIME = 5 * 60 * 1000;
//
//	private static final String HEADER_APP_KEY = "appKey";
//	private static final String HEADER_SIGNATURE = "signature";
//	private static final String HEADER_TIMESTAMP = "timestamp";
//
//	@Autowired
//	private SysAccountService sysAccountService;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		if (request instanceof BodyReaderHttpServletRequestWrapper) {
//
//			String headerTimestamp = request.getHeader(HEADER_TIMESTAMP);
//			String headerAppKey = request.getHeader(HEADER_APP_KEY);
//			String headerSignature = request.getHeader(HEADER_SIGNATURE);
//			if (StringUtils.isBlank(headerTimestamp)) {
//				response.setCharacterEncoding("utf-8");
//				response.setContentType("application/json;charset=utf-8");
//				response.getWriter().write(JSONObject.toJSONString(R.failed("请求时间戳不合法")));
//				return false;
//			}
//
//			Long ts = Long.valueOf(headerTimestamp);
//			// 禁止超时签名
//			if (System.currentTimeMillis() - ts > SIGN_EXPIRED_TIME) {
//				response.setCharacterEncoding("utf-8");
//				response.setContentType("application/json;charset=utf-8");
//				response.getWriter().write(JSONObject.toJSONString(R.failed("请求超时")));
//				return false;
//			}
//
//			if (StringUtils.isBlank(headerAppKey) || StringUtils.isBlank(headerSignature)) {
//				response.setCharacterEncoding("utf-8");
//				response.setContentType("application/json;charset=utf-8");
//				response.getWriter().write(JSONObject.toJSONString(R.failed("加密KEY不合法")));
//				return false;
//			}
//
//			SysAccountDO sysAccount = sysAccountService.getByAppKey(headerAppKey);
//			if (sysAccount == null) {
//				response.setCharacterEncoding("utf-8");
//				response.setContentType("application/json;charset=utf-8");
//				response.getWriter().write(JSONObject.toJSONString(R.failed("加密KEY不合法")));
//				return false;
//			}
//			/// application/x-www-form-urlencoded方式获取参数
//			/*SortedMap<String, String> result = new TreeMap<>();
//			Enumeration<?> pNames = request.getParameterNames();
//			// 该方式获取表单数据application/x-www-form-urlencoded
//			while (pNames.hasMoreElements()) {
//				String pName = (String) pNames.nextElement();
//				String pValue = request.getParameter(pName);
//				result.put(pName, pValue);
//			}*/
//
//			// 通过application/json方式获取全部参数
//			SortedMap<String, String> allParams = HttpUtils.getAllParams(request);
//			// 生成签名
//			String sign = SignUtils.getSign(allParams, sysAccount.getSecretKey(), ts);
//			// 比较签名
//			if (!headerSignature.equals(sign)) {
//				log.info("签名错误");
//				response.setCharacterEncoding("utf-8");
//				response.setContentType("application/json;charset=utf-8");
//				response.getWriter().write(JSONObject.toJSONString(R.failed("签名错误")));
//				return false;
//			}
//		}
//
//		return true;
//	}
//}
