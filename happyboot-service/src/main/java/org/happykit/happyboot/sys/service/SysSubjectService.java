package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.model.form.SysSubjectForm;
import org.happykit.happyboot.sys.model.query.SysSubjectPageQueryParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 主体
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
public interface SysSubjectService extends IService<SysSubjectDO> {

    /**
     * 通过用户id获取主体列表
     *
     * @param userId
     * @return
     */
    List<SysSubjectDO> listSysObjsByUserId(String userId);

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysSubjectDO> listSysObjsByPage(SysSubjectPageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysSubjectDO saveSysObj(SysSubjectForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysSubjectDO updateSysObj(SysSubjectForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysObj(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysObj(String[] ids);

    /**
     * 根据id获取名称
     *
     * @param id
     * @return
     */
    String getNameById(String id);
}
