package org.happykit.happyboot.log;

import com.google.gson.Gson;
import org.happykit.happyboot.log.model.WebLogDTO;
import org.happykit.happyboot.log.service.WebLogService;
import org.happykit.happyboot.sys.model.entity.SysLogDO;
import org.happykit.happyboot.sys.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * web日志服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Service
public class WebLogServiceImpl implements WebLogService {
    private static final Logger logger = LoggerFactory.getLogger(WebLogServiceImpl.class);
    @Resource
    private SysLogService sysLogService;

    @Override
    public void saveWebLog(WebLogDTO dto) {
        SysLogDO entity = new SysLogDO();
        entity.setCostTime(dto.costTime());
        entity.setIp(dto.ip());
        entity.setRequestParam(dto.requestArgs());
        entity.setRequestType(dto.httpMethod());
        entity.setRequestUrl(dto.url());
        entity.setMethod(dto.classMethod());
        entity.setLogContent(dto.description());
        sysLogService.save(entity);

        logger.info("WebLogDTO  : {}", new Gson().toJson(dto));
    }
}
