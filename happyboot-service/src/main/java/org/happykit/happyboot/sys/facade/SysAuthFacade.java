package org.happykit.happyboot.sys.facade;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.happykit.happyboot.constant.AdminConstant;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.model.dto.TreeNodeDTO;
import org.happykit.happyboot.sys.enums.AuthTypeEnum;
import org.happykit.happyboot.sys.factory.SysPermissionFactory;
import org.happykit.happyboot.sys.model.dto.SysPermissionTreeDTO;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.entity.SysDeptRegionDO;
import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.model.form.SysUserDeptForm;
import org.happykit.happyboot.sys.service.*;
import org.happykit.happyboot.sys.util.SysLeadDeptUtils;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限
 *
 * @author shaoqiang
 * @version 1.0 2020/3/27
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysAuthFacade {

    private final SysRefRolePermissionService sysRefRolePermissionService;
    private final SysPermissionService sysPermissionService;
    private final SysRefRoleUserService sysRefRoleUserService;
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysRefUserDeptRegionService sysRefUserDeptRegionService;
    private final SysDeptObjService sysDeptObjService;
    private final SysDeptRegionService sysDeptRegionService;
    private final SysSubjectService sysSubjectService;
    private final SysSecurityUtils sysSecurityUtils;
    private final SysLeadDeptUtils sysLeadDeptUtils;

    /**
     * 查询用户登录选择内部部门树
     *
     * @return
     */
    public List<TreeNodeDTO> listUserDeptTree() {

        List<TreeNodeDTO> tree = new ArrayList<>();
        // 查询用户关联的对象列表
        List<SysSubjectDO> sysObjList = sysSubjectService.listSysObjsByUserId(sysSecurityUtils.getCurrentUserId());

        sysObjList.forEach(sysObj -> {
            TreeNodeDTO node = new TreeNodeDTO();
            node.setParentId(SysConstant.ROOT_PARENT_ID_STR);
            node.setId(sysObj.getId());
            node.setTitle(sysObj.getSubjectName());
            List<TreeNodeDTO> children = new ArrayList<>();
            List<SysDeptObjDO> sysDeptObjList =
                    sysDeptObjService.listSysDeptObjsByObjIdAndUserId(sysObj.getId(), sysSecurityUtils.getCurrentUserId());
            sysDeptObjList.forEach(sysDeptObj -> {
                TreeNodeDTO c = new TreeNodeDTO();
                c.setId(sysDeptObj.getId());
                c.setParentId(sysDeptObj.getSubjectId());
                String deptName = sysDeptObj.getDeptName();
                // 添加综治中心部门标识
                c.setTitle(sysLeadDeptUtils.isLeadDept(sysDeptObj.getIsLeadDept()) ? deptName + "（综治中心）" : deptName);
                c.setChildren(new ArrayList<>());
                children.add(c);
            });
            node.setChildren(children);
            tree.add(node);
        });

        return tree;
    }

    /**
     * 获取当前登录用户所属的部门对象
     *
     * @return
     */
    public SysDeptObjDO getCurrentDept() {
        return sysDeptObjService.getById(sysSecurityUtils.getCurrentObjDeptId());
    }

    /**
     * 更新当前登录用户所属的部门ID
     *
     * @param form
     * @return
     */
    public SysDeptObjDO updateCurrentDept(SysUserDeptForm form) {
        if (SysConstant.ROOT_PARENT_ID_STR.equals(form.getDeptId())) {
            sysSecurityUtils.clearCurrentObjDeptId();
            return null;
        }
        List<SysDeptObjDO> sysDeptObjList =
                sysDeptObjService.listSysDeptObjsByUserId(sysSecurityUtils.getCurrentUserId());
        SysDeptObjDO sysDeptObj =
                sysDeptObjList.stream().filter(f -> f.getId().equals(form.getDeptId())).findFirst().get();
        sysSecurityUtils.setCurrentObjDeptId(sysDeptObj.getId());
        return sysDeptObj;
    }

    /**
     * 查询adminAPI接口
     *
     * @return
     */
    public List<String> listAdminApis() {
        List<String> list = new ArrayList<>();
        Set<String> apis = sysPermissionService.listSysPermissionsByType(SysConstant.PERMISSION_KEY).stream()
                .filter(f -> StringUtils.isNotBlank(f.getPath())).map(m -> m.getPath()).collect(Collectors.toSet());
        list.addAll(apis);
        return list;
    }

    /**
     * 查询admin角色代号集合
     *
     * @return
     */
    public List<String> listAdminRoles() {
        List<String> list = new ArrayList<>();
        Set<String> roles = sysRoleService.list().stream().filter(f -> StringUtils.isNotBlank(f.getAuthorityName()))
                .map(m -> m.getAuthorityName()).collect(Collectors.toSet());
        list.addAll(roles);
        return list;
    }

    /**
     * 查询用户可见的API接口
     *
     * @param userId
     * @return
     */
    public List<String> listVisibleApisByUserId(String userId) {
        List<String> list = new ArrayList<>();
        Set<String> apis = sysPermissionService
                .listSysPermissionsByUserIdAndAuthTypeAndModule(userId, AuthTypeEnum.VISIBLE.getCode(),
                        AuthTypeEnum.VISIBLE.getCode(), null, null, null, new String[]{SysConstant.PERMISSION_KEY})
                .stream().filter(f -> StringUtils.isNotBlank(f.getPath())).map(m -> m.getPath())
                .collect(Collectors.toSet());
        list.addAll(apis);
        return list;
    }

    /**
     * 获取用户部门树
     *
     * @return
     */
    public List<TreeNodeDTO> deptTree() {
        TreeNodeDTO node = new TreeNodeDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildDeptChildTree(node);
    }

    /**
     * 获取用户部门树
     *
     * @return
     */
    public List<TreeNodeDTO> deptTree(Long parentId) {
        List<TreeNodeDTO> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(sysSecurityUtils.getCurrentObjIdList())) {
            return list;
        }

        LambdaQueryWrapper<SysDeptObjDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptObjDO::getParentId, parentId);
        queryWrapper.in(SysDeptObjDO::getSubjectId, sysSecurityUtils.getCurrentObjIdList());
        queryWrapper.orderByAsc(SysDeptObjDO::getOrderId);
        List<SysDeptObjDO> sysDeptObjs = sysDeptObjService.list(queryWrapper);

        sysDeptObjs.stream().forEach(deptObj -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(deptObj.getId());
            dto.setParentId(deptObj.getParentId());
            dto.setTitle(deptObj.getDeptName());

            LambdaQueryWrapper<SysDeptObjDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(SysDeptObjDO::getParentId, deptObj.getId());
            List<SysDeptObjDO> children = sysDeptObjService.list(queryWrapper2);
            if (CollectionUtils.isEmpty(children)) {
                dto.setLeaf(true);
            } else {
                dto.setLeaf(false);
            }
            if (sysSecurityUtils.getCurrentObjDeptIdList().contains(deptObj.getId())) {
                dto.setDisabled(false);
            } else {
                dto.setDisabled(true);
            }

            list.add(dto);
        });
        return list;
    }

    /**
     * 获取用户区域树
     *
     * @return
     */
    public List<TreeNodeDTO> regionTree() {
        TreeNodeDTO node = new TreeNodeDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildRegionChildTree(node);
    }

    /**
     * 获取用户区域树
     *
     * @return
     */
    public List<TreeNodeDTO> regionTree(Long parentId) {
        List<TreeNodeDTO> list = new ArrayList<>();

        LambdaQueryWrapper<SysDeptRegionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptRegionDO::getParentId, parentId);
        queryWrapper.orderByAsc(SysDeptRegionDO::getOrderId);
        List<SysDeptRegionDO> sysDeptRegions = sysDeptRegionService.list(queryWrapper);

        sysDeptRegions.stream().forEach(region -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(region.getId());
            dto.setParentId(region.getParentId());
            dto.setTitle(region.getRegionName());

            LambdaQueryWrapper<SysDeptRegionDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(SysDeptRegionDO::getParentId, parentId);
            List<SysDeptRegionDO> children = sysDeptRegionService.list(queryWrapper2);
            if (CollectionUtils.isEmpty(children)) {
                dto.setLeaf(true);
            } else {
                dto.setLeaf(false);
            }
            if (sysSecurityUtils.getCurrentDeptRegionIdList().contains(region.getId())) {
                dto.setDisabled(false);
            } else {
                dto.setDisabled(true);
            }
            list.add(dto);
        });
        return list;
    }

    /**
     * 通过模块和授权类型获取用户权限树
     *
     * @param module
     * @param authType
     * @param neTypes
     * @param types
     * @return
     */
    public List<SysPermissionTreeDTO> listPermissionTreeByModuleAndAuthType(String module, String authType,
                                                                            String[] neTypes, String[] types) {
        if (checkAdmin()) {
            return sysPermissionService.tree(module, neTypes, types);
        }
        SysPermissionTreeDTO node = new SysPermissionTreeDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildPermissionChildTree(node, sysSecurityUtils.getCurrentUserId(), module, authType, neTypes, types);
    }

    private boolean checkAdmin() {
        if (sysSecurityUtils.getCurrentUserDetails() == null) {
            return false;
        }
        if (checkAdminByUsername(sysSecurityUtils.getCurrentUserDetails().getUsername())) {
            return true;
        }
        return false;
    }

    public boolean checkAdminByUsername(String username) {
        if (AdminConstant.ADMIN_ACCOUNT.equals(username)) {
            return true;
        }
        return false;
    }

    /**
     * 通过父级节点递归获取子节点，构建部门树
     *
     * @param node
     * @return
     */
    private List<TreeNodeDTO> buildDeptChildTree(TreeNodeDTO node) {
        List<TreeNodeDTO> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(sysSecurityUtils.getCurrentObjIdList())) {
            return list;
        }

        LambdaQueryWrapper<SysDeptObjDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptObjDO::getParentId, node.getId());
        queryWrapper.in(SysDeptObjDO::getSubjectId, sysSecurityUtils.getCurrentObjIdList());
        queryWrapper.orderByAsc(SysDeptObjDO::getOrderId);
        List<SysDeptObjDO> sysDeptObjs = sysDeptObjService.list(queryWrapper);

        sysDeptObjs.stream().forEach(deptObj -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(deptObj.getId());
            dto.setParentId(deptObj.getParentId());
            dto.setTitle(deptObj.getDeptName());

            List<TreeNodeDTO> children = buildDeptChildTree(dto);
            dto.setChildren(children);
            if (CollectionUtils.isEmpty(children)) {
                dto.setLeaf(true);
            } else {
                dto.setLeaf(false);
            }
            if (sysSecurityUtils.getCurrentObjDeptIdList().contains(deptObj.getId())) {
                dto.setDisabled(false);
            } else {
                dto.setDisabled(true);
            }

            list.add(dto);
        });
        return list;
    }

    /**
     * 通过父级节点递归获取子节点，构建区域树
     *
     * @param node
     * @return
     */
    private List<TreeNodeDTO> buildRegionChildTree(TreeNodeDTO node) {
        List<TreeNodeDTO> list = new ArrayList<>();

        LambdaQueryWrapper<SysDeptRegionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptRegionDO::getParentId, node.getId());
        queryWrapper.orderByAsc(SysDeptRegionDO::getOrderId);
        List<SysDeptRegionDO> sysDeptRegions = sysDeptRegionService.list(queryWrapper);

        sysDeptRegions.stream().forEach(region -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(region.getId());
            dto.setParentId(region.getParentId());
            dto.setTitle(region.getRegionName());

            List<TreeNodeDTO> children = buildRegionChildTree(dto);
            dto.setChildren(children);
            if (CollectionUtils.isEmpty(children)) {
                dto.setLeaf(true);
            } else {
                dto.setLeaf(false);
            }
            if (sysSecurityUtils.getCurrentDeptRegionIdList().contains(region.getId())) {
                dto.setDisabled(false);
            } else {
                dto.setDisabled(true);
            }
            list.add(dto);
        });
        return list;
    }

    /**
     * 通过父级节点递归获取子节点，构建权限树
     *
     * @param node
     * @param userId
     * @param module
     * @return
     */
    private List<SysPermissionTreeDTO> buildPermissionChildTree(SysPermissionTreeDTO node, String userId, String module,
                                                                String authType, String[] neTypes, String[] types) {
        List<SysPermissionTreeDTO> list = new ArrayList<>();

        List<SysPermissionDO> sysPermissions = sysPermissionService.listSysPermissionsByUserIdAndAuthTypeAndModule(
                userId, authType, authType, node.getId(), module, neTypes, types);
        sysPermissions.stream().forEach(sysPermission -> {
            SysPermissionTreeDTO dto = SysPermissionFactory.INSTANCE.do2Node(sysPermission);
            List<SysPermissionTreeDTO> children =
                    buildPermissionChildTree(dto, userId, module, authType, neTypes, types);
            dto.setChildren(children);
            list.add(dto);
        });
        return list;
    }

}
