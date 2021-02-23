package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.sys.model.form.SysConfigForm;
import org.happykit.happyboot.sys.model.query.SysConfigPageQueryParam;
import org.happykit.happyboot.sys.service.SysConfigService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

/**
 * 系统配置
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {

    private final SysConfigService sysConfigService;

    public SysConfigController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }
//
//	/**
//	 * 列表
//	 *
//	 * @return
//	 */
//	@PostMapping("/list")
//	@JsonView({View.SimpleView.class})
//	public R list() {
//		return success(sysAttrConfigService.list());
//	}

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @Log("系统配置-分页列表")
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Valid SysConfigPageQueryParam query) {
        return success(sysConfigService.page(query));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Log("系统配置-查询")
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotBlank String id) {
        return success(sysConfigService.get(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @Log("系统配置-新增")
    @PostMapping("/add")
//	@JsonView({View.SimpleView.class})
    public R add(@RequestBody @Valid SysConfigForm form) {
        return success(sysConfigService.add(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @Log("系统配置-更新")
    @PutMapping("/update")
//	@JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysConfigForm form) {
        return success(sysConfigService.update(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log("系统配置-删除")
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(sysConfigService.removeByIds(Arrays.asList(ids)));
    }
}
