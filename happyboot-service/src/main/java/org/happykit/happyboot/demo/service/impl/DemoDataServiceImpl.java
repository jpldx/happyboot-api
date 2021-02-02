package org.happykit.happyboot.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.demo.model.entity.DemoDataDO;
import org.happykit.happyboot.demo.factory.DemoDataFactory;
import org.happykit.happyboot.demo.model.form.DemoDataForm;
import org.happykit.happyboot.demo.mapper.DemoDataMapper;
import org.happykit.happyboot.demo.model.query.DemoDataPageQueryParam;
import org.happykit.happyboot.demo.service.DemoDataService;
import org.happykit.happyboot.page.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/06/10
 */
@Validated(Default.class)
@Service
public class DemoDataServiceImpl extends ServiceImpl<DemoDataMapper, DemoDataDO> implements DemoDataService {

    @Override
    public IPage<DemoDataDO> listDemoDatasByPage(DemoDataPageQueryParam param) {
        Page page = new PageUtils<DemoDataPageQueryParam>().page(param, DemoDataDO.class);
        return this.baseMapper.selectDemoDatasByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DemoDataDO saveDemoData(DemoDataForm form) {
        DemoDataDO entity = DemoDataFactory.INSTANCE.form2Do(form);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DemoDataDO updateDemoData(DemoDataForm form) {
        DemoDataDO entity = DemoDataFactory.INSTANCE.form2Do(form);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDemoData(Long id) {
        DemoDataDO demoData = getById(id);
        if (demoData == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDemoData(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }
}