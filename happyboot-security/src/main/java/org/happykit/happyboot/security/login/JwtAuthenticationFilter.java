package org.happykit.happyboot.security.login;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.security.util.JwtUtils;
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

/**
 * JWT 凭证认证过滤器
 *
 * @author shaoqiang
 * @version 1.0 2020/6/8
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private TokenProperties tokenProperties;
    private SecurityCacheService securityService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   TokenProperties tokenProperties,

                                   SecurityCacheService securityService) {
        super(authenticationManager);
        this.tokenProperties = tokenProperties;
        this.securityService = securityService;
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(tokenProperties.getAuthorization());
        SecurityUserDetails userDetails = getSecurityUserDetails(header);
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }


    private SecurityUserDetails getSecurityUserDetails(String header) {
        if (StringUtils.isBlank(header)) {
            return null;
        }
        try {
            JwtUtils.verify(header);
        } catch (JWTVerificationException e) {
            return null;
        }
        if (securityService.isTokenInBlackList(header)) {
            return null;
        }
        // TODO 缓存没有从数据库取
        return securityService.getUserDetails(header);
    }
}
