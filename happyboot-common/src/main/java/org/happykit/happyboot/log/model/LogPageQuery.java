package org.happykit.happyboot.log.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.happykit.happyboot.page.PageQuery;

/**
 * 日志分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogPageQuery extends PageQuery {
    /**
     * 用户名
     */
    private String username;
}
