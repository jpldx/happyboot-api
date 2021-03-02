package org.happykit.happyboot.security.login;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.happykit.happyboot.base.R;
import org.happykit.happyboot.enums.AppPlatformEnum;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.login.service.SecurityLogService;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.security.util.JwtUtils;
import org.happykit.happyboot.util.ResponseUtils;
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
@Slf4j
@Component
public class JwtLogoutHandler implements LogoutHandler {

    @Autowired
    private SecurityCacheService securityCacheService;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private TokenProperties tokenProperties;

    // 记录用户安全日志
    // 清除用户信息上下文
    // 删除用户信息缓存
    // token 拉入黑名单
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(tokenProperties.getAuthorization());
        if (StringUtils.isNotBlank(token)) {
            DecodedJWT verify = null;
            try {
                verify = JwtUtils.verify(token);
            } catch (JWTVerificationException e) {
                log.error("用户注销JWT校验失败，未做任何处理：" + e.getMessage());
            }
            if (verify != null) {
                securityLogService.saveSecurityLog(request,
                        verify.getClaim(JwtUtils.CLAIM_USER_ID).asString(),
                        verify.getClaim(JwtUtils.CLAIM_USER_NAME).asString(),
                        SecurityConstant.SecurityOperationEnum.LOGOUT,
                        AppPlatformEnum.PC,
                        null);

                securityCacheService.setTokenToBlackList(token);
            }
        }
        try {
            ResponseUtils.out(response, R.ok(null, "注销成功"));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
