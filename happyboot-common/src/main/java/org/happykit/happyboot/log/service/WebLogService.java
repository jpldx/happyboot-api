package org.happykit.happyboot.log.service;


import org.happykit.happyboot.log.model.WebLogDTO;

/**
 * 日志接口服务
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
public interface WebLogService {
    /**
     * 保存日志
     *
     * @param dto
     */
    void saveWebLog(WebLogDTO dto);
}
