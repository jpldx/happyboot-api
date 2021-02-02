package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.happykit.happyboot.sys.model.entity.SysRefRegionObjDO;
import org.apache.ibatis.annotations.Param;

/**
 * 对象区域关系表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysRefRegionObjMapper extends BaseMapper<SysRefRegionObjDO> {

    /**
     * 通过单位id删除
     *
     * @param objId
     * @return
     */
    int deleteByObjId(@Param("objId") Long objId);
}
