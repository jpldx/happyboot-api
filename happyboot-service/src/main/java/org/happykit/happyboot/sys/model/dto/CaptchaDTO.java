package org.happykit.happyboot.sys.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 验证码
 *
 * @author shaoqiang
 * @version 1.0 2020/4/2
 */
@Data
public class CaptchaDTO implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 验证码base64图片字符串
     */
    private String base64ImgStr;
}
