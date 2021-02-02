package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.happykit.happyboot.sys.model.entity.SysRefUserDeptRegionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员与区域部门关系表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysRefUserDeptRegionMapper extends BaseMapper<SysRefUserDeptRegionDO> {
    /**
     * 通过用户id删除
     *
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 通过区域id删除
     *
     * @param regionId
     * @return
     */
    int deleteByRegionId(@Param("regionId") Long regionId);

    /**
     * 查询用户关联的区域id列表
     *
     * @param userId
     * @return
     */
    List<String> listRegionIdsByUserId(@Param("userId") Long userId);
}