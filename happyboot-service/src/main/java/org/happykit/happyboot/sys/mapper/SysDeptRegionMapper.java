package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.happykit.happyboot.sys.model.entity.SysDeptRegionDO;
import org.happykit.happyboot.sys.model.query.SysDeptRegionQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域部门表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysDeptRegionMapper extends BaseMapper<SysDeptRegionDO> {

    /**
     * 列表查询
     *
     * @param param
     * @return
     */
    List<SysDeptRegionDO> selectSysDeptRegions(@Param("param") SysDeptRegionQueryParam param);

    /**
     * 通过父ID获取区域列表
     *
     * @param parentId
     * @return
     */
    List<SysDeptRegionDO> selectSysDeptRegionsByParentId(@Param("parentId") String parentId);

    /**
     * 通过用户id获取区域列表
     *
     * @param userId
     * @return
     */
    List<SysDeptRegionDO> selectSysDeptRegionsByUserId(@Param("userId") String userId);

    /**
     * 通过对象id获取区域列表
     *
     * @param objId
     * @return
     */
    List<SysDeptRegionDO> selectSysDeptRegionsByObjId(@Param("objId") String objId);

    /**
     * 通过id清空区域编号
     *
     * @param entity
     * @return
     */
    int updateRegionCodeNull(SysDeptRegionDO entity);
}
