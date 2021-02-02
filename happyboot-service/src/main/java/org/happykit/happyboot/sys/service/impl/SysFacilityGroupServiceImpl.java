package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.model.model.IdNameFlagModel;
import org.happykit.happyboot.sys.mapper.SysFacilityGroupMapper;
import org.happykit.happyboot.sys.mapper.SysFacilityGroupRelMapper;
import org.happykit.happyboot.sys.mapper.SysFacilityMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityDO;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupDO;
import org.happykit.happyboot.sys.model.query.SysFacilityGroupPageQueryParam;
import org.happykit.happyboot.sys.model.vo.SysFacilityVO;
import org.happykit.happyboot.sys.service.SysFacilityGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能组
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Slf4j
@Service
public class SysFacilityGroupServiceImpl extends ServiceImpl<SysFacilityGroupMapper, SysFacilityGroupDO> implements SysFacilityGroupService {

    @Autowired
    private SysFacilityMapper sysFacilityMapper;
    @Autowired
    private SysFacilityGroupRelMapper sysFacilityGroupRelMapper;

    @Override
    public IPage<SysFacilityGroupDO> page(SysFacilityGroupPageQueryParam query) {
        Page page = new Page(query.getPageNo(), query.getPageSize());
        return this.baseMapper.page(page, query);
    }

    @Override
    public List<IdNameFlagModel> queryFacilityGroupByUser(String userId) {
        List<SysFacilityGroupDO> groups = this.list();
        Set<String> userGroupIds = this.baseMapper.getFacilityGroupIdsByUserId(userId);
        return groups.stream().map(group -> {
            IdNameFlagModel model = new IdNameFlagModel();
            model.setId(group.getId());
            model.setName(group.getFacilityGroupName());
            model.setFlag(userGroupIds.contains(group.getId()));
            return model;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysFacilityVO> queryFacilityByGroup(String groupId) {
        List<SysFacilityDO> facilities = sysFacilityMapper.selectList(new QueryWrapper<SysFacilityDO>()
                .orderByDesc("create_time"));
        Set<String> groupFacilityIds = sysFacilityGroupRelMapper.getFacilityIdsByGroupId(groupId);
        return facilities.stream().map(facility -> {
            SysFacilityVO model = new SysFacilityVO();
            model.setId(facility.getId());
            model.setName(facility.getFacilityName());
            model.setType(facility.getFacilityType());
            model.setPlatform(facility.getFacilityPlatform());
            model.setDes(facility.getDes());
            model.setFlag(groupFacilityIds.contains(facility.getId()));
            return model;
        }).collect(Collectors.toList());
    }
}