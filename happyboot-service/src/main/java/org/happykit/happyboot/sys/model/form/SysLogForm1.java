package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysLogForm1 implements Serializable {
    /**
     *
     */
    private Long costTime;
    /**
     *
     */
    private String ip;
    /**
     *
     */
    private String requestParam;
    /**
     *
     */
    private String requestType;
    /**
     *
     */
    private String requestUrl;
    /**
     *
     */
    private String method;
    /**
     *
     */
    private String logContent;
}