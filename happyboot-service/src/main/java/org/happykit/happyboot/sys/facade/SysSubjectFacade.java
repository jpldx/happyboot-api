package org.happykit.happyboot.sys.facade;

import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.enums.StatusEnum;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.entity.SysDeptRegionDO;
import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.model.entity.SysRefRegionObjDO;
import org.happykit.happyboot.sys.model.form.SysSubjectForm;
import org.happykit.happyboot.sys.model.form.SysObjRegionForm;
import org.happykit.happyboot.sys.service.SysDeptObjService;
import org.happykit.happyboot.sys.service.SysDeptRegionService;
import org.happykit.happyboot.sys.service.SysSubjectService;
import org.happykit.happyboot.sys.service.SysRefRegionObjService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 主体
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSubjectFacade {
    private final SysSubjectService sysSubjectService;
    private final SysDeptObjService sysDeptObjService;
    private final SysRefRegionObjService sysRefRegionObjService;
    private final SysDeptRegionService sysDeptRegionService;

    /**
     * 保存
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SysSubjectDO saveSysObj(SysSubjectForm form) {
        SysSubjectDO sysSubject = sysSubjectService.saveSysObj(form);

        SysDeptObjDO entity = new SysDeptObjDO();
        entity.setParentId(SysConstant.ROOT_PARENT_ID_STR);
        entity.setDeptName(sysSubject.getSubjectName());
        entity.setOrderId(1);
        entity.setStatus(StatusEnum.ENABLE.getCode());
        entity.setSubjectId(sysSubject.getId());
        sysDeptObjService.saveSysDeptObjParent(entity);
        return sysSubject;
    }

    /**
     * 保存区域-主体关联
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveObjRegion(SysObjRegionForm form) {
        SysSubjectDO sysObj = sysSubjectService.getById(form.getObjId());
        if (sysObj == null) {
            return false;
        }
        // 先删除
        sysRefRegionObjService.deleteByObjId(form.getObjId());
        if (CollectionUtils.isEmpty(form.getRegionIds())) {
            return true;
        }
        Set<Long> regionIdSet = new HashSet<>(form.getRegionIds());

        List<SysRefRegionObjDO> entityList = new ArrayList<>();
        regionIdSet.stream().forEach(regionId -> {
            SysDeptRegionDO sysDeptRegion = sysDeptRegionService.getById(regionId);
            if (sysDeptRegion != null) {
                SysRefRegionObjDO entity = new SysRefRegionObjDO();
                entity.setObjId(form.getObjId());
                entity.setRegionId(regionId);
                entity.setRegionCode(sysDeptRegion.getRegionCode());
                entityList.add(entity);
            }
        });

        return sysRefRegionObjService.saveBatch(entityList);
    }
}
