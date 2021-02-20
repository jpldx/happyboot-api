//package org.happykit.happyboot.security.validate;
//
//
//import com.baomidou.mybatisplus.extension.api.R;
//import org.happykit.happyboot.security.constants.CaptchaConstant;
//import org.happykit.happyboot.security.properties.CaptchaProperties;
//import org.happykit.happyboot.util.ResponseUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 验证码过滤器
// *
// * @author shaoqiang
// * @version 1.0
// * @description OncePerRequestFilter 是spring的一个工具类，保证过滤器每次只被调用一次;InitializingBean
// * 实现它的目的是，在其他参数组装完毕之后初始化要拦截请求的值
// * @date 2020/7/17 13:45
// */
//@Component
//public class CaptchaValidateFilter extends OncePerRequestFilter implements InitializingBean {
//
//    private static final Logger logger = LoggerFactory.getLogger(CaptchaValidateFilter.class);
//    /**
//     * spring工具类可用来做url请求匹配
//     */
//    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
//    @Autowired
//    private CaptchaProperties captchaProperties;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    /**
//     * 所有图形验证码要拦截的请求
//     */
//    private Set<String> urls = new HashSet<>();
//
//    @Override
//    public void afterPropertiesSet() throws ServletException {
//        super.afterPropertiesSet();
//        if (!CollectionUtils.isEmpty(captchaProperties.getImage())) {
//            urls.addAll(captchaProperties.getImage());
//        }
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        boolean flag = false;
//        for (String url : captchaProperties.getImage()) {
//            if (antPathMatcher.match(url, request.getServletPath())) {
//                flag = true;
//                break;
//            }
//        }
//
//        if (flag) {
//
//            String captchaId = request.getParameter(CaptchaConstant.CAPTCHA_ID);
//            String code = request.getParameter(CaptchaConstant.CAPTCHA_CODE);
//            if (StringUtils.isBlank(captchaId) || StringUtils.isBlank(code)) {
//                ResponseUtils.out(response, R.failed("请传入图形验证码所需参数captchaId或captchaCode"));
//                return;
//            }
//            String redisCode = stringRedisTemplate.opsForValue().get(CaptchaConstant.CAPTCHA_PRE + captchaId);
//            if (StringUtils.isBlank(redisCode)) {
//                ResponseUtils.out(response, R.failed("验证码已过期，请重新获取"));
//                return;
//
//            }
//            if (!redisCode.toLowerCase().equals(code.toLowerCase())) {
//                logger.debug("验证码错误：code:" + code + "，redisCode:" + redisCode);
//                ResponseUtils.out(response, R.failed("验证码错误"));
//                return;
//
//            }
//            // 已验证清除key
//            stringRedisTemplate.delete(CaptchaConstant.CAPTCHA_PRE + captchaId);
//            // 验证成功 放行
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // 无需验证 放行
//        filterChain.doFilter(request, response);
//    }
//}
