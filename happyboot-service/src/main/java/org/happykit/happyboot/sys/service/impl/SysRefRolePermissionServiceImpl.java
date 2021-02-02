package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysRefRolePermissionMapper;
import org.happykit.happyboot.sys.model.entity.SysRefRolePermissionDO;
import org.happykit.happyboot.sys.service.SysRefRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.List;

/**
 * 角色与权限关系 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/03/26
 */
@Validated(Default.class)
@Service
public class SysRefRolePermissionServiceImpl extends ServiceImpl<SysRefRolePermissionMapper, SysRefRolePermissionDO>
        implements SysRefRolePermissionService {

    @Override
    public List<SysRefRolePermissionDO> listSysRefRolePermissionsByPermissionId(Long permissionId) {
        return this.baseMapper.selectSysRefRolePermissionsByPermissionId(permissionId);
    }

    @Override
    public List<SysRefRolePermissionDO> listSysRefRolePermissionsByRoleId(Long roleId) {
        return this.baseMapper.selectSysRefRolePermissionsByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPermissionId(String permissionId) {
        return retBool(this.baseMapper.deleteByPermissionId(permissionId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleId(String roleId) {
        return retBool(this.baseMapper.deleteByRoleId(roleId));
    }
}
