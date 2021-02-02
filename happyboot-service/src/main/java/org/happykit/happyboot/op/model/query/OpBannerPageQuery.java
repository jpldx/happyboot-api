package org.happykit.happyboot.op.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpBannerPageQuery extends PageQuery {
    /**
     * 是否启用
     */
    private String enable;
}
