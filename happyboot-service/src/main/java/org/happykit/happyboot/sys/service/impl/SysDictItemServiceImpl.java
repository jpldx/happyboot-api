package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.CacheConstant;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.factory.SysDictItemFactory;
import org.happykit.happyboot.sys.mapper.SysDictItemMapper;
import org.happykit.happyboot.sys.model.entity.SysDictItemDO;
import org.happykit.happyboot.sys.model.form.SysDictItemForm;
import org.happykit.happyboot.sys.model.query.SysDictItemPageQueryParam;
import org.happykit.happyboot.sys.service.SysDictItemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 字典项 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Validated(Default.class)
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItemDO>
        implements SysDictItemService {

    @Override
    public SysDictItemDO getByDictIdAndItemValue(String dictId, String itemValue) {
        return this.baseMapper.selectByDictIdAndItemValue(dictId, itemValue);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.PREFIX_DICT, key = "#dictId")
    public List<SysDictItemDO> listSysDictItemsByDictId(String dictId) {
        return this.baseMapper.selectSysDictItemsByDictId(dictId);
    }

    @Override
    public IPage<SysDictItemDO> listSysDictItemsByPage(SysDictItemPageQueryParam param) {
        Page page = new PageUtils<SysDictItemPageQueryParam>().page(param, SysDictItemDO.class);
        return this.baseMapper.selectSysDictItemsByPage(page, param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictItemDO saveSysDictItem(SysDictItemForm form) {
        SysDictItemDO entity = SysDictItemFactory.INSTANCE.form2Do(form);
        // 判断字典代号是否重复
        checkSysDictItemUniqueness(entity);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictItemDO updateSysDictItem(SysDictItemForm form) {
        SysDictItemDO entity = SysDictItemFactory.INSTANCE.form2Do(form);
        // 判断字典代号是否重复
        checkSysDictItemUniqueness(entity);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    public boolean deleteSysDictItem(@NotNull Long id) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDictItem(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByDictId(Long dictId) {
        LambdaUpdateWrapper<SysDictItemDO> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(SysDictItemDO::getDictId, dictId);
        return this.remove(lambdaUpdateWrapper);
    }

    /**
     * 判断字典项值是否唯一
     *
     * @param sysDictItem
     */
    private void checkSysDictItemUniqueness(SysDictItemDO sysDictItem) {
        SysDictItemDO temp =
                this.baseMapper.selectByDictIdAndItemValue(sysDictItem.getDictId(), sysDictItem.getItemValue());
        if (temp != null && !temp.getId().equals(sysDictItem.getId())) {
            throw new RuntimeException(String.format("当前字典项值【%s】已经存在，请重新选择一个字典项值", sysDictItem.getItemValue()));
        }
    }
}
