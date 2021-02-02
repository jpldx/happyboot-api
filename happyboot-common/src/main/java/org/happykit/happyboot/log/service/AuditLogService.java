package org.happykit.happyboot.log.service;


import org.happykit.happyboot.log.model.Log;

/**
 * @author shaoqiang
 * @version 1.0 2020/7/8
 */
public interface AuditLogService {
    /**
     * TODO 保存审计内容（日志）
     *
     * @param log
     */
    void saveAuditLog(Log log);
}
