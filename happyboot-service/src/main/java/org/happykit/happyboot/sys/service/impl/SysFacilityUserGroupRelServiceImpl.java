package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysFacilityUserGroupRelMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityUserGroupRelDO;
import org.happykit.happyboot.sys.service.SysFacilityUserGroupRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户-功能组关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Slf4j
@Service
public class SysFacilityUserGroupRelServiceImpl extends ServiceImpl<SysFacilityUserGroupRelMapper, SysFacilityUserGroupRelDO> implements SysFacilityUserGroupRelService {

    @Override
    public int deleteByUserId(String userId) {
        return this.baseMapper.deleteByUserId(userId);
    }
}