package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysRefUserDeptRegionMapper;
import org.happykit.happyboot.sys.model.entity.SysRefUserDeptRegionDO;
import org.happykit.happyboot.sys.service.SysRefUserDeptRegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 人员与区域部门关系表 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated(Default.class)
@Service
public class SysRefUserDeptRegionServiceImpl extends ServiceImpl<SysRefUserDeptRegionMapper, SysRefUserDeptRegionDO>
        implements SysRefUserDeptRegionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRefUserDeptRegion(Long id) {
        SysRefUserDeptRegionDO sysRefUserDeptRegion = getById(id);
        if (sysRefUserDeptRegion == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRefUserDeptRegion(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByUserId(String userId) {
        return retBool(this.baseMapper.deleteByUserId(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRegionId(Long regionId) {
        return retBool(this.baseMapper.deleteByRegionId(regionId));
    }

    @Override
    public List<String> listRegionIdsByUserId(Long userId) {
        return this.baseMapper.listRegionIdsByUserId(userId);
    }
}