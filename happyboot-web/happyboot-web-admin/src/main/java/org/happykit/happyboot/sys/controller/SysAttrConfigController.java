package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.form.SysAttrConfigForm;
import org.happykit.happyboot.sys.model.query.SysAttrConfigPageQueryParam;
import org.happykit.happyboot.sys.service.SysAttrConfigService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Validated
@Slf4j
@RestController
@RequestMapping("sys/sysattrconfig")
public class SysAttrConfigController extends BaseController {

    private final SysAttrConfigService sysAttrConfigService;

    public SysAttrConfigController(SysAttrConfigService sysAttrConfigService) {
        this.sysAttrConfigService = sysAttrConfigService;
    }

    /**
     * 列表
     *
     * @return
     */
    @PostMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(sysAttrConfigService.list());
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @PostMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@RequestBody @Validated SysAttrConfigPageQueryParam param) {
        return success(sysAttrConfigService.listSysAttrConfigsByPage(param));
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PostMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotNull Long id) {
        return success(sysAttrConfigService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysAttrConfigForm form) {
        return success(sysAttrConfigService.saveSysAttrConfig(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysAttrConfigForm form) {
        return success(sysAttrConfigService.updateSysAttrConfig(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {
        return success(sysAttrConfigService.deleteSysAttrConfig(ids));
    }
}
