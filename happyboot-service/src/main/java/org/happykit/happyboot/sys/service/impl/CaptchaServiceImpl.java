package org.happykit.happyboot.sys.service.impl;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.happykit.happyboot.sys.extension.CustomArithmeticCaptcha;
import org.happykit.happyboot.util.IdUtils;
import org.happykit.happyboot.sys.model.dto.CaptchaDTO;
import org.happykit.happyboot.sys.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;

/**
 * @author shaoqiang
 * @version 1.0 2020/4/2
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);
    /**
     * 验证码redis前缀
     */
    private static final String CAPTCHA_PREFIX = "captcha:";
    /**
     * 验证码有效时间为3分钟
     */
    private static int CAPTCHA_EXPIRES = 3 * 60;
    private final DefaultKaptcha defaultKaptcha;

    private final RedisTemplate<String, String> redisTemplate;

    public CaptchaServiceImpl(DefaultKaptcha defaultKaptcha, RedisTemplate<String, String> redisTemplate) {
        this.defaultKaptcha = defaultKaptcha;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public CaptchaDTO generateCaptcha() throws IOException, FontFormatException {
        // 生成文字验证码
        // String text = defaultKaptcha.createText();
        // String uuid = IdGenerator.simpleUUID();
        //
        // logger.info("验证码" + text + "," + uuid);
        //
        // // 将验证码以<key,value>形式缓存到redis
        // redisTemplate.opsForValue().set(uuid, text, CAPTCHA_EXPIRES, TimeUnit.SECONDS);
        //
        // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // BufferedImage image = defaultKaptcha.createImage(text);
        // ImageIO.write(image, "jpg", outputStream);
        // String base64ImgStr = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        //
        // CaptchaDTO dto = new CaptchaDTO();
        // dto.setId(uuid);
        // dto.setBase64ImgStr(base64ImgStr);

//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        CustomArithmeticCaptcha captcha = new CustomArithmeticCaptcha();
        captcha.setFont(Captcha.FONT_6);
        String captchaId = IdUtils.simpleUUID();
        String captchaCode = captcha.text();
        // 验证码redis缓存
        redisTemplate.opsForValue().set(captchaId, captchaCode, CAPTCHA_EXPIRES, TimeUnit.SECONDS);
        CaptchaDTO dto = new CaptchaDTO();
        dto.setId(captchaId);
        dto.setBase64ImgStr(captcha.toBase64(""));
        return dto;
    }

    @Override
    public boolean verify(String id, String captcha) {
        String cacheCaptchaValue = redisTemplate.opsForValue().get(id);
        if (captcha.equals(cacheCaptchaValue)) {
            // 验证成功后删除
            redisTemplate.delete(id);
            return true;
        }
        return false;
    }
}
