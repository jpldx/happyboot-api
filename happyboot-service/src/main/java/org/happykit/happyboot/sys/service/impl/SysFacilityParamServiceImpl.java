package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysFacilityParamMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamDO;
import org.happykit.happyboot.sys.service.SysFacilityParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能点参数
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Slf4j
@Service
public class SysFacilityParamServiceImpl extends ServiceImpl<SysFacilityParamMapper, SysFacilityParamDO> implements SysFacilityParamService {

    @Override
    public boolean delete(String id) {
        return retBool(this.baseMapper.delete(id));
    }
}