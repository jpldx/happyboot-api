package org.happykit.happyboot.sys.model.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 单位区域提交对象
 *
 * @author shaoqiang
 * @version 1.0 2020/03/25
 */
@Data
public class SysObjRegionForm implements Serializable {
    /**
     * 对象id
     */
    @NotNull(message = "对象id必须填")
    private Long objId;
    /**
     * 所选区域
     */
    private List<Long> regionIds;
}