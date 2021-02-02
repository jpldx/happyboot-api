package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysRefUserDeptRegionDO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 人员与区域部门关系表 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysRefUserDeptRegionService extends IService<SysRefUserDeptRegionDO> {

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysRefUserDeptRegion(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysRefUserDeptRegion(Long... ids);

    /**
     * 通过用户id删除关联
     *
     * @param userId
     * @return
     */
    boolean deleteByUserId(@NotNull String userId);

    /**
     * 通过区域id删除关联
     *
     * @param regionId
     * @return
     */
    boolean deleteByRegionId(@NotNull Long regionId);

    /**
     * 查询用户关联的区域id列表
     *
     * @param userId 用户id
     * @return
     */
    List<String> listRegionIdsByUserId(Long userId);
}
