package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.query.SysMsgPageQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * 消息管理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
public interface SysMsgMapper extends BaseMapper<SysMsgDO> {
    /**
     * 分页列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysMsgDO> page(Page page, @Param("param") SysMsgPageQueryParam param);
}
