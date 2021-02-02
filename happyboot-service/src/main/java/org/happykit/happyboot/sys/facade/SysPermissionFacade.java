package org.happykit.happyboot.sys.facade;

/**
 * @author shaoqiang
 * @date 2020/6/23
 */

import java.util.*;
import java.util.stream.Collectors;

import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.apache.commons.collections4.CollectionUtils;
import org.happykit.happyboot.sys.service.SysPermissionService;
import org.happykit.happyboot.sys.service.SysRefRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 权限服务接口
 *
 * @author shaoqiang
 * @version 1.0 2020/6/23
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysPermissionFacade {

    private final SysPermissionService sysPermissionService;
    private final SysRefRolePermissionService sysRefRolePermissionService;

    /**
     * 删除权限
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysPermission(String... ids) {
        // 待删除的权限集合包含子集
        Set<String> permissionIds = new HashSet<>();
        List<SysPermissionDO> list = sysPermissionService.list();
        List<String> idList = Arrays.asList(ids);
        idList.stream().forEach(id -> {
            List<SysPermissionDO> permissionList = listAllChild(id, list);
            Set<String> permissionIdSet = permissionList.stream().map(m -> m.getId()).collect(Collectors.toSet());
            permissionIds.addAll(permissionIdSet);
        });
        permissionIds.stream().forEach(id -> {
            sysRefRolePermissionService.deleteByPermissionId(id);
        });

        return sysPermissionService.deleteSysPermission(ids);
    }

    /**
     * 递归查找所有子集
     *
     * @param parentId
     * @param sysPermissionList
     * @return
     */
    private List<SysPermissionDO> listAllChild(String parentId, List<SysPermissionDO> sysPermissionList) {
        List<SysPermissionDO> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysPermissionList)) {
            for (SysPermissionDO p : sysPermissionList) {
                if (parentId.equals(p.getParentId())) {
                    list.add(p);
                    list.addAll(listAllChild(p.getId(), sysPermissionList));
                }
            }
        }
        return list;
    }

}
