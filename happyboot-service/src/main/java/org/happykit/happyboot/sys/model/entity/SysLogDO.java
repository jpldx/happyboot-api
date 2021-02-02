package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统日志表
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log")
public class SysLogDO extends BaseEntity {
    /**
     * 耗时
     */
    private Long costTime;
    /**
     * ip
     */
    private String ip;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 请求类型
     */
    private String requestType;
    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 操作详细日志
     */
    private String logContent;
}
