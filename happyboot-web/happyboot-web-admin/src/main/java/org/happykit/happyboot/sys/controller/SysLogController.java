package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.sys.model.form.SysLogForm;
import org.happykit.happyboot.sys.model.query.SysLogPageQuery;
import org.happykit.happyboot.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private SysLogService sysLogService;

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public R page(@Validated SysLogPageQuery query) {
        String type = query.getType();
        if ("sys".equals(type)) {
            return success(sysLogService.selectPageList(query, LogType.SYS));
        } else if ("biz".equals(type)) {
            return success(sysLogService.selectPageList(query, LogType.BIZ));
        }
        return R.failed(SysExceptionConstant.UNSUPPORTED_PARAM_TYPE);
    }

    /**
     * 清空日志
     *
     * @return
     */
    @PostMapping("/clear")
    public R clear(@Validated @RequestBody SysLogForm form) {
        String type = form.getType();
        if ("sys".equals(type)) {
            sysLogService.clear(LogType.SYS);
            return R.ok(null);
        } else if ("biz".equals(type)) {
            sysLogService.clear(LogType.BIZ);
            return R.ok(null);
        }
        return R.failed(SysExceptionConstant.UNSUPPORTED_PARAM_TYPE);
    }
}
