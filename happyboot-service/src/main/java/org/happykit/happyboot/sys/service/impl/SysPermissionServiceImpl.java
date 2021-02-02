package org.happykit.happyboot.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.groups.Default;

import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.model.model.ModifyNodeModel;
import org.happykit.happyboot.sys.enums.ModuleEnum;
import org.happykit.happyboot.sys.factory.SysPermissionFactory;
import org.happykit.happyboot.sys.mapper.SysPermissionMapper;
import org.happykit.happyboot.sys.model.dto.SysPermissionTreeDTO;
import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.happykit.happyboot.sys.model.form.SysMenuForm;
import org.happykit.happyboot.sys.service.SysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 权限服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Validated(Default.class)
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionDO>
        implements SysPermissionService {

    @Override
    public List<SysPermissionTreeDTO> tree(String module, String[] neTypes, String[] types) {
        SysPermissionTreeDTO node = new SysPermissionTreeDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildChildTree(node, module, neTypes, types);
    }

    @Override
    public List<SysPermissionDO> listSysPermissionsByType(String type) {
        return this.baseMapper.selectSysPermissions(null, null, type, null, null);
    }

    @Override
    public List<SysPermissionDO> listSysPermissionsByParentIdAndType(String parentId, String type) {
        return this.baseMapper.selectSysPermissions(parentId, null, type, null, null);
    }

    @Override
    public List<SysPermissionDO> listSysPermissionsByRoleIdAndAuthTypeAndModule(String roleId, String authType,
                                                                                String module, String[] neTypes, String[] types) {
        return this.baseMapper.selectSysPermissionsByRoleIdAndAuthType(roleId, authType, null, module, neTypes, types);
    }

    @Override
    public List<SysPermissionDO> listSysPermissionsByUserIdAndAuthTypeAndModule(String userId, String roleUserType,
                                                                                String roleMenuType, String parentId, String module, String[] neTypes, String[] types) {
        return this.baseMapper.selectSysPermissionsByUserIdAndAuthType(userId, roleUserType, roleMenuType, parentId,
                module, neTypes, types);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPermissionDO saveSysMenu(SysMenuForm form) {
        SysPermissionDO entity = SysPermissionFactory.INSTANCE.menu2Do(form);
        entity.setId(null);
        entity.setModule(ModuleEnum.ADMIN.getCode());
        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPermissionDO updateSysMenu(SysMenuForm form) {
        SysPermissionDO entity = SysPermissionFactory.INSTANCE.menu2Do(form);
        entity.setModule(ModuleEnum.ADMIN.getCode());
        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysPermission(String id) {
        SysPermissionDO sysPermission = getById(id);
        if (sysPermission == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysPermission(String... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    public boolean updateNode(ModifyNodeModel form) {
        SysPermissionDO sysPermission = this.getById(form.getId());
        if (sysPermission == null) {
            throw new RuntimeException("未找到节点");
        }
        // 根节点不需要查询对象
        if (!SysConstant.ROOT_PARENT_ID.equals(form.getParentId())) {
            SysPermissionDO parentSysPermission = this.getById(form.getParentId());
            if (parentSysPermission == null) {
                throw new RuntimeException("未找到节点");
            } else {
                if (!sysPermission.getModule().equals(parentSysPermission.getModule())) {
                    throw new RuntimeException("不允许跨模块移动");
                }
            }
        }
        if (sysPermission.getParentId().equals(form.getParentId())) {
            return true;
        }

        SysPermissionDO entity = new SysPermissionDO();
        entity.setId(form.getId());
        entity.setParentId(form.getParentId());
        return updateById(entity);
    }

    /**
     * 递归构建树
     *
     * @param node
     * @param module
     * @param neTypes
     * @param types
     * @return
     */
    private List<SysPermissionTreeDTO> buildChildTree(SysPermissionTreeDTO node, String module, String[] neTypes,
                                                      String[] types) {
        List<SysPermissionTreeDTO> list = new ArrayList<>();
        List<SysPermissionDO> sysPermissionList =
                this.baseMapper.selectSysPermissions(node.getId(), module, null, neTypes, types);
        System.out.println(sysPermissionList.size());
        sysPermissionList.stream().forEach(sysPermission -> {
            SysPermissionTreeDTO dto = SysPermissionFactory.INSTANCE.do2Node(sysPermission);
            List<SysPermissionTreeDTO> children = buildChildTree(dto, module, neTypes, types);
            dto.setChildren(children);
            list.add(dto);
        });
        return list;
    }

}
