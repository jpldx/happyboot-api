package org.happykit.happyboot.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.demo.model.entity.DemoDataDO;
import org.happykit.happyboot.demo.model.form.DemoDataForm;
import org.happykit.happyboot.demo.model.query.DemoDataPageQueryParam;

import javax.validation.constraints.NotNull;

/**
 * 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/06/10
 */
public interface DemoDataService extends IService<DemoDataDO> {

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<DemoDataDO> listDemoDatasByPage(DemoDataPageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    DemoDataDO saveDemoData(DemoDataForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    DemoDataDO updateDemoData(DemoDataForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteDemoData(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteDemoData(Long... ids);
}

