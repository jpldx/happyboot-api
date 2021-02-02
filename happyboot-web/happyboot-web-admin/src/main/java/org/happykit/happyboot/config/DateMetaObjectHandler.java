package org.happykit.happyboot.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.happykit.happyboot.base.BaseEntity;
import org.happykit.happyboot.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis-Plus 字段填充
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/15
 */
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(DateMetaObjectHandler.class);
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = securityUtils.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, BaseEntity.CREATE_BY, String.class, userId);
        this.strictInsertFill(metaObject, BaseEntity.CREATE_TIME, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, BaseEntity.UPDATE_BY, String.class, userId);
        this.strictInsertFill(metaObject, BaseEntity.UPDATE_TIME, LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = securityUtils.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, BaseEntity.UPDATE_BY, String.class, userId);
        this.strictUpdateFill(metaObject, BaseEntity.UPDATE_TIME, LocalDateTime.class, now);
    }
}
