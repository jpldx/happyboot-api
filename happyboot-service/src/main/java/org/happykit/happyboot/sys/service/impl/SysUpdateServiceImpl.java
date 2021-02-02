package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.factory.SysUpdateFactory;
import org.happykit.happyboot.sys.mapper.SysUpdateMapper;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.entity.SysUpdateDO;
import org.happykit.happyboot.sys.model.form.SysUpdateForm;
import org.happykit.happyboot.sys.model.query.SysUpdatePageQueryParam;
import org.happykit.happyboot.sys.service.SysUpdateService;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 服务实现类
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
@Validated(Default.class)
@Service
public class SysUpdateServiceImpl extends ServiceImpl<SysUpdateMapper, SysUpdateDO> implements SysUpdateService {
    @Autowired
    private SysSecurityUtils sysSecurityUtils;

    @Override
    public IPage<SysUpdateDO> listSysUpdatesByPage(SysUpdatePageQueryParam param) {
        Page page = new PageUtils<SysUpdatePageQueryParam>().page(param, SysUpdateDO.class);
        return this.baseMapper.selectSysUpdatesByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUpdateDO saveSysUpdate(SysUpdateForm form) {
        SysUpdateDO entity = SysUpdateFactory.INSTANCE.form2Do(form);
        entity.setUpdateId(IdGenerator.generateIdStr());
        SysDeptObjDO dept = sysSecurityUtils.getCurrentUserDept();
        if (null == dept) {
            throw new RuntimeException(SysExceptionConstant.NOT_FOUND_DEPT);
        }
        entity.setSubjectId(dept.getSubjectId());
        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUpdateDO updateSysUpdate(SysUpdateForm form) {
        SysUpdateDO entity = SysUpdateFactory.INSTANCE.form2Do(form);
        entity.setUpdateId(IdGenerator.generateIdStr());
        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysUpdate(String id) {
        SysUpdateDO sysUpdate = getById(id);
        if (sysUpdate == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysUpdate(String... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }
}