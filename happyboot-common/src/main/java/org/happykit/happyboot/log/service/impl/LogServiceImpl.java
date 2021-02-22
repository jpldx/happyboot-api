package org.happykit.happyboot.log.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.log.model.Log;
import org.happykit.happyboot.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 日志
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/6/9
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    private static final String SYS_LOG_COLLECTION_NAME = "sys_logs";
    private static final String BIZ_LOG_COLLECTION_NAME = "biz_logs";

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveLog(Log log, LogType logType) {
        switch (logType) {
            case SYS:
                mongoTemplate.save(log, SYS_LOG_COLLECTION_NAME);
                break;
            case BIZ:
                mongoTemplate.save(log, BIZ_LOG_COLLECTION_NAME);
                break;
            default:
                mongoTemplate.save(log);
        }
    }

    @Override
    public Page<Log> pageList(int pageNo, int pageSize) {
        Query query = new Query();
//        query.addCriteria()
        return null;
    }
}
