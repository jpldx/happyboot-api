package org.happykit.happyboot.op.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.annotation.CheckPermission;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.op.model.form.OpBannerForm;
import org.happykit.happyboot.op.model.query.OpBannerPageQuery;
import org.happykit.happyboot.op.service.OpBannerService;
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
 * 轮播图
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/op/banner")
public class OpBannerController extends BaseController {

    private final OpBannerService opBannerService;

    public OpBannerController(OpBannerService opBannerService) {
        this.opBannerService = opBannerService;
    }

//	/**
//	 * 列表
//	 *
//	 * @return
//	 */
//	@GetMapping("/list")
//	@JsonView({View.SimpleView.class})
//	public R list() {
//		return success(opBannerService.list());
//	}

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(OpBannerPageQuery query) {
        return success(opBannerService.page(query));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotBlank String id) {
        return success(opBannerService.get(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
//	@JsonView({View.SimpleView.class})
    @CheckPermission
    public R add(@RequestBody @Valid OpBannerForm form) {
        return success(opBannerService.add(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PutMapping("/update")
//	@JsonView({View.SimpleView.class})
//    @CheckPermission
    public R update(@RequestBody @Validated(Update.class) OpBannerForm form) {
        return success(opBannerService.update(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
//    @CheckPermission
    public R delete(@NotEmpty String[] ids) {
        return success(opBannerService.removeByIds(Arrays.asList(ids)));
    }
}
