package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.mapper.SysLogMapper;
import org.happykit.happyboot.sys.model.entity.SysLogDO;
import org.happykit.happyboot.sys.model.query.SysLogPageQueryParam;
import org.happykit.happyboot.sys.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 系统日志 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogDO> implements SysLogService {
    @Override
    public IPage<SysLogDO> listSysLogsByPage(SysLogPageQueryParam param) {
        Page page = new PageUtils<SysLogPageQueryParam>().page(param, SysLogDO.class);
        return this.page(page);
    }
}
