package org.happykit.happyboot.sys.model.query;


import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 主体分页列表查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSubjectPageQueryParam extends PageQuery {
    /**
     * 主体名称
     */
    private String subjectName;
}
