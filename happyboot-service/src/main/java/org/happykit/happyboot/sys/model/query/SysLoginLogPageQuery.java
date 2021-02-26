package org.happykit.happyboot.sys.model.query;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.happykit.happyboot.page.PageQuery;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录历史分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLoginLogPageQuery extends PageQuery {
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;
}
