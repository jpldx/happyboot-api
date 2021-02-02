package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.AdminConstant;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.factory.SysRoleFactory;
import org.happykit.happyboot.sys.mapper.SysRoleMapper;
import org.happykit.happyboot.sys.model.entity.SysRoleDO;
import org.happykit.happyboot.sys.model.form.SysRoleForm;
import org.happykit.happyboot.sys.model.query.SysRolePageQueryParam;
import org.happykit.happyboot.sys.service.SysRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统角色 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@Validated(Default.class)
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {
    // @Override
    // public List<SysRoleDO> listSysRoles() {
    // return this.baseMapper.listSysRoles();
    // }

    @Override
    public List<SysRoleDO> listSysRolesByUserIdAndAuthType(String userId, String authType) {
        return this.baseMapper.selectSysRolesByUserIdAndAuthType(userId, authType);
    }

    @Override
    public List<String> listAuthorityNamesByUserIdAndAuthType(String userId, String authType) {
        List<String> list = new ArrayList<>();
        Set<String> authorityNames = this.listSysRolesByUserIdAndAuthType(userId, authType).stream()
                .filter(f -> StringUtils.isNotBlank(f.getAuthorityName())).map(m -> m.getAuthorityName())
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(authorityNames)) {
            list.addAll(authorityNames);
        }
        return list;
    }

    @Override
    public IPage<SysRoleDO> listSysRolesByPage(SysRolePageQueryParam param) {
        Page page = new PageUtils<SysRolePageQueryParam>().page(param, SysRoleDO.class);
        return this.baseMapper.selectSysRolesByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleDO saveSysRole(SysRoleForm form) {
        SysRoleDO entity = SysRoleFactory.INSTANCE.form2Do(form);
        entity.setId(null);
        entity.setSys(false);
        // 判断角色是否重复
        checkSysRoleUniqueness(entity);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleDO updateSysRole(SysRoleForm form) {
        SysRoleDO entity = SysRoleFactory.INSTANCE.form2Do(form);
        // 判断角色是否重复
        checkSysRoleUniqueness(entity);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRole(Long id) {
        SysRoleDO sysRole = getById(id);
        if (sysRole == null) {
            return false;
        }
        canOperate(sysRole);
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRole(String... ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 判断是否可以操作
     *
     * @param sysRole
     * @return
     */
    private void canOperate(SysRoleDO sysRole) {
        // 系统级标识，不允许操作
        if (sysRole.getSys()) {
            throw new RuntimeException("不允许操作");
        }
        if (AdminConstant.ROLE_ADMIN.equals(sysRole.getAuthorityName())) {
            throw new RuntimeException("不允许操作");
        }
    }

    /**
     * 判断角色是否唯一
     *
     * @param sysRole
     */
    private void checkSysRoleUniqueness(SysRoleDO sysRole) {
        SysRoleDO temp = this.baseMapper.selectByAuthorityName(sysRole.getAuthorityName());
        if (temp != null && !temp.getId().equals(sysRole.getId())) {
            throw new RuntimeException(String.format("当前角色代号【%s】已经存在，请重新选择一个角色代号", sysRole.getAuthorityName()));
        }
    }
}
