package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.factory.SysSubjectFactory;
import org.happykit.happyboot.sys.mapper.SysSubjectMapper;
import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.model.form.SysSubjectForm;
import org.happykit.happyboot.sys.model.query.SysSubjectPageQueryParam;
import org.happykit.happyboot.sys.service.SysSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 单位 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/03/25
 */
@Validated(Default.class)
@Service
public class SysSubjectServiceImpl extends ServiceImpl<SysSubjectMapper, SysSubjectDO> implements SysSubjectService {

    @Override
    public List<SysSubjectDO> listSysObjsByUserId(String userId) {
        return this.baseMapper.selectSysObjsByUserId(userId);
    }

    @Override
    public IPage<SysSubjectDO> listSysObjsByPage(SysSubjectPageQueryParam param) {
        Page page = new PageUtils<SysSubjectPageQueryParam>().page(param, SysSubjectDO.class);
        return this.baseMapper.selectSysObjsByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysSubjectDO saveSysObj(SysSubjectForm form) {
        SysSubjectDO entity = SysSubjectFactory.INSTANCE.form2Do(form);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysSubjectDO updateSysObj(SysSubjectForm form) {
        SysSubjectDO entity = SysSubjectFactory.INSTANCE.form2Do(form);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysObj(Long id) {
        SysSubjectDO sysObj = getById(id);
        if (sysObj == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysObj(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getNameById(String id) {
        return this.baseMapper.getNameById(id);
    }
}