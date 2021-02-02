package org.happykit.happyboot.security.constants;

/**
 * 验证码相关常量
 *
 * @author shaoqiang
 * @version 1.0
 * @date 2020/7/22 14:53
 */
public class CaptchaConstant {
    /**
     * 验证码前缀key
     */
    public static final String CAPTCHA_PRE = "CAPTCHA:";
    public static final String CAPTCHA_ID = "captchaId";
    public static final String CAPTCHA_CODE = "captchaCode";
    /**
     * 验证码有效时间为3分钟
     */
    public static int CAPTCHA_EXPIRES = 3 * 60;
}
