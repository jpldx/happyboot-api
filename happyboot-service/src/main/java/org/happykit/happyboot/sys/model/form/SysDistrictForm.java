package org.happykit.happyboot.sys.model.form;


import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Data
public class SysDistrictForm implements Serializable {
    /**
     * id
     */
    @NotEmpty(message = "主键必须填", groups = Update.class)
    private String id;
    /**
     * 父id
     */
    @NotEmpty(message = "父id必须填")
    private String parentId;
    /**
     * 区域名称
     */
    @NotEmpty(message = "区域名称必须填")
    private String districtName;
    /**
     * 区域编码
     */
    @NotEmpty(message = "区域编码必须填")
    private String districtCode;
    /**
     * 排序号
     */
    @NotNull(message = "排序号必须填")
    private Integer orderId;
}
