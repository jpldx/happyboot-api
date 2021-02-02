package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysFacilityUserGroupRelDO;

/**
 * 用户-功能组关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityUserGroupRelService extends IService<SysFacilityUserGroupRelDO> {

    /**
     * 根据用户ID删除关联
     *
     * @param userId 用户ID
     * @return
     */
    int deleteByUserId(String userId);
}

