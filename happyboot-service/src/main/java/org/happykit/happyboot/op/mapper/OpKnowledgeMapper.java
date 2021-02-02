package org.happykit.happyboot.op.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.op.model.entity.OpKnowledgeDO;
import org.happykit.happyboot.op.model.query.OpKnowledgePageQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 知识库
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
public interface OpKnowledgeMapper extends BaseMapper<OpKnowledgeDO> {
    /**
     * 分页列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<OpKnowledgeDO> page(Page page, @Param("query") OpKnowledgePageQuery param);
}
