package org.happykit.happyboot.op.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.model.model.ImageArray;
import org.happykit.happyboot.op.factory.OpBannerFactory;
import org.happykit.happyboot.op.mapper.OpBannerMapper;
import org.happykit.happyboot.op.model.entity.OpBannerDO;
import org.happykit.happyboot.op.model.form.OpBannerForm;
import org.happykit.happyboot.op.model.query.OpBannerPageQuery;
import org.happykit.happyboot.op.service.OpBannerService;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 轮播图
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Service
public class OpBannerServiceImpl extends ServiceImpl<OpBannerMapper, OpBannerDO> implements OpBannerService {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;

    @Override
    public IPage<OpBannerDO> page(OpBannerPageQuery query) {
        Page page = new PageUtils<OpBannerPageQuery>().page(query, OpBannerDO.class);
        IPage<OpBannerDO> pageList = this.baseMapper.page(page, query);
        for (OpBannerDO record : pageList.getRecords()) {
            record.setImageArr(ImageArray.split(record.getImage()));
        }
        return pageList;
    }

    @Override
    public boolean add(OpBannerForm form) {
        OpBannerDO entity = OpBannerFactory.INSTANCE.form2Do(form);
        SysDeptObjDO dept = sysSecurityUtils.getCurrentUserDept();
        if (null == dept) {
            throw new RuntimeException(SysExceptionConstant.NOT_FOUND_DEPT);
        }
        entity.setSubjectId(dept.getSubjectId());
        entity.setImage(form.getImageArr().join());
        return this.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(OpBannerForm form) {
        OpBannerDO entity = OpBannerFactory.INSTANCE.form2Do(form);
        entity.setImage(form.getImageArr().join());
        return this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBaseBanner(String id) {
        OpBannerDO baseBanner = getById(id);
        if (baseBanner == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    public OpBannerDO get(String id) {
        OpBannerDO dbRecord = getById(id);
        Assert.isNotFound(dbRecord);
        dbRecord.setImageArr(ImageArray.split(dbRecord.getImage()));
        return dbRecord;
    }
}