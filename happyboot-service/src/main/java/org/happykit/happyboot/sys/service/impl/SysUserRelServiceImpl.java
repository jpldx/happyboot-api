package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysUserRelMapper;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.entity.SysUserRelDO;
import org.happykit.happyboot.sys.service.SysUserRelService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 用户账号关系表
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/2
 */
@Validated
@Service
//@CacheConfig(cacheNames = {"sysUser"})
public class SysUserRelServiceImpl extends ServiceImpl<SysUserRelMapper, SysUserRelDO> implements SysUserRelService {

    @Override
    public void addRel(String mainUserId, String subUserId) {
//        LambdaQueryWrapper<SysUserRelDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysUserRelDO::getMainUserId, mainUserId);
//        queryWrapper.eq(SysUserRelDO::getSubUserId, subUserId);
//        SysUserRelDO record = this.getOne(queryWrapper);
//        if(null != record){
//            throw new RuntimeException("已关联该账号");
//        }
        SysUserRelDO rel = new SysUserRelDO(mainUserId, subUserId);
        this.save(rel);
    }

    @Override
    public void delRel(String userId, String userType) {
        this.baseMapper.delRel(userId, userType);
    }

    @Override
    public List<SysUserDO> getUserRelListByUserId(String userId, String userType) {
        return this.baseMapper.getUserRelListByUserId(userId, userType);
    }
}