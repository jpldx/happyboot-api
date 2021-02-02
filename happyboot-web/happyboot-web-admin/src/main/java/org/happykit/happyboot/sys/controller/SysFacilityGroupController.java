package org.happykit.happyboot.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupDO;
import org.happykit.happyboot.sys.model.form.SysFacilityGroupForm;
import org.happykit.happyboot.sys.model.form.SysFacilityGroupRelForm;
import org.happykit.happyboot.sys.model.query.SysFacilityGroupPageQueryParam;
import org.happykit.happyboot.sys.model.vo.SysFacilityVO;
import org.happykit.happyboot.sys.service.SysDictService;
import org.happykit.happyboot.sys.service.SysFacilityGroupRelService;
import org.happykit.happyboot.sys.service.SysFacilityGroupService;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
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
 * 功能组
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/facilityGroup")
public class SysFacilityGroupController extends BaseController {
	@Autowired
	private SysFacilityGroupService sysFacilityGroupService;
	@Autowired
	private SysSecurityUtils sysSecurityUtils;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private SysFacilityGroupRelService sysFacilityGroupRelService;


	/**
	 * 列表
	 *
	 * @return
	 */
	@GetMapping("/list")
	@JsonView({View.SimpleView.class})
	public R list() {
		return success(sysFacilityGroupService.list(new QueryWrapper<SysFacilityGroupDO>().orderByDesc("create_time")));
	}


	/**
	 * 分页列表
	 *
	 * @param query
	 * @return
	 */
	@GetMapping("/page")
	@JsonView({View.SimpleView.class})
	public R page(@Valid SysFacilityGroupPageQueryParam query) {
		return success(sysFacilityGroupService.page(query));
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
		SysFacilityGroupDO dbRecord = sysFacilityGroupService.getById(id);
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
	public R add(@RequestBody @Valid SysFacilityGroupForm form) {
		SysFacilityGroupDO entity = new SysFacilityGroupDO();
		// TODO 对象信息
//		entity.setObjectId(sysSecurityUtils.getCurrentId);
		entity.setFacilityGroupName(form.getFacilityGroupName());
		return success(sysFacilityGroupService.save(entity));
	}

	/**
	 * 编辑
	 *
	 * @param form
	 * @return
	 */
	@PutMapping("/update")
	public R update(@RequestBody @Validated(Update.class) SysFacilityGroupForm form) {
		SysFacilityGroupDO dbRecord = sysFacilityGroupService.getById(form.getId());
		Assert.isNotFound(dbRecord);
		dbRecord.setFacilityGroupName(form.getFacilityGroupName());
		return success(sysFacilityGroupService.updateById(dbRecord));
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/delete")
	public R delete(@NotEmpty String[] ids) {
		return success(sysFacilityGroupService.removeByIds(Arrays.asList(ids)));
	}


	/**
	 * 查询用户-功能组关联关系
	 *
	 * @return
	 */
	@GetMapping("/queryFacilityGroupByUser")
	public R queryFacilityGroupByUser(@NotBlank @RequestParam String userId) {
		return success(sysFacilityGroupService.queryFacilityGroupByUser(userId));
	}

	/**
	 * 查询功能组-功能关联关系
	 *
	 * @return
	 */
	@GetMapping("/queryFacilityByGroup")
	public R queryFacilityByGroup(@NotBlank @RequestParam String facilityGroupId) {
		List<SysFacilityVO> list = sysFacilityGroupService.queryFacilityByGroup(facilityGroupId);
		Map<String, List<SysFacilityVO>> groupMap = list.stream()
				.collect(Collectors.groupingBy(SysFacilityVO::getType));
		JSONArray json = new JSONArray();
		Set<Map.Entry<String, List<SysFacilityVO>>> entries = groupMap.entrySet();
		for (Map.Entry<String, List<SysFacilityVO>> entry : entries) {
			JSONObject obj = new JSONObject();
//			obj.put("label", entry.getKey());
			obj.put("name", sysDictService.getTextByCodeAndValue("FACILITY_TYPE", entry.getKey()));
			obj.put("list", entry.getValue());
			json.add(obj);
		}
		return success(json);
	}


	/**
	 * 新增功能组-功能关联关系
	 *
	 * @return
	 */
	@PostMapping("/saveFacilityGroupRel")
	public R saveFacilityGroupRel(@RequestBody @Validated SysFacilityGroupRelForm form) {
		return success(sysFacilityGroupRelService.saveFacilityGroupRel(form));
	}
}
