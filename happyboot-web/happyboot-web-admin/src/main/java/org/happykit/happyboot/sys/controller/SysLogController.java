package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.log.model.Log;
import org.happykit.happyboot.log.model.LogPageQuery;
import org.happykit.happyboot.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/3/4
 */
@Slf4j
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {

    @Autowired
    private LogService logService;

//    /**
//     * 列表
//     *
//     * @return
//     */
//    @GetMapping("/list")
//    public R list() {
//        return success(sysLogService.list());
//    }

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public R page(@Validated LogPageQuery query) {
        return success(logService.selectPageList(query, LogType.SYS));
    }
}
