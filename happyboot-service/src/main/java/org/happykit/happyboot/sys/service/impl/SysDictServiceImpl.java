package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.factory.SysDictFactory;
import org.happykit.happyboot.sys.mapper.SysDictMapper;
import org.happykit.happyboot.sys.model.entity.SysDictDO;
import org.happykit.happyboot.sys.model.form.SysDictForm;
import org.happykit.happyboot.sys.model.query.SysDictPageQueryParam;
import org.happykit.happyboot.sys.service.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 数据字典 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Validated(Default.class)
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictDO> implements SysDictService {

    @Override
    public SysDictDO getByDictCode(String dictCode) {
        return this.baseMapper.selectByDictCode(dictCode);
    }

    @Override
    public String getTextByCodeAndValue(String code, String value) {
        return this.baseMapper.getTextByCodeAndValue(code, value);
    }

    @Override
    public IPage<SysDictDO> listSysDictsByPage(SysDictPageQueryParam param) {
        Page page = new PageUtils<SysDictPageQueryParam>().page(param, SysDictDO.class);
        return this.baseMapper.selectSysDictsByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictDO saveSysDict(SysDictForm form) {
        SysDictDO entity = SysDictFactory.INSTANCE.form2Do(form);
        // 判断字典代号是否重复
        checkSysDictUniqueness(entity);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictDO updateSysDict(SysDictForm form) {
        SysDictDO entity = SysDictFactory.INSTANCE.form2Do(form);
        // 判断字典代号是否重复
        checkSysDictUniqueness(entity);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDict(Long id) {
        SysDictDO sysDict = getById(id);
        if (sysDict == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDict(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    /**
     * 判断字典代名是否唯一
     *
     * @param sysDict
     */
    private void checkSysDictUniqueness(SysDictDO sysDict) {
        SysDictDO temp = this.baseMapper.selectByDictCode(sysDict.getDictCode());
        if (temp != null && !temp.getId().equals(sysDict.getId())) {
            throw new RuntimeException(String.format("当前字典代号【%s】已经存在，请重新选择一个字典代号", sysDict.getDictCode()));
        }
    }
}
