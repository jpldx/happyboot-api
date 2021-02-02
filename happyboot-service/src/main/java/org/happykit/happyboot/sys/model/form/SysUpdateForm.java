package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交类
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
@Data
public class SysUpdateForm implements Serializable {

    /**
     * 更新内容
     */
    private String updateContent;
    /**
     * 文件地址
     */
    private String blobUrl;
    /**
     * 文件版本号
     */
    private String blobVersion;
}