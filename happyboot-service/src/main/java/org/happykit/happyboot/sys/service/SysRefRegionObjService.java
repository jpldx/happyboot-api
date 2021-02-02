package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysRefRegionObjDO;

import javax.validation.constraints.NotNull;

/**
 * 对象区域关系表 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysRefRegionObjService extends IService<SysRefRegionObjDO> {
    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysRefRegionObj(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysRefRegionObj(Long... ids);

    /**
     * 通过单位id删除
     *
     * @param objId
     * @return
     */
    Boolean deleteByObjId(@NotNull Long objId);
}
