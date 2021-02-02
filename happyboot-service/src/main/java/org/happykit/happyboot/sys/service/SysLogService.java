package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysLogDO;
import org.happykit.happyboot.sys.model.query.SysLogPageQueryParam;

/**
 * 系统日志 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
public interface SysLogService extends IService<SysLogDO> {
    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysLogDO> listSysLogsByPage(SysLogPageQueryParam param);
}
