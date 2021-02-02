package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.happykit.happyboot.sys.model.entity.SysFileDO;
import org.happykit.happyboot.sys.model.query.SysFilePageQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/03/30
 */
public interface SysFileMapper extends BaseMapper<SysFileDO> {

    /**
     * 通过sha1获取文件对象
     *
     * @param sha1
     * @return
     */
    SysFileDO selectBySha1(@Param("sha1") String sha1);

    /**
     * 分页列表
     *
     * @param page
     * @param query
     * @return
     */
    IPage<SysFileDO> page(Page page, @Param("query") SysFilePageQueryParam query);
}
