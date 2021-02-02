package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupRelDO;
import org.happykit.happyboot.sys.model.form.SysFacilityGroupRelForm;

import java.util.Set;

/**
 * 功能组-功能点关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityGroupRelService extends IService<SysFacilityGroupRelDO> {

    /**
     * 根据功能组查询功能IDS
     *
     * @param groupId
     * @return
     */
    Set<String> getFacilityIdsByGroupId(String groupId);

    /**
     * 保存功能组-功能关联关系
     *
     * @param form
     * @return
     */
    boolean saveFacilityGroupRel(SysFacilityGroupRelForm form);

}

