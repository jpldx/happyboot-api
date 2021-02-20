package org.happykit.happyboot.security.validate;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.security.properties.CaptchaProperties;
import org.happykit.happyboot.util.HttpUtils;
import org.happykit.happyboot.util.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.SortedMap;

/**
 * 图形验证码过滤器
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
@Component
public class CaptchaValidateFilter extends OncePerRequestFilter {
    static final String CAPTCHA_ID = "captchaId";
    static final String CAPTCHA_CODE = "captchaCode";
    private static final Logger logger = LoggerFactory.getLogger(CaptchaValidateFilter.class);
    @Autowired
    private CaptchaProperties captchaProperties;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 判断URL是否需要验证
        Boolean flag = false;
        String contextPath = request.getContextPath();
        String requestUrl = request.getRequestURI();
        if (StringUtils.isNotBlank(contextPath)) {
            requestUrl = requestUrl.replaceFirst(contextPath, "");
        }
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : captchaProperties.getImage()) {
            if (pathMatcher.match(url, requestUrl)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            // 防止流读取一次后就没有了, 所以需要将流继续写出去
            HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            // 获取全部参数(包括URL和body上的)
            SortedMap<String, String> allParams = HttpUtils.getAllParams(requestWrapper);

            String captchaId = allParams.get(CAPTCHA_ID);
            String code = allParams.get(CAPTCHA_CODE);
            if (StringUtils.isBlank(captchaId) || StringUtils.isBlank(code)) {
                ResponseUtils.out(response, R.failed("请传入图形验证码所需参数kaptchaId或kaptchaValue"));
                return;
            }
            String redisCode = redisTemplate.opsForValue().get(captchaId);
            if (StringUtils.isBlank(redisCode)) {
                ResponseUtils.out(response, R.failed("验证码已过期，请重新获取"));
                return;
            }

            if (!redisCode.toLowerCase().equals(code.toLowerCase())) {
                logger.info("验证码错误：code:" + code + "，redisCode:" + redisCode);
                ResponseUtils.out(response, R.failed("图形验证码输入错误"));
                return;
            }
            // 已验证清除key
            redisTemplate.delete(captchaId);
            // 验证成功 放行
            chain.doFilter(requestWrapper, response);
            return;
        }
        // 无需验证 放行
        chain.doFilter(request, response);
    }
}
