package org.happykit.happyboot.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.sys.mapper.SysFacilityMapper;
import org.happykit.happyboot.sys.mapper.SysFacilityParamMapper;
import org.happykit.happyboot.sys.mapper.SysFacilityParamRelMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityDO;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamDO;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamRelDO;
import org.happykit.happyboot.sys.model.form.SysFacilityParamForm;
import org.happykit.happyboot.sys.model.query.SysFacilityPageQueryParam;
import org.happykit.happyboot.sys.model.vo.SysFacilityVO;
import org.happykit.happyboot.sys.service.SysFacilityService;
import org.happykit.happyboot.util.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能点
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Slf4j
@Service
public class SysFacilityServiceImpl extends ServiceImpl<SysFacilityMapper, SysFacilityDO> implements SysFacilityService {

    @Autowired
    private SysFacilityParamRelMapper sysFacilityParamRelMapper;
    @Autowired
    private SysFacilityParamMapper sysFacilityParamMapper;

    @Override
    public IPage<SysFacilityDO> page(SysFacilityPageQueryParam query) {
        Page page = new Page(query.getPageNo(), query.getPageSize());
        return this.baseMapper.page(page, query);
    }

    @Transactional
    @Override
    public boolean setParam(SysFacilityParamForm form) {
        String setFrom = form.getSetFrom();
        String facilityGroupId = form.getFacilityGroupId();
        String userId = form.getUserId();
        String facilityParamId = form.getFacilityParamId();
        String facilityParam = form.getFacilityParam();
        // 参数校验
        try {
            JSONObject.parseObject(facilityParam);
        } catch (Exception e) {
            throw new SysException("功能参数格式错误");
        }
        if ("group".equals(setFrom)) {
            Assert.isBlank(facilityGroupId, "功能组ID不能为空");
        }
        if ("user".equals(setFrom)) {
            Assert.isBlank(userId, "用户ID不能为空");
        }
        // 新增
        if (null == facilityParamId) {
            // 设置参数
            SysFacilityParamDO par = new SysFacilityParamDO();
            par.setFacilityParam(facilityParam);
            sysFacilityParamMapper.insert(par);

            // 保存关系表
            SysFacilityParamRelDO rel = new SysFacilityParamRelDO();
            rel.setFacilityId(form.getFacilityId())
                    .setFacilityParamId(par.getId())
                    .setSetFrom(form.getSetFrom())
                    .setUserId(form.getUserId())
                    .setFacilityGroupId(form.getFacilityGroupId());
            sysFacilityParamRelMapper.insert(rel);
            return true;
        }

        // 更新
        SysFacilityParamDO par = sysFacilityParamMapper.selectById(facilityParamId);
        Assert.isNotFound(par);
        par.setFacilityParam(facilityParam);
        sysFacilityParamMapper.updateById(par);
        return true;
    }

    @Override
    public SysFacilityParamForm getParam(String id, String setFrom, String facilityGroupId, String userId) {
        if ("group".equals(setFrom)) {
            Assert.isBlank(facilityGroupId, "功能组ID不能为空");
        }
        if ("user".equals(setFrom)) {
            Assert.isBlank(userId, "用户ID不能为空");
        }
        return this.baseMapper.getParam(id, setFrom, facilityGroupId, userId);
    }

    @Override
    @Transactional
    public boolean deleteParam(String facilityParamId) {
        sysFacilityParamRelMapper.delete(facilityParamId);
        sysFacilityParamMapper.delete(facilityParamId);
        return true;
    }

    @Override
    public List<SysFacilityVO> getFacilityByUser(String userId) {
        // 查询所有功能
        List<SysFacilityDO> facilities = this.baseMapper.selectList(new QueryWrapper<SysFacilityDO>()
                .orderByDesc("create_time"));
        // 查询用户关联功能
        Set<String> userFacilityIds = this.baseMapper.getFacilityIdsByUserId(userId);
        return facilities.stream().map(facility -> {
            SysFacilityVO model = new SysFacilityVO();
            model.setId(facility.getId());
            model.setName(facility.getFacilityName());
            model.setType(facility.getFacilityType());
            model.setPlatform(facility.getFacilityPlatform());
            model.setDes(facility.getDes());
            model.setFlag(userFacilityIds.contains(facility.getId()));
            return model;
        }).collect(Collectors.toList());
    }
}