package org.happykit.happyboot.sys.facade;

import com.alibaba.excel.EasyExcel;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.sys.model.entity.*;
import org.happykit.happyboot.sys.model.excel.SysUserData;
import org.happykit.happyboot.sys.model.excel.listener.SysUserDataListener;
import org.happykit.happyboot.util.Assert;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.happykit.happyboot.sys.model.form.*;
import org.happykit.happyboot.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserFacade {
    private final SysDeptObjService sysDeptObjService;
    private final SysDeptRegionService sysDeptRegionService;
    private final SysUserService sysUserService;
    private final SysRefUserDeptRegionService sysRefUserDeptRegionService;
    private final SysRoleService sysRoleService;
    private final SysFileService sysFileService;
    private final SysRefRoleUserService sysRefRoleUserService;
    private final SysFacilityUserGroupRelService sysFacilityUserGroupRelService;

    /**
     * 新增用户
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SysUserDO saveSysUser(SysUserForm form) {
        SysUserDO sysUser = sysUserService.saveSysUser(form);
        // 部门用户关联
//        sysRefUserDeptObjService.deleteByUserId(sysUser.getId());
//        if (CollectionUtils.isNotEmpty(form.getDeptIds())) {
//            form.getDeptIds().stream().forEach(deptId -> {
//                SysDeptObjDO sysDeptObj = sysDeptObjService.getById(deptId);
//                if (sysDeptObj != null) {
//                    SysRefUserDeptObjDO refEntity = new SysRefUserDeptObjDO();
//                    refEntity.setUserId(sysUser.getId());
//                    refEntity.setDeptId(deptId);
//                    refEntity.setObjId(sysDeptObj.getSubjectId());
//                    refEntity.setDeptCode(sysDeptObj.getDeptCode());
//                    sysRefUserDeptObjService.save(refEntity);
//                }
//            });
//        }
        // 区域用户关联
        sysRefUserDeptRegionService.deleteByUserId(sysUser.getId());
        if (CollectionUtils.isNotEmpty(form.getRegionIds())) {
            form.getRegionIds().stream().forEach(regionId -> {
                SysDeptRegionDO sysDeptRegion = sysDeptRegionService.getById(regionId);
                if (sysDeptRegion != null) {
                    SysRefUserDeptRegionDO refEntity = new SysRefUserDeptRegionDO();
                    refEntity.setUserId(sysUser.getId());
                    refEntity.setRegionId(regionId);
                    refEntity.setRegionCode(sysDeptRegion.getRegionCode());
                    sysRefUserDeptRegionService.save(refEntity);
                }
            });
        }

        return sysUser;
    }

    /**
     * 更新用户
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SysUserDO updateSysUser(SysUserForm form) {
        return sysUserService.updateSysUser(form);

        // 部门用户关联
//        sysRefUserDeptObjService.deleteByUserId(sysUser.getId());
//        if (CollectionUtils.isNotEmpty(form.getDeptIds())) {
//            form.getDeptIds().stream().forEach(deptId -> {
//                SysDeptObjDO sysDeptObj = sysDeptObjService.getById(deptId);
//                if (sysDeptObj != null) {
//                    SysRefUserDeptObjDO refEntity = new SysRefUserDeptObjDO();
//                    refEntity.setUserId(sysUser.getId());
//                    refEntity.setDeptId(deptId);
//                    refEntity.setObjId(sysDeptObj.getObjId());
//                    refEntity.setDeptCode(sysDeptObj.getDeptCode());
//                    sysRefUserDeptObjService.save(refEntity);
//                }
//            });
//        }
        // 区域用户关联
//        sysRefUserDeptRegionService.deleteByUserId(sysUser.getId());
//        if (CollectionUtils.isNotEmpty(form.getRegionIds())) {
//            form.getRegionIds().stream().forEach(regionId -> {
//                SysDeptRegionDO sysDeptRegion = sysDeptRegionService.getById(regionId);
//                if (sysDeptRegion != null) {
//                    SysRefUserDeptRegionDO refEntity = new SysRefUserDeptRegionDO();
//                    refEntity.setUserId(sysUser.getId());
//                    refEntity.setRegionId(regionId);
//                    refEntity.setRegionCode(sysDeptRegion.getRegionCode());
//                    sysRefUserDeptRegionService.save(refEntity);
//                }
//            });
//        }
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysUser(String... ids) {
//        List<String> idList = Arrays.asList(ids);
        // 删除用户区域关联
//        idList.stream().forEach(id -> {
////            sysRefUserDeptObjService.deleteByUserId(id);
//            sysRefUserDeptRegionService.deleteByUserId(id);
//        });
        return sysUserService.deleteSysUser(ids);
    }

    /**
     * 导入数据
     *
     * @param form
     * @return
     */
    public boolean importData(SysUserImportForm form) {
        SysFileDO sysFile = sysFileService.getById(form.getFileId());
        if (sysFile == null) {
            return false;
        }
        EasyExcel.read(sysFile.getFilePath(), SysUserData.class, new SysUserDataListener(sysUserService)).sheet()
                .doRead();
        return true;
    }

    // /**
    // * 查询角色列表
    // * @return
    // */
    // public List<SysRoleDO> queryRoleList(){
    // return sysRoleService.listSysRoles();
    // }
    // /**
    // * 查询用户 - 角色列表
    // * @return
    // */
    // public List<SysRoleDO> queryUserRoleList(Long userId){
    // return sysRoleService.listSysRolesByUserId(userId);
    // }

    /**
     * 保存用户角色关联，用户1-角色n
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserRole(SysRefUserRoleForm form) {
        String userId = form.getUserId();
        List<String> roleIds = form.getRoleIds();
        SysUserDO sysUser = sysUserService.getById(userId);
        if (sysUser == null) {
            return false;
        }
        // 先删除关联
        sysRefRoleUserService.deleteByUserId(userId);

        if (CollectionUtils.isEmpty(roleIds)) {
            return true;
        }

        Set<String> roleIdSet = new HashSet<>(roleIds);
        List<SysRefRoleUserDO> entityList = roleIdSet.stream().map(roleId -> {
            SysRoleDO sysRole = sysRoleService.getById(roleId);
            if (sysRole == null) {
                return null;
            }
            SysRefRoleUserDO entity = new SysRefRoleUserDO();
            entity.setRoleId(roleId);
            entity.setUserId(userId);
            entity.setAuthType(form.getAuthType());
            return entity;
        }).filter(item -> item != null).collect(Collectors.toList());
        return sysRefRoleUserService.saveBatch(entityList);
    }

    /**
     * 保存用户区域关联，用户1-区域n
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserDeptRegion(SysRefUserDeptRegionForm form) {
        String userId = form.getUserId();
        List<Long> regionIds = form.getRegionIds();
        SysUserDO sysUser = sysUserService.getById(userId);
        if (sysUser == null) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_USER);
        }
        // 先删除关联
        sysRefUserDeptRegionService.deleteByUserId(userId);

        if (CollectionUtils.isEmpty(regionIds)) {
            return true;
        }

        Set<Long> regionIdSet = new HashSet<>(regionIds);
        List<SysRefUserDeptRegionDO> entityList = regionIdSet.stream().map(regionId -> {
            SysDeptRegionDO sysDeptRegion = sysDeptRegionService.getById(regionId);
            if (sysDeptRegion == null) {
                return null;
            }
            SysRefUserDeptRegionDO entity = new SysRefUserDeptRegionDO();
            entity.setUserId(userId);
            entity.setRegionId(regionId);
            entity.setRegionCode(sysDeptRegion.getRegionCode());
            return entity;
        }).filter(item -> item != null).collect(Collectors.toList());
        return sysRefUserDeptRegionService.saveBatch(entityList);
    }

    /**
     * 保存用户-部门关联
     *
     * @param form
     * @return
     */
    public boolean saveUserDeptObj(SysRefUserDeptObjForm form) {
        String userId = form.getUserId();
        String deptId = form.getDeptId();
        SysUserDO sysUser = sysUserService.getById(userId);
        Assert.isNotFoundUser(sysUser);

        SysDeptObjDO sysDept = sysDeptObjService.getById(deptId);
        Assert.isNotFoundDept(sysDept);

        sysUser.setSubjectId(sysDept.getSubjectId());
        sysUser.setDeptId(sysDept.getId());
        return sysUserService.updateById(sysUser);
    }

    /**
     * 保存用户-功能组关联
     *
     * @param form
     * @return
     */
    @Transactional
    public boolean saveUserFacilityGroupRel(SysFacilityUserGroupRelForm form) {
        String userId = form.getUserId();
        Set<String> facilityGroupIds = form.getFacilityGroupIds();
        SysUserDO sysUser = sysUserService.getById(userId);
        Assert.isNotFoundUser(sysUser);
        // 删除关联
        sysFacilityUserGroupRelService.deleteByUserId(userId);

        if (CollectionUtils.isEmpty(facilityGroupIds)) {
            return true;
        }

        List<SysFacilityUserGroupRelDO> entities = facilityGroupIds.stream().map(groupId -> {
            SysFacilityUserGroupRelDO entity = new SysFacilityUserGroupRelDO();
            entity.setUserId(userId);
            entity.setFacilityGroupId(groupId);
            return entity;
        }).collect(Collectors.toList());
        return sysFacilityUserGroupRelService.saveBatch(entities);
    }
}
