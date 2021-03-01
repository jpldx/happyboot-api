package org.happykit.happyboot.security.login;

import lombok.SneakyThrows;
import org.happykit.happyboot.base.R;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.util.ResponseUtils;
import org.happykit.happyboot.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/3/1
 */
@Component
public class JwtLogoutHandler implements LogoutHandler {

    @Autowired
    private SecurityCacheService securityCacheService;
    @Autowired
    private TokenProperties tokenProperties;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(tokenProperties.getAuthorization());
        if (StringUtils.isNotBlank(token)) {
            //
//            SecurityContextHolder.setContext();
//            // 1. 删除缓存
//            securityCacheService.removeUserDetails(token);
            // 2. token 拉入黑名单
            securityCacheService.setTokenToBlackList(token);
        }
        ResponseUtils.out(response, R.ok(null, "注销成功"));
    }
}
