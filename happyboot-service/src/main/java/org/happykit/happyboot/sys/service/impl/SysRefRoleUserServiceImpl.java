package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.factory.SysRefRoleUserFactory;
import org.happykit.happyboot.sys.mapper.SysRefRoleUserMapper;
import org.happykit.happyboot.sys.model.entity.SysRefRoleUserDO;
import org.happykit.happyboot.sys.model.form.SysRefRoleUserForm;
import org.happykit.happyboot.sys.service.SysRefRoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 角色与账号关系表 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated(Default.class)
@Service
public class SysRefRoleUserServiceImpl extends ServiceImpl<SysRefRoleUserMapper, SysRefRoleUserDO>
        implements SysRefRoleUserService {

    @Override
    public List<SysRefRoleUserDO> listSysRefRoleUsersByUserId(Long userId) {
        return this.baseMapper.selectSysRefRoleUsersByUserId(userId);
    }

    @Override
    public List<SysRefRoleUserDO> listSysRefRoleUsersByRoleId(Long roleId) {
        return this.baseMapper.selectSysRefRoleUsersByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRefRoleUserDO saveSysRefRoleUser(SysRefRoleUserForm form) {
        SysRefRoleUserDO entity = SysRefRoleUserFactory.INSTANCE.form2Do(form);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRefRoleUserDO updateSysRefRoleUser(SysRefRoleUserForm form) {
        SysRefRoleUserDO entity = SysRefRoleUserFactory.INSTANCE.form2Do(form);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRefRoleUser(Long id) {
        SysRefRoleUserDO sysRefRoleUser = getById(id);
        if (sysRefRoleUser == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRefRoleUser(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByUserId(String userId) {
        return retBool(this.baseMapper.deleteByUserId(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleId(String roleId) {
        return retBool(this.baseMapper.deleteByRoleId(roleId));
    }

}