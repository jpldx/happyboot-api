package org.happykit.happyboot.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.entity.SysFacilityDO;
import org.happykit.happyboot.sys.model.form.SysFacilityForm;
import org.happykit.happyboot.sys.model.form.SysFacilityParamForm;
import org.happykit.happyboot.sys.model.query.SysFacilityPageQueryParam;
import org.happykit.happyboot.sys.model.vo.SysFacilityVO;
import org.happykit.happyboot.sys.service.SysDictService;
import org.happykit.happyboot.sys.service.SysFacilityService;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/facility")
public class SysFacilityController extends BaseController {
    @Autowired
    private SysFacilityService sysFacilityService;
    @Autowired
    private SysSecurityUtils sysSecurityUtils;
    @Autowired
    private SysDictService sysDictService;


    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Valid SysFacilityPageQueryParam query) {
        return success(sysFacilityService.page(query));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotBlank String id) {
        SysFacilityDO dbRecord = sysFacilityService.getById(id);
        Assert.isNotFound(dbRecord);
        return success(dbRecord);
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody @Valid SysFacilityForm form) {
        SysFacilityDO entity = new SysFacilityDO();
        BeanUtils.copyProperties(form, entity);
        return success(sysFacilityService.save(entity));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody @Validated(Update.class) SysFacilityForm form) {
        SysFacilityDO dbRecord = sysFacilityService.getById(form.getId());
        Assert.isNotFound(dbRecord);
        BeanUtils.copyProperties(form, dbRecord);
        return success(sysFacilityService.updateById(dbRecord));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(sysFacilityService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 参数查询
     *
     * @param id              功能ID
     * @param setFrom         功能定义（默认sys）
     * @param facilityGroupId 功能组ID
     * @param userId          用户ID
     * @return
     */
    @GetMapping("/getParam")
    public R getParam(@NotBlank @RequestParam(name = "id") String id,
                      @NotBlank(message = "功能定义不能为空") @RequestParam(name = "setFrom", required = false, defaultValue = "sys") String setFrom,
                      @RequestParam(name = "facilityGroupId", required = false) String facilityGroupId,
                      @RequestParam(name = "userId", required = false) String userId) {
        SysFacilityParamForm param = sysFacilityService.getParam(id, setFrom, facilityGroupId, userId);
        return success(param);
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    @PostMapping("/setParam")
    public R param(@RequestBody @Valid SysFacilityParamForm form) {
        return success(sysFacilityService.setParam(form));
    }

    /**
     * 参数删除
     *
     * @param facilityParamId 功能参数ID
     * @return
     */
    @DeleteMapping("/deleteParam")
    public R param(@NotBlank @RequestParam(name = "facilityParamId") String facilityParamId) {
        return success(sysFacilityService.deleteParam(facilityParamId));
    }

    /**
     * 查询用户-功能关联关系
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/queryFacilityByUser")
    public R queryFacilityByUser(@NotBlank String userId) {
        List<SysFacilityVO> list = sysFacilityService.getFacilityByUser(userId);
        Map<String, List<SysFacilityVO>> groupMap = list.stream()
                .collect(Collectors.groupingBy(SysFacilityVO::getType));
        JSONArray json = new JSONArray();
        Set<Map.Entry<String, List<SysFacilityVO>>> entries = groupMap.entrySet();
        for (Map.Entry<String, List<SysFacilityVO>> entry : entries) {
            JSONObject obj = new JSONObject();
            obj.put("name", sysDictService.getTextByCodeAndValue("FACILITY_TYPE", entry.getKey()));
            obj.put("list", entry.getValue());
            json.add(obj);
        }
        return success(json);
    }
}
