package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.sys.model.form.SysDictItemForm;
import org.happykit.happyboot.sys.model.query.SysDictItemPageQueryParam;
import org.happykit.happyboot.sys.service.SysDictItemService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 数据字典项控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/dictItem")
public class SysDictItemController extends BaseController {

    private final SysDictItemService sysDictItemService;

    public SysDictItemController(SysDictItemService sysDictItemService) {
        this.sysDictItemService = sysDictItemService;
    }

    /**
     * 列表
     *
     * @return
     */
    @Log("字典值-列表")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(sysDictItemService.list());
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @Log("字典值-分页列表")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Validated SysDictItemPageQueryParam param) {
        return success(sysDictItemService.listSysDictItemsByPage(param));
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @Log("字典值-查询")
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotNull Long id) {
        return success(sysDictItemService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @Log("字典值-新增")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysDictItemForm form) {
        return success(sysDictItemService.saveSysDictItem(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @Log("字典值-更新")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysDictItemForm form) {
        return success(sysDictItemService.updateSysDictItem(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log("字典值-删除")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {
        return success(sysDictItemService.deleteSysDictItem(ids));
    }

}
