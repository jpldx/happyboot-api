package org.happykit.happyboot.demo.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.demo.model.form.DemoDataForm;
import org.happykit.happyboot.demo.model.query.DemoDataPageQueryParam;
import org.happykit.happyboot.demo.service.DemoDataService;
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
 * @author shaoqiang
 * @version 1.0 2020/06/10
 */
@Validated
@Slf4j
@RestController
@RequestMapping("demo/demodata")
public class DemoDataController extends BaseController {

    private final DemoDataService demoDataService;

    public DemoDataController(DemoDataService demoDataService) {
        this.demoDataService = demoDataService;
    }

    /**
     * 列表
     *
     * @return
     */
    @PostMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(demoDataService.list());
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @PostMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@RequestBody @Validated DemoDataPageQueryParam param) {
        return success(demoDataService.listDemoDatasByPage(param));
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
        return success(demoDataService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated DemoDataForm form) {
        return success(demoDataService.saveDemoData(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) DemoDataForm form) {
        return success(demoDataService.updateDemoData(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {
        return success(demoDataService.deleteDemoData(ids));
    }
}
