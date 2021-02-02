package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysFacilityParamRelMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamRelDO;
import org.happykit.happyboot.sys.service.SysFacilityParamRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能点-功能点参数关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Slf4j
@Service
public class SysFacilityParamRelServiceImpl extends ServiceImpl<SysFacilityParamRelMapper, SysFacilityParamRelDO> implements SysFacilityParamRelService {

    @Override
    public boolean delete(String facilityParamId) {
        return retBool(this.baseMapper.delete(facilityParamId));
    }
}