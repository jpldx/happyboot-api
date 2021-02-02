package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.query.SysLogPageQueryParam;
import org.happykit.happyboot.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * web日志控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@Slf4j
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {

    private final SysLogService sysLogService;

    public SysLogController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return success(sysLogService.list());
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @GetMapping("/page")
    public R page(@Validated SysLogPageQueryParam param) {
        return success(sysLogService.listSysLogsByPage(param));
    }
}
