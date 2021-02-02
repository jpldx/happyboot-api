package org.happykit.happyboot.log.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * web日志对象
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Data
@Accessors(fluent = true)
public class WebLogDTO {
    /**
     * 请求路径
     */
    private String url;
    /**
     * 日志描述信息
     */
    private String description;
    /**
     * 请求方法
     */
    private String httpMethod;
    /**
     * 请求class
     */
    private String classMethod;
    /**
     * 来源IP
     */
    private String ip;
    /**
     * 请求参数
     */
    private String requestArgs;
    /**
     * 响应结果
     */
    private String responseArgs;
    /**
     * 耗时
     */
    private Long costTime;
}
