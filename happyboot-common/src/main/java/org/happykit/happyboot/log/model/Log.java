package org.happykit.happyboot.log.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 日志对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/6/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 请求资源路径
     */
    private String requestUri;
    /**
     * 日志描述信息
     */
    private String description;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 请求类.方法
     */
    private String requestClass;
    /**
     * 请求ip
     */
    private String requestIp;
    /**
     * 请求参数
     */
    private String requestArgs;
    /**
     * 响应参数
     */
    private String responseArgs;
    /**
     * 请求用户
     */
    private String requestUser;
    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 请求耗时(ms)
     */
    private Long costTime;
}
