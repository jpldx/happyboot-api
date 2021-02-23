package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.log.annotation.LogType;
import org.happykit.happyboot.log.model.LogPageQuery;
import org.happykit.happyboot.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public R page(@Validated LogPageQuery query) {
        String type = query.getType();
        if ("sys".equals(type)) {
            return success(logService.selectPageList(query, LogType.SYS));
        } else if ("biz".equals(type)) {
            return success(logService.selectPageList(query, LogType.BIZ));
        }
        return R.failed(SysExceptionConstant.UNSUPPORTED_PARAM_TYPE);
    }

////    /**
//     * 列表
//     *
//     * @return
//     */
//    @GetMapping("/list")
//    public R list() {
//        return success(sysLogService.list());
//    }
}
