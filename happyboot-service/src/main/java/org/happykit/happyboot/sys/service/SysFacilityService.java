package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysFacilityDO;
import org.happykit.happyboot.sys.model.form.SysFacilityParamForm;
import org.happykit.happyboot.sys.model.query.SysFacilityPageQueryParam;
import org.happykit.happyboot.sys.model.vo.SysFacilityVO;

import java.util.List;

/**
 * 功能
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityService extends IService<SysFacilityDO> {

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<SysFacilityDO> page(SysFacilityPageQueryParam query);

    /**
     * 参数设置
     *
     * @param form
     * @return
     */
    boolean setParam(SysFacilityParamForm form);

    /**
     * 参数查询
     *
     * @param id              功能ID
     * @param setFrom         功能定义
     * @param facilityGroupId 功能组ID
     * @param userId          用户ID
     * @return
     */
    SysFacilityParamForm getParam(String id, String setFrom, String facilityGroupId, String userId);

    /**
     * 参数删除
     *
     * @param facilityParamId 功能ID
     * @return
     */
    boolean deleteParam(String facilityParamId);

    /**
     * 根据用户查询功能
     *
     * @param userId 用户ID
     * @return
     */
    List<SysFacilityVO> getFacilityByUser(String userId);
}

