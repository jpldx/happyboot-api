package org.happykit.happyboot.util;//package com.ltitframework.boot.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.Serializable;
//import java.util.Map;
//import java.util.SortedMap;
//
///**
// * 一、构造待签名字符串。待签名字符的生成规则如下：
// * 1.所有发送到后端的请求参数均加入签名，除了sign字端
// * 2.所有参与签名的请求参数都按照名称字符升序排列(参数名称不允许相同)
// * 3.如果参数值带有中文， 需要制定字符集编码为UTF-8
// * 4.如果参数值为空，那么该参数不参与签名
// * 5.将采宝的合作秘钥作为最后一个参数， 参数名为：key, 参数值就是采宝的合作秘钥本身
// * 6.将请求参数用`&`拼接起来(按照名称字符升序排列)
// * 二、用MD5算法，对待签名字符串进行加密， 生成的签名数据（32位大写字符）， 就是公共参数中sign的值。
// *
// * @author shaoqiang
// * @version 1.0 2020/3/5
// */
//@Slf4j
//public class SignUtils implements Serializable {
//
//	/**
//	 * 通过请求参数获取加密签名
//	 *
//	 * @param params    所有的请求参数都会在这里进行排序加密
//	 * @param secretKey 密钥
//	 * @param timestamp 时间戳
//	 * @return
//	 */
//	public static String getSign(SortedMap<String, String> params, String secretKey, long timestamp) {
//		StringBuilder sb = new StringBuilder();
//		for (Map.Entry entry : params.entrySet()) {
//			if (!StringUtils.isEmpty(entry.getKey()) && !StringUtils.isEmpty(entry.getValue())) {
//				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
//			}
//		}
//		sb.append("timestamp").append("=").append(timestamp)
//				.append("&").append("key").append("=").append(secretKey);
//		log.info("Before Sign : {}", sb.toString());
//		return DigestUtils.md5Hex(sb.toString()).toUpperCase();
//	}
//}
