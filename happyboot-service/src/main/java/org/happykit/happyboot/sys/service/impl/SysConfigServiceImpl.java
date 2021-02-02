package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.sys.factory.SysConfigFactory;
import org.happykit.happyboot.sys.mapper.SysConfigMapper;
import org.happykit.happyboot.sys.model.entity.SysConfigDO;
import org.happykit.happyboot.sys.model.form.SysConfigForm;
import org.happykit.happyboot.sys.model.query.SysConfigPageQueryParam;
import org.happykit.happyboot.sys.service.SysConfigService;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.happykit.happyboot.util.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 系统配置
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
@Validated(Default.class)
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigDO> implements SysConfigService {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;


    @Override
    public IPage<SysConfigDO> page(SysConfigPageQueryParam query) {
        Page page = new Page(query.getPageNo(), query.getPageSize());
        return this.baseMapper.page(page, query);
    }

    @Override
    public SysConfigDO get(String id) {
        SysConfigDO dbRecord = this.baseMapper.get(id);
        Assert.isNotFound(dbRecord);
        return dbRecord;
    }

    @Override
    public boolean add(SysConfigForm form) {
        SysConfigDO entity = new SysConfigDO();
        BeanUtils.copyProperties(form, entity);
        // 校验重复性
        checkSysConfigUniqueness(entity);
        entity.setId(IdGenerator.generateIdStr());
        entity.setCreateBy(sysSecurityUtils.getCurrentUserId());
        entity.setCreateTime(LocalDateTime.now());
        return retBool(this.baseMapper.add(entity));
    }

    @Override
    public boolean update(SysConfigForm form) {
        SysConfigDO entity = this.get(form.getId());
        Assert.isNotFound(entity);
        BeanUtils.copyProperties(form, entity);
        // 校验重复性
        checkSysConfigUniqueness(entity);
        entity.setUpdateBy(sysSecurityUtils.getCurrentUserId());
        entity.setUpdateTime(LocalDateTime.now());
        return retBool(this.baseMapper.update(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigDO saveSysConfig(SysConfigForm form) {
        SysConfigDO entity = SysConfigFactory.INSTANCE.form2Do(form);
        // 判断是否重复
//        checkSysConfigUniqueness(entity);
        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigDO updateSysConfig(SysConfigForm form) {
        SysConfigDO entity = SysConfigFactory.INSTANCE.form2Do(form);
        // 判断是否重复
//        checkSysConfigUniqueness(entity);
        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysConfig(Long id) {
        SysConfigDO sysConfig = getById(id);
        if (sysConfig == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysConfig(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    public String getValueByKey(String key) {
        return this.baseMapper.getValueByKey(key);
    }

    @Override
    public SysConfigDO getByKey(String key) {
        return this.baseMapper.getByKey(key);
    }

    /**
     * 校验系统配置是否唯一
     *
     * @param config
     */
    private void checkSysConfigUniqueness(SysConfigDO config) {
        SysConfigDO dbRecord = this.baseMapper.getByKey(config.getKey());
        if (dbRecord != null && !dbRecord.getId().equals(config.getId())) {
            throw new SysException("系统已存在该参数名对应的配置");
        }
    }

}