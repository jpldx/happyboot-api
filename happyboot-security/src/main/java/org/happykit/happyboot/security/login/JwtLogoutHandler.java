package org.happykit.happyboot.security.login;

import lombok.SneakyThrows;
import org.happykit.happyboot.base.R;
import org.happykit.happyboot.enums.AppPlatformEnum;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.login.service.SecurityLogService;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.util.ResponseUtils;
import org.happykit.happyboot.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private SecurityLogService securityLogService;
    @Autowired
    private TokenProperties tokenProperties;

    //    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(tokenProperties.getAuthorization());
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        if (StringUtils.isNotBlank(token)) {
            securityLogService.saveSecurityLog(request,
                    userDetails.getId(), userDetails.getUsername(),
                    SecurityConstant.SecurityOperationEnum.LOGOUT,
                    AppPlatformEnum.PC,
                    token);
            // 1. 清除用户信息上下文
            // 2. 删除用户信息缓存
            // 3. token 拉入黑名单
//            SecurityContextHolder.setContext();
//            securityCacheService.removeUserDetails(token);
            securityCacheService.setTokenToBlackList(token);
        }
        try {
            ResponseUtils.out(response, R.ok(null, "注销成功"));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
