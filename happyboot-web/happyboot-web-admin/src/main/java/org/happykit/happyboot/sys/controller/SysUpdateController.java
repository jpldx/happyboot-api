package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.annotation.CheckPermission;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.sys.model.form.SysUpdateForm;
import org.happykit.happyboot.sys.model.query.SysUpdatePageQueryParam;
import org.happykit.happyboot.sys.service.SysUpdateService;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * APP版本更新
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
@Validated
@Slf4j
@RestController
@RequestMapping("sys/update")
public class SysUpdateController extends BaseController {

    private final SysUpdateService sysUpdateService;

    public SysUpdateController(SysUpdateService sysUpdateService) {
        this.sysUpdateService = sysUpdateService;
    }


    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @Log("App更新-分页列表")
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(SysUpdatePageQueryParam param) {
        return success(sysUpdateService.listSysUpdatesByPage(param));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @Log("App更新-新增")
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    @CheckPermission
    public R add(@RequestBody @Validated SysUpdateForm form) {
        return success(sysUpdateService.saveSysUpdate(form));
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log("App更新-删除")
    @DeleteMapping("/delete")
    @CheckPermission
    public R delete(@NotEmpty String[] ids) {
        return success(sysUpdateService.deleteSysUpdate(ids));
    }
}
