package org.happykit.happyboot.security.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.happykit.happyboot.sys.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 动态权限实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/6/8
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    public List<String> list() {
        LambdaQueryWrapper<SysPermissionDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysPermissionDO::getType, SysConstant.PERMISSION_KEY);
        Set<String> set = sysPermissionService.list(lambdaQueryWrapper).stream().map(s -> s.getPath()).filter(f -> StringUtils.isNotBlank(f)).collect(Collectors.toSet());
        List<String> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }
}
