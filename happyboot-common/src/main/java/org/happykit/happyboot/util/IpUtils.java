package org.happykit.happyboot.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 获取IP地址类
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/5/11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class IpUtils {
    static final int MAX_IP_AGENT_COUNT = 15;
    private static final String USER_AGENT = "User-Agent";
    private static final String UNKNOWN = "unknown";
    private static final String ANDROID = "Android";
    private static final String LINUX = "Linux";
    private static final String IPHONE = "iPhone";
    private static final String WINDOWS = "Windows";
    private static final String CHROME = "Chrome";

    /**
     * 获取客户端真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        // 注意本地测试时，浏览器请求不要用localhost，要用本机IP访问项目地址，不然这里取不到ip
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (!isNull(ip) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if (isNull(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isNull(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isNull(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isNull(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isNull(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (isNull(ip)) {
            ip = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//        } else if (ip.length() > MAX_IP_AGENT_COUNT) {
//            String[] ips = ip.split(",");
//            for (int index = 0; index < ips.length; index++) {
//                String strIp = (String) ips[index];
//                if (!(UNKNOWN.equalsIgnoreCase(strIp))) {
//                    ip = strIp;
//                    break;
//                }
//            }
//        }
        return ip;
    }

    /**
     * 判断ip是否为空
     *
     * @param ip
     * @return
     */
    private static boolean isNull(String ip) {
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }

    /**
     * 判断是否是移动端
     *
     * @param requestHeader
     * @return
     */
    public static boolean isMobileDevice(String requestHeader) {
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android", "mac os", "windows phone"};
        if (requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 UserAgent
     *
     * @param request
     * @return
     */
    public static String isMobileDevice(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        return request.getHeader(USER_AGENT);
    }

}