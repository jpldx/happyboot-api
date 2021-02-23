package org.happykit.happyboot.log.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.log.model.Log;
import org.happykit.happyboot.log.model.LogPageQuery;

import java.util.List;

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

    /**
     * 分页列表
     *
     * @param query   查询对象
     * @param logType 日志类型
     * @return
     */
    Page<Log> selectPageList(LogPageQuery query, LogType logType);
}
