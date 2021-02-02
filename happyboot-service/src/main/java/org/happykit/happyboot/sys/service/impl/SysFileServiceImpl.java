package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.factory.SysFileFactory;
import org.happykit.happyboot.sys.mapper.SysFileMapper;
import org.happykit.happyboot.sys.model.entity.SysFileDO;
import org.happykit.happyboot.sys.model.form.SysFileForm;
import org.happykit.happyboot.sys.model.query.SysFilePageQueryParam;
import org.happykit.happyboot.sys.service.SysFileService;
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
 * @version 1.0 2020/03/30
 */
@Validated(Default.class)
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFileDO> implements SysFileService {

    @Override
    public SysFileDO getBySha1(String sha1) {
        return this.baseMapper.selectBySha1(sha1);
    }

    @Override
    public IPage<SysFileDO> page(SysFilePageQueryParam query) {
//        Page page = new PageUtils<SysFilePageQueryParam>().page(param, SysFileDO.class);
        Page page = new Page(query.getPageNo(), query.getPageSize());
        return this.baseMapper.page(page, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileDO saveSysFile(SysFileForm form) {
        SysFileDO entity = SysFileFactory.INSTANCE.form2Do(form);

        SysFileDO sysFile = getBySha1(entity.getSha1());
        if (sysFile != null) {
            return sysFile;
        }
        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysFile(Long id) {
        SysFileDO sysFile = getById(id);
        if (sysFile == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysFile(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }
}