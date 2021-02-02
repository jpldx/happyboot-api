package org.happykit.happyboot.sys.acpect;

import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.annotation.CheckPermission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 操作权限注解切面
 *
 * @author chen.xudong
 * @date 2020/6/29
 * @see CheckPermission
 */
@Slf4j
@Aspect
@Component
public class CheckPermissionAspect {
    @Autowired
    private SysSecurityUtils sysSecurityUtils;

    @Pointcut("@annotation(org.happykit.happyboot.annotation.CheckPermission)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void checkPermission(JoinPoint point) throws Throwable {
//        Signature signature = point.getSignature();
//        Method method = ((MethodSignature) signature).getMethod();
//        CheckPermission annotation = method.getAnnotation(CheckPermission.class);
//        String currentDeptId = sysSecurityUtils.getCurrentObjDeptId();
//        if (StringUtils.isBlank(currentDeptId)) {
//            throw new SysException(SysExceptionConstant.AUTH_ERROR_DEPT);
//        }
        log.info("CheckPermissionAspect.checkPermission()...切面生效");
        String deptId = sysSecurityUtils.getCurrentUserDeptId();
        if (StringUtils.isBlank(deptId)) {
            throw new SysException(SysExceptionConstant.AUTH_NOT_BIND_DEPT);
        }
    }
}
