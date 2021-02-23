package org.happykit.happyboot.log.annotation;

/**
 * 日志类型枚举
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/22
 */
public enum LogType {
    /**
     * 系统日志
     */
    SYS("sys_logs"),
    /**
     * 业务日志
     */
    BIZ("biz_logs");

    /**
     * mongodb集合名称
     */
    private final String collectName;

    LogType(String collectName) {
        this.collectName = collectName;
    }

    public String getCollectName() {
        return this.collectName;
    }
}
