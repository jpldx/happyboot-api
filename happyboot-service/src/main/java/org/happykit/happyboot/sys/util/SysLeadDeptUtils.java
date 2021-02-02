package org.happykit.happyboot.sys.util;

import org.happykit.happyboot.constant.CommonConstant;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.service.SysDeptObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 综治中心工具类
 *
 * @author chen.xudong
 * @date 2020/7/15
 */
@Component
public class SysLeadDeptUtils {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;
    @Autowired
    private SysDeptObjService sysDeptObjService;
    /**
     * 向上递归查询最近的综治中心部门
     *
     * @param currentDept 当前部门
     * @return
     */
    private SysDeptObjDO nearestLeadDept;

    /**
     * 判断是否是综治中心部门
     *
     * @param flag 部门标识 0 / 1
     * @return
     */
    public boolean isLeadDept(String flag) {
        return CommonConstant.LEAD_DEPT_FLAG.equals(flag);
    }

    /**
     * 判断当前部门是否是综治中心
     *
     * @return
     */
    public boolean isLeadDeptCurrent() {
        SysDeptObjDO currentDept = sysSecurityUtils.getCurrentObjDept();
        if (null == currentDept) {
            return false;
        }
        return CommonConstant.LEAD_DEPT_FLAG.equals(currentDept.getIsLeadDept());
    }

    public void setNearestLeadDeptNull() {
        this.nearestLeadDept = null;
    }

    public SysDeptObjDO findNearestLeadDept(SysDeptObjDO currentDept) {
        SysDeptObjDO parentDept = sysDeptObjService.getById(currentDept.getParentId());
        if (null == parentDept) {
            return null;
        }
        if (this.isLeadDept(parentDept.getIsLeadDept())) {
            nearestLeadDept = parentDept;
            return nearestLeadDept;
        }
        findNearestLeadDept(parentDept);
        return nearestLeadDept;
    }
}
