package org.happykit.happyboot.sys.model.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.gson.Gson;
import org.happykit.happyboot.sys.model.excel.SysUserData;
import org.happykit.happyboot.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户excel处理类
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
public class SysUserDataListener extends AnalysisEventListener<SysUserData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserDataListener.class);

    List<SysUserData> list = new ArrayList<>();

    private SysUserService sysUserService;

    public SysUserDataListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param sysUserData
     * @param analysisContext
     */
    @Override
    public void invoke(SysUserData sysUserData, AnalysisContext analysisContext) {

        LOGGER.info("解析到一条数据:{}", new Gson().toJson(sysUserData));
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("所有数据解析完成！");
    }
}
