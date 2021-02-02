package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.enums.AuthTypeEnum;
import org.happykit.happyboot.sys.facade.SysAuthFacade;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.form.SysUserDeptForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户权限
 *
 * @author shaoqiang
 * @version 1.0 2020/3/6
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/auth")
public class SysAuthController extends BaseController {

    private final SysAuthFacade sysAuthFacade;

    public SysAuthController(SysAuthFacade sysAuthFacade) {
        this.sysAuthFacade = sysAuthFacade;
    }

    /**
     * 查询登录用户拥有的内部部门树和当前部门
     *
     * @return
     */
    @GetMapping("/queryObjDeptTree")
    public R queryObjDeptTree() {
        Map<String, Object> map = new HashMap<>(2);
//        map.put("deptTree", sysAuthFacade.listUserDeptTree());
        map.put("deptTree", sysAuthFacade.deptTree());
        map.put("currentDept", sysAuthFacade.getCurrentDept());
        return success(map);
    }

    /**
     * 查询 - 根据用户切换的当前部门查询对应的部门树
     *
     * @return
     */
    @GetMapping("/queryCurrentDeptTree")
    public R queryCurrentDeptTree() {
        SysDeptObjDO currentDept = sysAuthFacade.getCurrentDept();
        // 未切换部门（外部视角）- 所有部门树
        if (null == currentDept) {
            return success(sysAuthFacade.listUserDeptTree());
        }
        // 已切换部门 - 当前部门
        return success(currentDept);
    }

    /**
     * 更新登录用户所属部门
     *
     * @param form
     * @return
     */
    @PostMapping("/updateUserDept")
    public R updateUserDept(@RequestBody @Validated SysUserDeptForm form) {
        return success(sysAuthFacade.updateCurrentDept(form));
    }

    /**
     * 查询用户可见的菜单树
     *
     * @return
     */
    @GetMapping("/permission/tree")
    public R queryPermissionTree(@NotBlank String module, String[] neTypes, String[] types) {
        return success(sysAuthFacade.listPermissionTreeByModuleAndAuthType(module, AuthTypeEnum.VISIBLE.getCode(),
                neTypes, types));
    }

    /**
     * 查询 - 一次性加载用户可操作的部门树
     *
     * @return
     */
    @GetMapping("/dept/tree")
    public R queryDeptTree() {
        return success(sysAuthFacade.deptTree());
    }

    /**
     * 查询 - 一次性加载用户可操作的区域树
     *
     * @return
     */
    @GetMapping("/region/tree")
    public R queryRegionTree() {
        return success(sysAuthFacade.regionTree());
    }

    /**
     * 查询 - 异步加载用户可操作的部门
     *
     * @return
     */
    @GetMapping("/dept/asyncTree")
    public R queryDeptAsyncTree(@NotNull Long parentId) {
        return success(sysAuthFacade.deptTree(parentId));
    }

    /**
     * 查询 - 异步加载用户可操作的区域
     *
     * @return
     */
    @GetMapping("/region/asyncTree")
    public R queryRegionAsyncTree(@NotNull Long parentId) {
        return success(sysAuthFacade.regionTree(parentId));
    }
}
