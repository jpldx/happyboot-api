package org.happykit.happyboot.op.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import org.happykit.happyboot.model.model.ImageArray;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("op_banner")
public class OpBannerDO extends BaseEntity {
    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 链接
     */
    private String linkUrl;
    /**
     * 图片
     */
    private String image;
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
    /**
     * 图片数组
     */
    @TableField(exist = false)
    private ImageArray imageArr;
}
