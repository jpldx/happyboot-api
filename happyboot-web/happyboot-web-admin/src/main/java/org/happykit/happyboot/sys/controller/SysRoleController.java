package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.facade.SysRoleFacade;
import org.happykit.happyboot.sys.model.form.SysRefRolePermissionForm;
import org.happykit.happyboot.sys.model.form.SysRefRoleUserForm;
import org.happykit.happyboot.sys.model.form.SysRoleForm;
import org.happykit.happyboot.sys.model.query.SysRolePageQueryParam;
import org.happykit.happyboot.sys.service.SysRoleService;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    private final SysRoleService sysRoleService;
    private final SysRoleFacade sysRoleFacade;

    public SysRoleController(SysRoleService sysRoleService, SysRoleFacade sysRoleFacade) {
        this.sysRoleService = sysRoleService;
        this.sysRoleFacade = sysRoleFacade;
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @JsonView({View.SimpleView.class})
    public R list() {
        return success(sysRoleService.list());
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Validated SysRolePageQueryParam param) {
        return success(sysRoleService.listSysRolesByPage(param));
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
        return success(sysRoleService.getById(id));
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysRoleForm form) {
        return success(sysRoleService.saveSysRole(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysRoleForm form) {
        return success(sysRoleService.updateSysRole(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(sysRoleFacade.deleteSysRole(ids));
    }

    /**
     * 通过用户、授权类型查询角色集合
     *
     * @param userId
     * @param authType
     * @return
     */
    @GetMapping("/queryRolesByUserIdAndAuthType")
    public R queryRolesByUserIdAndType(@NotNull String userId, @NotBlank String authType) {
        return success(sysRoleService.listSysRolesByUserIdAndAuthType(userId, authType));
    }

    /**
     * 通过用户、授权类型查询角色ID集合
     *
     * @param userId
     * @param authType
     * @return
     */
    @GetMapping("/queryIdsByUserIdAndAuthType")
    public R queryIdsByUserIdAndType(@NotNull String userId, @NotBlank String authType) {
        Set<String> ids = sysRoleService.listSysRolesByUserIdAndAuthType(userId, authType).stream()
                .map(m -> m.getId().toString()).collect(Collectors.toSet());
        return success(ids);
    }

    /**
     * 新增角色与菜单关联（1 - n）
     *
     * @param form
     * @return
     */
    @PostMapping("/saveRolePermission")
    public R saveRolePermission(@RequestBody @Validated SysRefRolePermissionForm form) {
        return success(sysRoleFacade.saveRolePermission(form));
    }

    /**
     * 新增角色与用户关联（n - 1）
     *
     * @param form
     * @return
     */
    @PostMapping("/saveRoleUser")
    public R saveRoleUser(@RequestBody @Validated SysRefRoleUserForm form) {
        return success(sysRoleFacade.saveRoleUser(form));
    }
}
