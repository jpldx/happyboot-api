package org.happykit.happyboot.security.login.service.impl;

import org.happykit.happyboot.common.service.openapi.baidu.map.service.ILocationService;
import org.happykit.happyboot.enums.AppPlatformEnum;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.repository.SecurityLogRepository;
import org.happykit.happyboot.security.login.service.SecurityLogService;
import org.happykit.happyboot.security.model.collections.SecurityLogCollection;
import org.happykit.happyboot.security.util.JwtUtils;
import org.happykit.happyboot.security.util.SecurityUtils;
import org.happykit.happyboot.util.DateUtils;
import org.happykit.happyboot.util.InternetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2021/3/2
 */
@Service
public class SecurityLogServiceImpl implements SecurityLogService {

    @Autowired
    private SecurityLogRepository securityLogRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private ILocationService locationService;

    @Override
    public void saveSecurityLog(HttpServletRequest request,
                                String userId,
                                String username,
                                SecurityConstant.SecurityOperationEnum securityOperation,
                                AppPlatformEnum appPlatform,
                                String token) {
        SecurityLogCollection securityLog = new SecurityLogCollection();
        String ip = InternetUtils.getIp(request);
        securityLog.setClientId(securityUtils.getClientId(request))
                .setUserId(userId)
                .setUsername(username)
                .setIp(ip)
                .setIpAddress(locationService.getAddressByIp(ip))
                .setOperationType(securityOperation.name())
                .setOperationPlatform(appPlatform.name())
                .setOperationTime(DateUtils.now())
                .setToken(token)
                .setTokenExpireTime(JwtUtils.decode(token).getExpiresAt())
                .setUa(InternetUtils.getUserAgent(request));
        securityLogRepository.insert(securityLog);
    }
}
