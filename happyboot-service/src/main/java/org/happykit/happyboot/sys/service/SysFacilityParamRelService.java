package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamRelDO;

/**
 * 功能点-功能点参数关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityParamRelService extends IService<SysFacilityParamRelDO> {

    /**
     * 根据功能参数ID删除
     *
     * @param facilityParamId 功能参数ID
     * @return
     */
    boolean delete(String facilityParamId);
}

