package org.happykit.happyboot.security.login;

import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.service.UserService;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.service.SysUserRelService;
import org.happykit.happyboot.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRelService sysUserRelService;

    @Override
    public Object loginSuccess(SecurityUserDetails userDetails) {

        String userId = userDetails.getId();
        String userType = userDetails.getUserType();

        SysUserDO sysUser = sysUserService.getById(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("token", userDetails.getToken());
        map.put("userinfo", sysUser);
        // 关联的账号数量
        int userlistCount = SecurityConstant.USER_TYPE_1.equals(userType) ? 0 :
                sysUserRelService.getUserRelListByUserId(userId, userType).size();
        map.put("userlist_count", userlistCount);
        return map;
    }
}
