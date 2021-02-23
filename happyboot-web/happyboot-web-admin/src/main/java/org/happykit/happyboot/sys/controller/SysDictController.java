package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.sys.facade.SysDictFacade;
import org.happykit.happyboot.sys.model.dto.DictSelectDTO;
import org.happykit.happyboot.sys.model.form.SysDictForm;
import org.happykit.happyboot.sys.model.query.DictSelectQueryParam;
import org.happykit.happyboot.sys.model.query.SysDictPageQueryParam;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends BaseController {

    private final SysDictFacade sysDictFacade;

    public SysDictController(SysDictFacade sysDictFacade) {
        this.sysDictFacade = sysDictFacade;
    }

    /**
     * 列表
     *
     * @return
     */
    @Log("字典-列表")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(sysDictFacade.list());
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @Log("字典-分页列表")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Validated SysDictPageQueryParam param) {
        return success(sysDictFacade.listSysDictsByPage(param));
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @Log("字典-查询")
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotNull Long id) {
        return success(sysDictFacade.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @Log("字典-新增")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysDictForm form) {
        return success(sysDictFacade.saveSysDict(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @Log("字典-编辑")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysDictForm form) {
        return success(sysDictFacade.updateSysDict(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log("字典-删除")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public R delete(@NotEmpty Long[] ids) {
        return success(sysDictFacade.deleteSysDict(ids));
    }

    /**
     * 列表
     *
     * @return
     */
    @Log("字典-通过code查询列表")
    @PostMapping("/queryByDictCode")
    public R queryByDictCode(@RequestBody @Validated DictSelectQueryParam param) {
        return success(sysDictFacade.listDictSelect(param.getDictCode()));
    }

    /**
     * 通过code查询item
     *
     * @param dictCode 字典code
     * @return
     */
    @Log("字典-通过code查询列表")
    @GetMapping("/dictItems/{dictCode}")
    public R dictItems(@NotBlank @PathVariable(name = "dictCode") String dictCode) {
        return success(sysDictFacade.listDictSelect(dictCode));
    }


    /**
     * 通过多个code查询item
     *
     * @param dictCodes 字典codes
     * @return
     */
    @Log("字典-通过多个code查询列表")
    @GetMapping("/dictItemsMap/{dictCodes}")
    public R dictItemsMap(@NotBlank @PathVariable(name = "dictCodes") String dictCodes) {
        String[] dictCodeArr = dictCodes.split(",");
        Map<String, List<DictSelectDTO>> map = new HashMap<>();
        for (String dictCode : dictCodeArr) {
            map.put(dictCode, sysDictFacade.listDictSelect(dictCode));
        }
        return success(map);
    }
}
