package org.happykit.happyboot.op.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识库分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpKnowledgePageQuery extends PageQuery {
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private String label;
    /**
     * 内容
     */
    private String content;
}
