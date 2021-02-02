package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shaoqiang
 * @version 1.0 2020/03/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_file")
public class SysFileDO extends BaseEntity {
    /**
     * 文件名称（原始文件名）
     */
    private String fileName;
    /**
     * 保存的文件名
     */
    private String fileAliasName;
    /**
     * 保存路径
     */
    private String filePath;
    /**
     * 文件类型
     */
    private String mime;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件md5值
     */
    private String md5;
    /**
     * 文件sha1值
     */
    private String sha1;

    /* 以下是表中没有的字段 */
    /**
     * 上传人名称
     */
    @TableField(exist = false)
    private String createUserName;
}
