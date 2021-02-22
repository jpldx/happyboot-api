package org.happykit.happyboot.log.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 日志对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/6/9
 */
@Data
@Accessors(fluent = true)
public class Log {
    /**
     * 请求路径
     */
    private String url;
    /**
     * 描述信息
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
     * 请求来源ip
     */
    private String ip;
    /**
     * 请求参数
     */
    private String requestArgs;
    /**
     * 响应参数
     */
    private String responseArgs;
    /**
     * 请求耗时
     */
    private Long costTime;
    /**
     * 创建时间
     */
    private String createTime;
}
