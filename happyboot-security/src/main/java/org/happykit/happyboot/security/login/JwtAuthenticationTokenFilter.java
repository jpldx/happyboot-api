package org.happykit.happyboot.security.login;

import com.google.gson.Gson;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * JWT 凭证验证过滤器
 *
 * @author shaoqiang
 * @version 1.0 2020/6/8
 */
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private TokenProperties tokenProperties;

    private StringRedisTemplate redisTemplate;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, TokenProperties tokenProperties, StringRedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenProperties = tokenProperties;
        this.redisTemplate = redisTemplate;
    }

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(tokenProperties.getAuthorization());
        if (StringUtils.isBlank(header)) {
            chain.doFilter(request, response);
            return;
        }
//		if(StringUtils.)

        SecurityUserDetails userDetails = getSecurityUserDetails(header);
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }


    private SecurityUserDetails getSecurityUserDetails(String header) {
        // 用户名
        String username = null;
        SecurityUserDetails userDetails = null;
        // 使用redis存储token

        // redis
        String v = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_PRE + header);
        if (StringUtils.isBlank(v)) {
            return null;
        }
        userDetails = new Gson().fromJson(v, SecurityUserDetails.class);

        username = userDetails.getUsername();

        // 若未保存登录状态重新设置失效时间
        redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, header, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + header, v, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);


        return userDetails;
    }
}
