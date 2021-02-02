package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/03/30
 */
@Data
public class SysFileForm implements Serializable {
    /**
     * 文件名称（上传的文件名）
     */
    private String fileName;
    /**
     * 保存的文件名
     */
    private String fileAliasName;
    /**
     * 路径
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
}