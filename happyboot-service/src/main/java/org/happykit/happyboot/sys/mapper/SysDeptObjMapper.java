package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.query.SysDeptObjQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 对象内部部门表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysDeptObjMapper extends BaseMapper<SysDeptObjDO> {

    /**
     * 列表查询
     *
     * @param param
     * @return
     */
    List<SysDeptObjDO> selectSysDeptObjs(@Param("param") SysDeptObjQueryParam param);

    /**
     * 通过用户ID获取部门列表
     *
     * @param userId
     * @return
     */
    List<SysDeptObjDO> selectSysDeptObjsByUserId(@Param("userId") String userId);

    /**
     * 通过对象ID、用户ID获取部门列表
     *
     * @param subjectId
     * @param userId
     * @return
     */
    List<SysDeptObjDO> selectSysDeptObjsByObjIdAndUserId(@Param("subjectId") String subjectId, @Param("userId") String userId);

    /**
     * 通过ID清空部门编号
     *
     * @param entity
     * @return
     */
    int updateDeptCodeNull(SysDeptObjDO entity);

    /**
     * 根据部门id查询部门名称
     *
     * @param id 部门id
     * @return
     */
    String getNameById(@Param("id") String id);
}
