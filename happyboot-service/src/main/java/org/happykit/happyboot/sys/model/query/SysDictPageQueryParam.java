package org.happykit.happyboot.sys.model.query;


import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictPageQueryParam extends PageQuery {
    /**
     * 字段名称
     */
    private String dictName;
    /**
     * 字段代号，默认大写加下划线
     */
    private String dictCode;
}
