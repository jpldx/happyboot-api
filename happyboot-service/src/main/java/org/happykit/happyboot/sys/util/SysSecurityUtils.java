package org.happykit.happyboot.sys.util;

import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.model.base.BaseAuthModel;
import org.happykit.happyboot.security.util.SecurityUtils;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.service.SysDeptObjService;
import org.happykit.happyboot.sys.service.SysDeptRegionService;
import org.happykit.happyboot.sys.service.SysSubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统Security帮助类,查询用户所属部门信息
 *
 * @author shaoqiang
 * @version 1.0 2020/6/18
 */
@Service
public class SysSecurityUtils extends SecurityUtils {
    /**
     * 用户所属部门ID前缀
     */
    static final String OBJ_DEPT_ID = "OBJ_DEPT_ID:";
    @Autowired
    private SysDeptObjService sysDeptObjService;
    @Autowired
    private SysSubjectService sysSubjectService;
    @Autowired
    private SysDeptRegionService sysDeptRegionService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 返回当前用户关联的部门id集合
     *
     * @return
     */
    public Set<String> getCurrentObjDeptIdList() {
        return sysDeptObjService.listSysDeptObjsByUserId(getCurrentUserId()).stream().map(m -> m.getId())
                .collect(Collectors.toSet());
    }

    /**
     * 返回当前用户关联的部门集合
     *
     * @return
     */
    public List<SysDeptObjDO> getCurrentObjDeptList() {
        return sysDeptObjService.listSysDeptObjsByUserId(getCurrentUserId());
    }

    // /**
    // * 返回当前用户关联的部门code集合
    // *
    // * @return
    // */
    // public Set<String> getCurrentObjDeptCodeList() {
    //
    // return new HashSet<>();
    // }

    /**
     * 返回当前用户关联的区域id集合
     *
     * @return
     */
    public Set<String> getCurrentDeptRegionIdList() {
        return sysDeptRegionService.listSysDeptRegionsByUserId(getCurrentUserId()).stream().map(m -> m.getId())
                .collect(Collectors.toSet());
    }

    /**
     * 返回当前用户关联的单位id集合
     *
     * @return
     */
    public Set<String> getCurrentObjIdList() {
        List<SysSubjectDO> sysObjList = sysSubjectService.listSysObjsByUserId(getCurrentUserId());
        return sysObjList.stream().map(m -> m.getId()).collect(Collectors.toSet());
    }

    public SysDeptObjDO getCurrentObjDept() {
//        String deptId = redisTemplate.opsForValue().get(OBJ_DEPT_ID + getCurrentUserToken());
        String deptId = this.getCurrentObjDeptId();
        if (StringUtils.isBlank(deptId)) {
            return null;
        }
        return sysDeptObjService.getById(deptId);
    }

    /**
     * 获取当前登录用户部门信息
     *
     * @return
     */
    public SysDeptObjDO getCurrentUserDept() {
        String deptId = this.getCurrentUserDeptId();
        if (StringUtils.isBlank(deptId)) {
            return null;
        }
        return sysDeptObjService.getById(deptId);
    }

    /**
     * 返回当前用户所处的内部部门ID
     *
     * @return
     */
    public String getCurrentObjDeptId() {
        return redisTemplate.opsForValue().get(OBJ_DEPT_ID + getCurrentUserToken());
    }

    /**
     * 设置当前登录用户所属的内部部门ID
     *
     * @param deptId
     */
    public void setCurrentObjDeptId(String deptId) {
        redisTemplate.opsForValue().set(OBJ_DEPT_ID + getCurrentUserToken(), deptId.toString());
    }

    /**
     * 清空当前登录用户所属的内部部门ID
     *
     * @return
     */
    public void clearCurrentObjDeptId() {
        redisTemplate.delete(OBJ_DEPT_ID + getCurrentUserToken());
    }

    /**
     * 校验操作权限
     *
     * @param authModel
     */
    @Deprecated
    public void checkPermission(BaseAuthModel authModel) {
        SysDeptObjDO dept = this.getCurrentObjDept();
        if (null == dept) {
            throw new SysException(SysExceptionConstant.AUTH_ERROR_DEPT);
        }
        if (null == authModel) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_RECORD);
        }
        if (!dept.getId().equals(authModel.getDeptId())) {
            throw new SysException(SysExceptionConstant.AUTH_ERROR);
        }
    }

    /**
     * 获取当前登录用户对象（从缓存中获取）
     *
     * @return
     */
//    public SecurityUserDetails getCurrentUserDetailsCache() {
//        String token = this.getCurrentUserToken();
//        if (null == token) {
//            return null;
//        }
//        String cacheValue = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_PRE + token);
//        if (null == cacheValue) {
//            return null;
//        }
//        return JSON.parseObject(cacheValue, SecurityUserDetails.class);
//    }
}
