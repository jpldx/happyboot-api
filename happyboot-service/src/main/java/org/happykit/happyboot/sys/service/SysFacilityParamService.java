package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamDO;

/**
 * 功能点参数
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityParamService extends IService<SysFacilityParamDO> {

    /**
     * 删除
     *
     * @param id 功能ID
     * @return
     */
    boolean delete(String id);

}

