package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.facade.CommonFacade;
import org.happykit.happyboot.web.model.CascaderDTO;
import org.happykit.happyboot.web.model.SelectDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统级全局公用方法
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/common")
public class CommonController extends BaseController {

    private final CommonFacade commonFacade;

    public CommonController(CommonFacade commonFacade) {
        this.commonFacade = commonFacade;
    }

    /**
     * 通过字典代号查询字典下拉列表
     *
     * @param dictCodes
     * @return
     */
    @GetMapping("/queryDictSelectList")
    public R queryDictSelectList(@NotEmpty String[] dictCodes) {
        List list = new ArrayList();
        for (String dictCode : dictCodes) {
            if (StringUtils.isNotBlank(dictCode)) {
                Map<String, Object> map = new HashMap<>(2);
                List<SelectDTO> selectList = commonFacade.listDictSelect(dictCode);
                map.put("dictCode", dictCode);
                map.put("data", selectList);
                list.add(map);
            }
        }
        return success(list);
    }

    /**
     * 通过字典代号查询无限级联字典树
     *
     * @param dictCodes
     * @return
     */
    @GetMapping("/queryDictCascaderList")
    public R queryDictCascaderList(@NotEmpty String[] dictCodes) {
        List list = new ArrayList();
        for (String dictCode : dictCodes) {
            if (StringUtils.isNotBlank(dictCode)) {
                Map<String, Object> map = new HashMap<>(2);
                List<CascaderDTO> cascaderList = commonFacade.listDictCascader(dictCode);
                map.put("dictCode", dictCode);
                map.put("data", cascaderList);
                list.add(map);
            }
        }
        return success(list);
    }

}
