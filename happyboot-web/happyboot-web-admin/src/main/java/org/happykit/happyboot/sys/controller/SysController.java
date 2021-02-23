package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.log.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 系统设置
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/setting")
public class SysController extends BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 清除缓存
     *
     * @return
     */
    @Log("系统-清除缓存")
    @GetMapping("/clearCache")
    public R clearCache() {
        // 清除字典缓存
        Set<String> dictKeys = redisTemplate.opsForValue().getOperations().keys(CacheConstant.PREFIX_DICT + "::*");
        long effect = redisTemplate.delete(dictKeys);
        return success("缓存清除成功");
    }
}
