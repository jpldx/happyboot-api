package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/31
 */
@Slf4j
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController extends BaseController {

    private final CaptchaService captchaService;

    public KaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * 生成验证码
     *
     * @return
     * @throws IOException
     */
    @GetMapping
    public R get() throws IOException, FontFormatException {
        return success(captchaService.generateCaptcha());
    }

    // /**
    // * 校对验证码
    // *
    // * @return
    // * @throws IOException
    // */
    // @PostMapping
    // public R verify(@RequestBody @Validated CaptchaForm form) {
    // return success(captchaService.verify(form.getId(), form.getCode()));
    // }
}
