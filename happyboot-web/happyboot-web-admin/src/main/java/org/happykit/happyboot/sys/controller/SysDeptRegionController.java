package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.model.model.ModifyNodeModel;
import org.happykit.happyboot.sys.model.form.SysDeptRegionForm;
import org.happykit.happyboot.sys.model.query.SysDeptRegionQueryParam;
import org.happykit.happyboot.sys.service.SysDeptRegionService;
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
 * 区域部门表控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated
@Slf4j
@RestController
@RequestMapping("sys/deptRegion")
public class SysDeptRegionController extends BaseController {

    private final SysDeptRegionService sysDeptRegionService;

    public SysDeptRegionController(SysDeptRegionService sysDeptRegionService) {
        this.sysDeptRegionService = sysDeptRegionService;
    }

    /**
     * 一次性加载树
     *
     * @return
     */
    @GetMapping("/tree")
    public R tree() {
        return success(sysDeptRegionService.tree());
    }

    /**
     * 加载树节点
     *
     * @return
     */
    @GetMapping("/queryTreeByParentId")
    public R queryTreeByParentId(@NotNull Long parentId) {
        return success(sysDeptRegionService.tree(parentId));
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list(SysDeptRegionQueryParam param) {
        return success(sysDeptRegionService.listSysDeptRegions(param));
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
        return success(sysDeptRegionService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysDeptRegionForm form) {
        return success(sysDeptRegionService.saveSysDeptRegion(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysDeptRegionForm form) {
        return success(sysDeptRegionService.updateSysDeptRegion(form));
    }

    /**
     * 变更节点
     *
     * @param form
     * @return
     */
    @PostMapping("/modifyNode")
    public R modifyNode(@RequestBody @Validated ModifyNodeModel form) {
        return success(sysDeptRegionService.updateSysDeptRegionNode(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {

        return success(sysDeptRegionService.deleteSysDeptRegion(ids));
    }

    /**
     * 通过用户ID查询区域ID集合
     *
     * @param userId
     * @return
     */
    @GetMapping("/queryIdsByUserId")
    public R queryIdsByUserId(@NotNull String userId) {
        Set<String> ids = sysDeptRegionService.listSysDeptRegionsByUserId(userId).stream()
                .map(m -> m.getId().toString()).collect(Collectors.toSet());
        return success(ids);
    }

    /**
     * 通过对象ID查询区域ID集合
     *
     * @param objId
     * @return
     */
    @GetMapping("/queryIdsByObjId")
    public R queryIdsByObjId(@NotNull String objId) {
        Set<String> ids = sysDeptRegionService.listSysDeptRegionsByObjId(objId).stream().map(m -> m.getId().toString())
                .collect(Collectors.toSet());
        return success(ids);
    }
}
