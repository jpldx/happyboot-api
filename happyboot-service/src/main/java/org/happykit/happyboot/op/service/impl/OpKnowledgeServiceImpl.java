package org.happykit.happyboot.op.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.model.model.ImageArray;
import org.happykit.happyboot.op.mapper.OpKnowledgeMapper;
import org.happykit.happyboot.op.model.entity.OpKnowledgeDO;
import org.happykit.happyboot.op.model.form.OpKnowledgeForm;
import org.happykit.happyboot.op.model.query.OpKnowledgePageQuery;
import org.happykit.happyboot.op.service.OpKnowledgeService;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Date;

/**
 * 知识库
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Validated(Default.class)
@Service
public class OpKnowledgeServiceImpl extends ServiceImpl<OpKnowledgeMapper, OpKnowledgeDO> implements OpKnowledgeService {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;

    @Override
    public IPage<OpKnowledgeDO> page(OpKnowledgePageQuery query) {
        Page page = new PageUtils<OpKnowledgePageQuery>().page(query, OpKnowledgeDO.class);
        IPage<OpKnowledgeDO> pageList = this.baseMapper.page(page, query);
        for (OpKnowledgeDO record : pageList.getRecords()) {
            record.setImageArr(ImageArray.split(record.getImage()));
        }
        return pageList;
    }

    @Override
    public boolean add(OpKnowledgeForm form) {
        SysDeptObjDO dept = sysSecurityUtils.getCurrentUserDept();
        Assert.isNotFound(dept, SysExceptionConstant.NOT_FOUND_DEPT);

        OpKnowledgeDO entity = new OpKnowledgeDO();
        BeanUtils.copyProperties(form, entity);

        entity.setSubjectId(dept.getSubjectId());
        entity.setDeptId(dept.getId());
        entity.setDeptCode(dept.getDeptCode());
        entity.setImage(form.getImageArr().join());
        entity.setPubTime(new Date());
        return this.save(entity);
    }

    @Override
    public boolean update(OpKnowledgeForm form) {
        OpKnowledgeDO dbRecord = this.getById(form.getId());
        Assert.isNotFound(dbRecord);
        BeanUtils.copyProperties(form, dbRecord);
        dbRecord.setImage(form.getImageArr().join());
        return this.updateById(dbRecord);
    }

//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public boolean deleteSysKnowledge(String id) {
//		SysKnowledgeDO sysKnowledge = getById(id);
//		if (sysKnowledge == null) {
//			return false;
//		}
//		return removeById(id);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public boolean deleteSysKnowledge(String... ids) {
//		List idList = Arrays.asList(ids);
//		return this.removeByIds(idList);
//	}

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean sendSysKnowledge(String id) {
//        SysKnowledgeDO sysKnowledge = getById(id);
//        if (sysKnowledge == null) {
//            return false;
//        }
//        sysKnowledge.setEsSendStatus(CommonConstant.SendStatus.SEND);
//        sysKnowledge.setEsSendTime(new Date());
//        if (!updateById(sysKnowledge)) {
//            throw new RuntimeException("保存失败");
//        }
//        return true;
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean backSysKnowledge(String id) {
//        SysKnowledgeDO sysKnowledge = getById(id);
//        if (sysKnowledge == null) {
//            return false;
//        }
//        sysKnowledge.setEsSendStatus(CommonConstant.SendStatus.BACK);
//        sysKnowledge.setEsSendTime(new Date());
//        if (!updateById(sysKnowledge)) {
//            throw new RuntimeException("保存失败");
//        }
//        return true;
//    }

    @Override
    public OpKnowledgeDO get(String id) {
        OpKnowledgeDO dbRecord = this.getById(id);
        Assert.isNotFound(dbRecord);
        dbRecord.setImageArr(ImageArray.split(dbRecord.getImage()));
        return dbRecord;
    }
}