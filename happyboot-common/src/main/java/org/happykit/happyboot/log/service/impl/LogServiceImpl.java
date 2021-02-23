package org.happykit.happyboot.log.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.log.model.Log;
import org.happykit.happyboot.log.model.LogPageQuery;
import org.happykit.happyboot.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

//    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveLog(Log log, LogType logType) {
        mongoTemplate.save(log, logType.getCollectName());
    }

    @Override
    public Page<Log> selectPageList(LogPageQuery query, LogType logType) {
        Long pageNo = query.getPageNo();
        Long pageSize = query.getPageSize();

        Query q = new Query();
        Criteria criteria = new Criteria();
        q.skip(pageSize * (pageNo - 1));
        q.limit(pageSize.intValue());
//        q.addCriteria(Criteria.where("requestUser").(query.getUsername()));

        List<Log> list = mongoTemplate.find(q, Log.class, logType.getCollectName());
        Page<Log> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        page.setRecords(list);
        return page;
    }
}
