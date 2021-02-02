package org.happykit.happyboot.log.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shaoqiang
 * @version 1.0 2020/7/8
 */
@Data
public class Log implements Serializable {
    /**
     * 标题
     */
    private String title;
    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;

    /**
     * 操作说明
     */
    private String operation;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 请求服务器地址
     */
    private String serverAddr;

    /**
     * 是否异常
     */
    private boolean exception;

    /**
     * 异常信息
     */
    private String exceptionName;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 设备名称/操作系统
     */
    private String deviceName;

    /**
     * 浏览器名称
     */
    private String browserName;

    /**
     * 执行时间
     */
    private Long executeTime;

    /**
     * 服务名称
     */
    private String serviceName;

}
