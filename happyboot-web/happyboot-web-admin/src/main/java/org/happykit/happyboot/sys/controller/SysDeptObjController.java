package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.form.SysDeptObjForm;
import org.happykit.happyboot.sys.model.form.SysDeptObjModifyNodeForm;
import org.happykit.happyboot.sys.model.query.SysDeptObjQueryParam;
import org.happykit.happyboot.sys.service.SysDeptObjService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 对象内部部门表控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/deptObj")
public class SysDeptObjController extends BaseController {

    private final SysDeptObjService sysDeptObjService;

    public SysDeptObjController(SysDeptObjService sysDeptObjService) {
        this.sysDeptObjService = sysDeptObjService;
    }

    /**
     * 一次性加载树
     *
     * @return
     */
    @GetMapping("/tree")
    public R tree() {
        return success(sysDeptObjService.tree());
    }

    /**
     * 通过父级节点加载树
     *
     * @return
     */
    @GetMapping("/queryTreeByParentId")
    public R queryTreeByParentId(@NotNull Long parentId) {
        return success(sysDeptObjService.tree(parentId));
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list(SysDeptObjQueryParam param) {
        return success(sysDeptObjService.listSysDeptObjs(param));
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
        return success(sysDeptObjService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysDeptObjForm form) {
        return success(sysDeptObjService.saveSysDeptObj(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysDeptObjForm form) {
        return success(sysDeptObjService.updateSysDeptObj(form));
    }

    /**
     * 变更节点
     *
     * @param form
     * @return
     */
    @PostMapping("/modifyNode")
    public R modifyNode(@RequestBody @Validated SysDeptObjModifyNodeForm form) {
        return success(sysDeptObjService.updateSysDeptObjNode(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {
        return success(sysDeptObjService.deleteSysDeptObj(ids));
    }

    /**
     * 通过用户ID查询部门ID集合
     *
     * @param userId
     * @return
     */
    @GetMapping("/queryIdsByUserId")
    public R queryIdsByUserId(@NotNull String userId) {
        Set<String> ids = sysDeptObjService.listSysDeptObjsByUserId(userId).stream().map(m -> m.getId().toString())
                .collect(Collectors.toSet());
        return success(ids);
    }

}
