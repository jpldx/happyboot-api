package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictItemPageQueryParam extends PageQuery {
    /**
     * 所属字典id
     */
    @NotNull(message = "所属字典id不能为空")
    private Long dictId;
    /**
     * 字典项文本
     */
    private String itemText;
    /**
     * 字典项值
     */
    private String itemValue;
}
