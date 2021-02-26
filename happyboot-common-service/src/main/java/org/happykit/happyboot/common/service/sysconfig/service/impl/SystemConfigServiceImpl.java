package org.happykit.happyboot.common.service.sysconfig.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.common.service.sysconfig.mapper.SystemConfigMapper;
import org.happykit.happyboot.common.service.sysconfig.service.ISystemConfigService;
import org.happykit.happyboot.constant.CacheConstant;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author chen.xudong
 * @date 2020/8/8
 */
@Slf4j
@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getValueByKey(String key) {
        String cacheValue = stringRedisTemplate.opsForValue().get(CacheConstant.PREFIX_SYS_CONFIG + key);
        if (cacheValue != null) {
            log.info("Read system config value from cache.");
            return cacheValue;
        }
        log.info("Read system config value from db.");
        String dbValue = systemConfigMapper.getValueByKey(key);
        if (dbValue == null) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_CONFIG);
        }
        stringRedisTemplate.opsForValue().set(CacheConstant.PREFIX_SYS_CONFIG + key, dbValue, 5, TimeUnit.MINUTES);
        return dbValue;
    }
}
