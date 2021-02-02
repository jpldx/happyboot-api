package org.happykit.happyboot.op.model.form;

import org.happykit.happyboot.model.model.ImageArray;
import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 轮播图表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
public class OpBannerForm implements Serializable {
    /**
     * 轮播图ID
     */
    @NotBlank(groups = Update.class)
    private String id;
    /**
     * 链接
     */
    private String linkUrl;
    /**
     * 图片数组
     */
    private ImageArray imageArr = new ImageArray();
    /**
     * 描述
     */
    private String des;
    /**
     * 是否启用
     */
    private String enable;
    /**
     * 排序号
     */
    private Integer sortOrder;
}