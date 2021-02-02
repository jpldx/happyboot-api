package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.form.SysTreeDictForm;
import org.happykit.happyboot.sys.service.SysTreeDictService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 树状数据字典控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/treeDict")
public class SysTreeDictController extends BaseController {

    private final SysTreeDictService sysTreeDictService;

    public SysTreeDictController(SysTreeDictService sysTreeDictService) {
        this.sysTreeDictService = sysTreeDictService;
    }

    /**
     * 列表
     *
     * @return
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(sysTreeDictService.list());
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotNull Long id) {
        return success(sysTreeDictService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysTreeDictForm form) {
        return success(sysTreeDictService.saveSysTreeDict(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysTreeDictForm form) {
        return success(sysTreeDictService.updateSysTreeDict(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {
        return success(sysTreeDictService.deleteSysTreeDict(ids));
    }
}
