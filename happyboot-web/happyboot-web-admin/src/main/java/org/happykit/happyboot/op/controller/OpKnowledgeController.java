package org.happykit.happyboot.op.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.annotation.CheckPermission;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.op.model.form.OpKnowledgeForm;
import org.happykit.happyboot.op.model.query.OpKnowledgePageQuery;
import org.happykit.happyboot.op.service.OpKnowledgeService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

/**
 * 知识库
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/op/knowledge")
public class OpKnowledgeController extends BaseController {

    private final OpKnowledgeService opKnowledgeService;

    public OpKnowledgeController(OpKnowledgeService opKnowledgeService) {
        this.opKnowledgeService = opKnowledgeService;
    }

//	/**
//	 * 列表
//	 *
//	 * @return
//	 */
//	@GetMapping("/list")
//	@JsonView({View.SimpleView.class})
//	public R list() {
//		return success(sysKnowledgeService.list());
//	}

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(OpKnowledgePageQuery query) {
        return success(opKnowledgeService.page(query));
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
        return success(opKnowledgeService.get(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @CheckPermission
    public R add(@RequestBody @Validated OpKnowledgeForm form) {
        return success(opKnowledgeService.add(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PutMapping("/update")
    @CheckPermission
    public R update(@RequestBody @Validated(Update.class) OpKnowledgeForm form) {
        return success(opKnowledgeService.update(form));
    }

//    /**
////     * 发布
////     *
////     * @param id
////     * @return
////     */
//    @PutMapping("/send/{id}")
//    @JsonView({View.SimpleView.class})
//    @CheckPermission
//    public R send(@PathVariable String id) {
//        return success(sysKnowledgeService.sendSysKnowledge(id));
//    }
//
//    /**
//     * 撤回
//     *
//     * @param id
//     * @return
//     */
//    @PutMapping("/back/{id}")
//    @JsonView({View.SimpleView.class})
//    @CheckPermission
//    public R back(@PathVariable String id) {
//        return success(sysKnowledgeService.backSysKnowledge(id));
//    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(opKnowledgeService.removeByIds(Arrays.asList(ids)));
    }
}
