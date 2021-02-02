package org.happykit.happyboot.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.happykit.happyboot.constant.AdminConstant;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.enums.StatusEnum;
import org.happykit.happyboot.sys.enums.ModuleEnum;
import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.happykit.happyboot.sys.model.entity.SysRoleDO;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.service.SysPermissionService;
import org.happykit.happyboot.sys.service.SysRoleService;
import org.happykit.happyboot.sys.service.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 初始化超级管理员的监听器
 *
 * @author shaoqiang
 * @version 1.0 2020/6/10
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {
    private static Pattern CONTROLLER_PATTERN = Pattern.compile("org.happykit.happyboot.");
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initRole();
        initUser();
//		initApi();
    }

    /**
     * 初始化角色
     */
    private void initRole() {
        LambdaQueryWrapper<SysRoleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleDO::getAuthorityName, AdminConstant.ROLE_ADMIN);
        SysRoleDO sysRole = sysRoleService.getOne(lambdaQueryWrapper);
        if (sysRole == null) {
            SysRoleDO entity = new SysRoleDO();
            entity.setRoleName("系统管理员");
            entity.setAuthorityName(AdminConstant.ROLE_ADMIN);
            entity.setSys(true);
            entity.setStatus(StatusEnum.ENABLE.getCode());
            sysRoleService.save(entity);
        }
    }

    /**
     * 初始化用户
     */
    private void initUser() {
        SysUserDO sysUser = sysUserService.getByUsername(AdminConstant.ADMIN_ACCOUNT);
        if (sysUser == null) {
            SysUserDO entity = new SysUserDO();
            entity.setUsername(AdminConstant.ADMIN_ACCOUNT);
            entity.setPassword(passwordEncoder.encode(AdminConstant.ADMIN_PASSWORD));
            entity.setStatus(StatusEnum.ENABLE.getCode());
            sysUserService.save(entity);
        }
    }

    /**
     * 初始化api接口
     */
    private void initApi() {
        Map<String, List<String>> resourceMap = new HashMap<>();
        Set<String> controllerSet = new HashSet<>();

        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        // 1.先循环获取controller
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            Matcher matcher = CONTROLLER_PATTERN.matcher(method.getBeanType().getName());
            if (matcher.find()) {
                controllerSet.add(method.getBeanType().getName());
            }
        }

        // 2.遍历controller，获取url集合
        for (String c : controllerSet) {
            List<String> urls = new ArrayList<>();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
                RequestMappingInfo info = m.getKey();
                HandlerMethod method = m.getValue();

                if (c.equals(method.getBeanType().getName())) {
                    // 获取url与类和方法的对应信息
                    Set<String> patterns = info.getPatternsCondition().getPatterns();
                    Set<RequestMethod> requestMethods = info.getMethodsCondition().getMethods();
                    String requestMethodName = "";
                    for (RequestMethod requestMethod : requestMethods) {
                        requestMethodName = requestMethod.toString();
                    }
                    for (String url : patterns) {
                        //把结果存入静态变量中程序运行一次次方法之后就不用再次请求次方法
                        urls.add(requestMethodName + url);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(urls)) {
                resourceMap.put(c, urls);
            }
        }

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String key = ite.next();
            LambdaQueryWrapper<SysPermissionDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SysPermissionDO::getModule, ModuleEnum.API.getCode());
            lambdaQueryWrapper.eq(SysPermissionDO::getName, key);
            List<SysPermissionDO> list = sysPermissionService.list(lambdaQueryWrapper);
            SysPermissionDO entity = null;
            if (CollectionUtils.isEmpty(list)) {
                entity = new SysPermissionDO();
                entity.setParentId(SysConstant.ROOT_PARENT_ID_STR);
                entity.setName(key);
                entity.setModule(ModuleEnum.API.getCode());
                sysPermissionService.save(entity);
            } else {
                entity = list.get(0);
            }
            List<String> urls = resourceMap.get(key);
            for (String url : urls) {
                LambdaQueryWrapper<SysPermissionDO> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                lambdaQueryWrapper2.eq(SysPermissionDO::getModule, ModuleEnum.API.getCode());
                lambdaQueryWrapper2.eq(SysPermissionDO::getPath, url);
                List<SysPermissionDO> list2 = sysPermissionService.list(lambdaQueryWrapper2);
                if (CollectionUtils.isEmpty(list2)) {
                    SysPermissionDO entity2 = new SysPermissionDO();
                    entity2.setParentId(entity.getId());
                    entity2.setName(url);
                    entity2.setPath(url);
                    entity2.setModule(ModuleEnum.API.getCode());
                    sysPermissionService.save(entity2);
                }
            }
        }
    }
}
