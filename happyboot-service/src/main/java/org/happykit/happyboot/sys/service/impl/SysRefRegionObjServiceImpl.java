package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysRefRegionObjMapper;
import org.happykit.happyboot.sys.model.entity.SysRefRegionObjDO;
import org.happykit.happyboot.sys.service.SysRefRegionObjService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 对象区域关系表 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated(Default.class)
@Service
public class SysRefRegionObjServiceImpl extends ServiceImpl<SysRefRegionObjMapper, SysRefRegionObjDO>
        implements SysRefRegionObjService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRefRegionObj(Long id) {
        SysRefRegionObjDO sysRefRegionObj = getById(id);
        if (sysRefRegionObj == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRefRegionObj(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByObjId(Long objId) {
        return retBool(this.baseMapper.deleteByObjId(objId));
    }
}