package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.happykit.happyboot.sys.model.entity.SysTreeDictDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 树状字典表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/5/18
 */
public interface SysTreeDictMapper extends BaseMapper<SysTreeDictDO> {
    /**
     * 通过字典类型查询列表
     *
     * @param dictCode
     * @return
     */
    List<SysTreeDictDO> selectSysTreeDictsByDictCode(@Param("dictCode") String dictCode);

    /**
     * 通过字典类型与父级ID查询列表
     *
     * @param dictCode
     * @param parentCode
     * @return
     */
    List<SysTreeDictDO> selectSysTreeDictsByDictCodeAndParentCode(@Param("dictCode") String dictCode, @Param("parentCode") String parentCode);
}
