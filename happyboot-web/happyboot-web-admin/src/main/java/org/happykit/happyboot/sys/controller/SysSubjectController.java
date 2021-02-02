package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.facade.SysSubjectFacade;
import org.happykit.happyboot.sys.model.form.SysSubjectForm;
import org.happykit.happyboot.sys.model.query.SysSubjectPageQueryParam;
import org.happykit.happyboot.sys.service.SysSubjectService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 主体
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
@Validated
@Slf4j
@RestController
@RequestMapping("sys/subject")
public class SysSubjectController extends BaseController {

    private final SysSubjectService sysSubjectService;
    private final SysSubjectFacade sysSubjectFacade;

    public SysSubjectController(SysSubjectService sysSubjectService, SysSubjectFacade sysSubjectFacade) {
        this.sysSubjectService = sysSubjectService;
        this.sysSubjectFacade = sysSubjectFacade;
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(sysSubjectService.list());
    }

    /**
     * 分页列表
     *
     * @param param
     * @return
     */
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Validated SysSubjectPageQueryParam param) {
        return success(sysSubjectService.listSysObjsByPage(param));
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
        return success(sysSubjectService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysSubjectForm form) {
        return success(sysSubjectFacade.saveSysObj(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysSubjectForm form) {
        // TODO 同时更新部门表根节点名称
        return success(sysSubjectService.updateSysObj(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(sysSubjectService.deleteSysObj(ids));
    }

//    /**
//     * 新增区域-主体关联
//     *
//     * @param form
//     * @return
//     */
//    @PostMapping("/saveObjRegion")
//    public R saveObjRegion(@RequestBody @Validated SysObjRegionForm form) {
//        return success(sysSubjectFacade.saveObjRegion(form));
//    }
}
