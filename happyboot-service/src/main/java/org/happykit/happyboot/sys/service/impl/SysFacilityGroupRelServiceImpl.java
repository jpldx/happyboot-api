package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysFacilityGroupRelMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupRelDO;
import org.happykit.happyboot.sys.model.form.SysFacilityGroupRelForm;
import org.happykit.happyboot.sys.service.SysFacilityGroupRelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能组-功能关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Slf4j
@Service
public class SysFacilityGroupRelServiceImpl extends ServiceImpl<SysFacilityGroupRelMapper, SysFacilityGroupRelDO> implements SysFacilityGroupRelService {

    @Override
    public Set<String> getFacilityIdsByGroupId(String groupId) {
        return this.baseMapper.getFacilityIdsByGroupId(groupId);
    }

    @Override
    public boolean saveFacilityGroupRel(SysFacilityGroupRelForm form) {
        String facilityGroupId = form.getFacilityGroupId();
        Set<String> facilityIds = form.getFacilityIds();
        // 删除关联
        this.baseMapper.deleteByFacilityGroupId(facilityGroupId);

        if (CollectionUtils.isEmpty(facilityIds)) {
            return true;
        }

        List<SysFacilityGroupRelDO> entities = facilityIds.stream().map(facilityId -> {
            SysFacilityGroupRelDO entity = new SysFacilityGroupRelDO();
            entity.setFacilityGroupId(facilityGroupId);
            entity.setFacilityId(facilityId);
            return entity;
        }).collect(Collectors.toList());
        return this.saveBatch(entities);
    }
}