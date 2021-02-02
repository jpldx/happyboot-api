package org.happykit.happyboot.sys.service;

import org.happykit.happyboot.sys.model.dto.CaptchaDTO;

import java.awt.*;
import java.io.IOException;

/**
 * @author shaoqiang
 * @version 1.0 2020/4/2
 */
public interface CaptchaService {
    /**
     * 生成验证码
     *
     * @return
     * @throws IOException
     */
    CaptchaDTO generateCaptcha() throws IOException, FontFormatException;

    /**
     * 校验验证码是否正确
     *
     * @param id
     * @param captcha
     * @return
     */
    boolean verify(String id, String captcha);
}
