package org.happykit.happyboot.security.login;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.exceptions.LoginFailLimitException;
import org.happykit.happyboot.security.model.AuthenticationBean;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.util.IpUtils;
import org.happykit.happyboot.util.RSAUtils;
import org.happykit.happyboot.util.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 自定义json登录 默认的用户名/密码提取就是通过request中的getParameter来提取的,改用json方式
 *
 * @author shaoqiang
 * @version 1.0 2020/3/9
 */
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private boolean postOnly = true;

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenProperties tokenProperties;

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }


    protected CustomAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    /**
     * 请求登录
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 必须以post方式提交
        if (postOnly && !HttpMethod.POST.name().equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        UsernamePasswordAuthenticationToken authRequest = null;
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            // 允许对象忽略json中不存在的属性
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                AuthenticationBean authenticationBean = mapper.readValue(request.getInputStream(), AuthenticationBean.class);

                // 判断账号是否登录限制
                boolean limitFlag = isBlocked(authenticationBean.getUsername());
                if (limitFlag) {
                    logger.info("登录错误次数超过限制");

                    throw new AuthenticationServiceException("登录错误次数超过限制");
                }

                // 登录密码是否加密
                if (tokenProperties.getLpe()) {
                    String password = RSAUtils.decrypt(authenticationBean.getPassword(), tokenProperties.getLpeKey());
                    authenticationBean.setPassword(password);
                }

                authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUsername(), authenticationBean.getPassword(), null);
            } catch (Exception e) {
                throw new AuthenticationServiceException(e.getMessage());
            }
        } else {
            throw new AuthenticationServiceException("请求头类型不支持: " + request.getContentType());
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 登录成功
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityUserDetails userDetails = (SecurityUserDetails) authResult.getPrincipal();

        String username = userDetails.getUsername();
        String userId = userDetails.getId();
        // 登陆成功生成token
        String token = userDetails.getToken();
        // 使用redis存储token

        // 单设备登录 之前的token失效
        if (tokenProperties.getSdl()) {
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);
            if (StringUtils.isNotBlank(oldToken)) {
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
        }
        // 将用户信息和token存入redis
        redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(userDetails), tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);

        // 更新登录信息
        Object loginInfo = userService.loginSuccess(userDetails, token, IpUtils.getIpAddress(request));
        ResponseUtils.out(response, R.ok(loginInfo));
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            ResponseUtils.out(response, R.failed("用户名或密码错误"));
        } else if (e instanceof DisabledException) {
            ResponseUtils.out(response, R.failed("账户被禁用，请联系管理员"));
        } else if (e instanceof LoginFailLimitException) {
            ResponseUtils.out(response, R.failed(e.getMessage()));
        } else {
            ResponseUtils.out(response, R.failed(e.getMessage()));
        }
    }


    public boolean isBlocked(String username) {
        // 登录错误的用户存储标识
        String flagKey = SecurityConstant.LOGIN_FAIL_FLAG + username;
        String value = redisTemplate.opsForValue().get(flagKey);
        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        // 超过限制次数
        return StringUtils.isNotBlank(value);
    }


}
