package org.happykit.happyboot.security.login;

import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.sys.enums.AuthTypeEnum;
import org.happykit.happyboot.sys.facade.SysAuthFacade;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.service.SysRoleService;
import org.happykit.happyboot.sys.service.SysUserService;
import org.happykit.happyboot.util.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * spring security 身份认证实现类，改变用户信息来源
 *
 * @author shaoqiang
 * @version 1.0 2020/3/6
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsServiceImpl.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysAuthFacade sysAuthFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名密码不符");
        }

        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(flagKey);
        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        if (StringUtils.isNotBlank(value)) {
            // 超过限制次数
            throw new InternalAuthenticationServiceException("登录错误次数超过限制，请" + timeRest + "分钟后再试");
        }

        SysUserDO user = sysUserService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名密码不符");
        }
        List<String> roles =
                sysRoleService.listAuthorityNamesByUserIdAndAuthType(user.getId(), AuthTypeEnum.VISIBLE.getCode());
        List<String> permissions;
        if (sysAuthFacade.checkAdminByUsername(user.getUsername())) {
            permissions = sysAuthFacade.listAdminApis();
            roles = sysAuthFacade.listAdminRoles();
        } else {
            permissions = sysAuthFacade.listVisibleApisByUserId(user.getId());
        }
        String token = IdUtils.simpleUUID();
        return new SecurityUserDetails(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getDeptId(),
                user.getUserType(),
                user.getStatus(),
                permissions,
                roles,
                token);
    }

}
