package org.happykit.happyboot.security.login;

import org.apache.commons.lang3.StringUtils;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.util.JwtUtils;
import org.happykit.happyboot.sys.enums.AuthTypeEnum;
import org.happykit.happyboot.sys.facade.SysAuthFacade;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.service.SysRoleService;
import org.happykit.happyboot.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户身份认证
 *
 * @author shaoqiang
 * @version 1.0 2020/3/6
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAuthFacade sysAuthFacade;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名密码不符");
        }
        SysUserDO user = sysUserService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名密码不符");
        }

        String userId = user.getId();
        String userType = user.getUserType();
        String mainAccountId = SecurityConstant.USER_TYPE_0.equals(userType) ? userId : null;

        List<String> roles =
                sysRoleService.listAuthorityNamesByUserIdAndAuthType(userId, AuthTypeEnum.VISIBLE.getCode());
        List<String> permissions;
        if (sysAuthFacade.checkAdminByUsername(username)) {
            permissions = sysAuthFacade.listAdminApis();
            roles = sysAuthFacade.listAdminRoles();
        } else {
            permissions = sysAuthFacade.listVisibleApisByUserId(userId);
        }

        // 生成JWT
        Map<String, String> payload = new HashMap<>();
        payload.put(JwtUtils.CLAIM_USER_ID, userId);
        payload.put(JwtUtils.CLAIM_USER_NAME, username);
        payload.put(JwtUtils.CLAIM_USER_TYPE, userType);
        payload.put(JwtUtils.CLAIM_MAIN_ACCOUNT_ID, mainAccountId);

        String token = jwtUtils.create(payload);

        return new SecurityUserDetails(
                userId,
                mainAccountId,
                userType,
                username,
                user.getPassword(),
                user.getDeptId(),
                user.getStatus(),
                permissions,
                roles,
                token);
    }
}
