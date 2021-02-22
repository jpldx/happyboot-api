package org.happykit.happyboot.log.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

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
    /**
     * 创建时间
     */
    private String createTime;
}
