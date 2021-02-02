package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.model.model.IdNameFlagModel;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupDO;
import org.happykit.happyboot.sys.model.query.SysFacilityGroupPageQueryParam;
import org.happykit.happyboot.sys.model.vo.SysFacilityVO;

import java.util.List;

/**
 * 功能组
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityGroupService extends IService<SysFacilityGroupDO> {

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<SysFacilityGroupDO> page(SysFacilityGroupPageQueryParam query);

    /**
     * 查询用户-功能组关联关系
     *
     * @param userId 用户ID
     * @return
     */
    List<IdNameFlagModel> queryFacilityGroupByUser(String userId);


    /**
     * 查询功能组-功能关联关系
     *
     * @param groupId 功能组ID
     * @return
     */
    List<SysFacilityVO> queryFacilityByGroup(String groupId);
}

