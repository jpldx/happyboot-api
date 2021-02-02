package org.happykit.happyboot.security.login;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.constant.ResultCodeConstant;
import org.happykit.happyboot.security.properties.IgnoredUrlsProperties;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ClientId（客户端唯一标识）校验过滤器
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/15
 */
@Slf4j
@Component
public class SecurityClientIdFilter extends GenericFilterBean {

    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        log.info("在UsernamePasswordAuthenticationFilter前调用...");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestUrl = req.getRequestURI();
        String contextPath = req.getContextPath();
        if (StringUtils.isNotBlank(contextPath)) {
            requestUrl = requestUrl.replaceFirst(contextPath, "");
        }
        final String finalRequestUrl = requestUrl;

        String clientId = req.getHeader(tokenProperties.getClientId());
        List<String> ignoredClientIdUrls = ignoredUrlsProperties.getClientIdUrls();
//        log.info("ClientId 校验放行接口：" + ignoredClientIdUrls);

        PathMatcher pathMatcher = new AntPathMatcher();
        if (ignoredClientIdUrls.stream().anyMatch(url -> pathMatcher.match(url, finalRequestUrl))) {
            filterChain.doFilter(request, response);
            return;
        }
        if (StringUtils.isBlank(clientId)) {
            R<Object> r = new R<>();
            r.setCode(ResultCodeConstant.INVALID_CLIENT_ID);
            r.setData(null);
            r.setMsg("无效的客户端标识");
            ResponseUtils.out(resp, r);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
