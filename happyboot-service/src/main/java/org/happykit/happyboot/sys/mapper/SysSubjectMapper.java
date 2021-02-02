package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.model.query.SysSubjectPageQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主体
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
public interface SysSubjectMapper extends BaseMapper<SysSubjectDO> {
    /**
     * 通过用户id获取主体列表
     *
     * @param userId
     * @return
     */
    List<SysSubjectDO> selectSysObjsByUserId(@Param("userId") String userId);

    /**
     * 分页列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysSubjectDO> selectSysObjsByPage(Page page, @Param("param") SysSubjectPageQueryParam param);

    String getNameById(@Param("id") String id);
}
