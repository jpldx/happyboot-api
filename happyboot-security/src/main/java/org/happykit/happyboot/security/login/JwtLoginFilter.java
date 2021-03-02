package org.happykit.happyboot.security.login;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.happykit.happyboot.common.service.openapi.baidu.map.service.ILocationService;
import org.happykit.happyboot.enums.AppPlatformEnum;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.exceptions.LoginFailedLimitException;
import org.happykit.happyboot.security.login.repository.SecurityLogRepository;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.login.service.SecurityLogService;
import org.happykit.happyboot.security.login.service.UserService;
import org.happykit.happyboot.security.model.AuthenticationBean;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.security.util.SecurityUtils;
import org.happykit.happyboot.util.InternetUtils;
import org.happykit.happyboot.util.RSAUtils;
import org.happykit.happyboot.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
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


/**
 * 用户登录认证过滤器
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/3/9
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtLoginFilter.class);

    private boolean postOnly = true;

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private SecurityCacheService securityCacheService;

    public JwtLoginFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }


    protected JwtLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected JwtLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
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
        // 提交方法校验
        String requestMethod = request.getMethod();
        String clientId = request.getHeader(tokenProperties.getClientId());
        if (!HttpMethod.POST.name().equals(requestMethod)) {
            throw new AuthenticationServiceException("Authentication method not supported：" + requestMethod);
        }
        UsernamePasswordAuthenticationToken authRequest = null;
        ObjectMapper mapper = new ObjectMapper();
        // 允许对象忽略json中不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            AuthenticationBean authenticationBean = mapper.readValue(request.getInputStream(), AuthenticationBean.class);

            // 判断客户端是否登录限制
            if (isLoginFailedLimited(clientId)) {
                throw new LoginFailedLimitException("登录失败次数过多，请" + tokenProperties.getLoginFailedLimitRecoverTime() + "分钟后再试");
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

        String userId = userDetails.getId();
        String username = userDetails.getUsername();
        String token = userDetails.getToken();

        // 缓存用户信息
        securityCacheService.setUserDetails(userDetails);

        // TODO 删除登录失败缓存计数

        // 记录安全日志 TODO 队列处理
        securityLogService.saveSecurityLog(request,
                userId, username,
                SecurityConstant.SecurityOperationEnum.LOGIN,
                AppPlatformEnum.PC,
                token);
        // 返回认证信息
        Object loginInfo = userService.loginSuccess(userDetails);
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
        if (e instanceof AuthenticationServiceException) {
            ResponseUtils.out(response, R.failed(e.getMessage()));
        } else if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            ResponseUtils.out(response, R.failed("用户名或密码错误"));
        } else if (e instanceof DisabledException) {
            ResponseUtils.out(response, R.failed("账号被禁用，请联系管理员"));
        } else if (e instanceof LoginFailedLimitException) {
            ResponseUtils.out(response, R.failed(e.getMessage()));
        } else {
            ResponseUtils.out(response, R.failed(e.getMessage()));
        }
    }


    /**
     * 判断客户端是否登录失败限制
     *
     * @param clientId 客户端id
     * @return
     */
    public boolean isLoginFailedLimited(String clientId) {
        String times = redisTemplate.opsForValue().get(SecurityConstant.LOGIN_FAILED_LIMIT + clientId);
        return (times != null && Integer.parseInt(times) >= tokenProperties.getLoginFailedLimit());
    }

}
