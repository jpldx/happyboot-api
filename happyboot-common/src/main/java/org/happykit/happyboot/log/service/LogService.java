package org.happykit.happyboot.log.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.log.model.Log;

/**
 * 日志
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/6/9
 */
public interface LogService {
    /**
     * 保存日志
     *
     * @param log     日志对象
     * @param logType 日志类型
     */
    void saveLog(Log log, LogType logType);

    Page<Log> pageList(int pageNo, int pageSize);
}
