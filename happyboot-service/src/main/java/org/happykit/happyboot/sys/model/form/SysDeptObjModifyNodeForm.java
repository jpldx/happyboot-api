package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 对象内部部门表节点变更
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysDeptObjModifyNodeForm implements Serializable {
    /**
     * 主键id
     */
    @NotNull(message = "主键必须填")
    private String id;
    /**
     * 父亲
     */
    @NotNull(message = "父ID必须填")
    private String parentId;

}