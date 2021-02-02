package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.factory.SysAttrConfigFactory;
import org.happykit.happyboot.sys.mapper.SysAttrConfigMapper;
import org.happykit.happyboot.sys.model.entity.SysAttrConfigDO;
import org.happykit.happyboot.sys.model.form.SysAttrConfigForm;
import org.happykit.happyboot.sys.model.query.SysAttrConfigPageQueryParam;
import org.happykit.happyboot.sys.service.SysAttrConfigService;
import org.happykit.happyboot.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @version 1.0 2020/07/06
 */
@Slf4j
@Validated(Default.class)
@Service
public class SysAttrConfigServiceImpl extends ServiceImpl<SysAttrConfigMapper, SysAttrConfigDO> implements SysAttrConfigService {

    @Override
    public IPage<SysAttrConfigDO> listSysAttrConfigsByPage(SysAttrConfigPageQueryParam param) {
        Page page = new PageUtils<SysAttrConfigPageQueryParam>().page(param, SysAttrConfigDO.class);
        return this.baseMapper.selectSysAttrConfigsByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysAttrConfigDO saveSysAttrConfig(SysAttrConfigForm form) {
        SysAttrConfigDO entity = SysAttrConfigFactory.INSTANCE.form2Do(form);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysAttrConfigDO updateSysAttrConfig(SysAttrConfigForm form) {
        SysAttrConfigDO entity = SysAttrConfigFactory.INSTANCE.form2Do(form);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysAttrConfig(Long id) {
        SysAttrConfigDO sysAttrConfig = getById(id);
        if (sysAttrConfig == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysAttrConfig(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    public boolean CheckBatchIsSuccess() {
        String value = this.baseMapper.querySysAttrConfigTextByKey();
        if (StringUtils.isBlank(value)) {
            log.info("未设置BATCH_RUN_SUCCESSFUL_DATE参数");
            return false;
        }
        //当天已正常运行
        return value.compareTo(DateUtils.formatDate()) >= 0;
    }

    @Override
    public void UpdateBatchSuccess() {
        this.baseMapper.UpdateBatchSuccess(DateUtils.formatDate());
    }
}