package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.model.model.ModifyNodeModel;
import org.happykit.happyboot.sys.facade.SysPermissionFacade;
import org.happykit.happyboot.sys.model.form.SysMenuForm;
import org.happykit.happyboot.sys.service.SysPermissionService;
import org.happykit.happyboot.validation.Add;
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
 * 菜单控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController extends BaseController {

    private final SysPermissionService sysPermissionService;
    private final SysPermissionFacade sysPermissionFacade;

    public SysPermissionController(SysPermissionService sysPermissionService, SysPermissionFacade sysPermissionFacade) {
        this.sysPermissionService = sysPermissionService;
        this.sysPermissionFacade = sysPermissionFacade;
    }

    /**
     * 查询权限树
     *
     * @param module
     * @param neTypes
     * @param types
     * @return
     */
    @Log("权限-查询权限树")
    @GetMapping("/tree")
    public R tree(@NotBlank String module, String[] neTypes, String[] types) {
        return success(sysPermissionService.tree(module, neTypes, types));
    }

    /**
     * 明细
     *
     * @param id
     * @return
     */
    @Log("权限-查询")
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotNull Long id) {
        return success(sysPermissionService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param form
     * @return
     */
    @Log("权限-新增")
    @PostMapping("/menu/add")
    @JsonView({View.SimpleView.class})
    public R addMenu(@RequestBody @Validated(Add.class) SysMenuForm form) {
        return success(sysPermissionService.saveSysMenu(form));
    }

    /**
     * 更新菜单
     *
     * @param form
     * @return
     */
    @Log("权限-更新")
    @PostMapping("/menu/update")
    @JsonView({View.SimpleView.class})
    public R updateMenu(@RequestBody @Validated(Update.class) SysMenuForm form) {
        return success(sysPermissionService.updateSysMenu(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log("权限-删除")
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(sysPermissionFacade.deleteSysPermission(ids));
    }

    /**
     * 变更节点
     *
     * @param form
     * @return
     */
    @Log("权限-变更节点")
    @PostMapping("/modifyNode")
    public R modifyNode(@RequestBody @Validated ModifyNodeModel form) {
        return success(sysPermissionService.updateNode(form));
    }

    /**
     * 通过角色、授权类型、模块查询权限ID集合
     *
     * @param roleId
     * @param authType
     * @param module
     * @param neTypes
     * @param types
     * @return
     */
    @Log("权限-查询角色权限列表")
    @GetMapping("/queryIdsByRoleIdAndTypeAndModule")
    public R queryIdsByRoleIdAndTypeAndModule(@NotNull String roleId, @NotBlank String authType, @NotBlank String module,
                                              String[] neTypes, String[] types) {
        Set<String> ids = sysPermissionService
                .listSysPermissionsByRoleIdAndAuthTypeAndModule(roleId, authType, module, neTypes, types).stream()
                .map(m -> m.getId().toString()).collect(Collectors.toSet());
        return success(ids);
    }
}
