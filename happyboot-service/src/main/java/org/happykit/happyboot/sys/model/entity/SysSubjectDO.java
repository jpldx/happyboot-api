package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 主体
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_subject")
public class SysSubjectDO extends BaseEntity {
    /**
     * 主体名称
     */
    private String subjectName;
}
