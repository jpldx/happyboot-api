package org.happykit.happyboot.controller.sys;//package com.ltitframework.boot.controller.sys;
//
//import com.baomidou.mybatisplus.extension.api.R;
//import com.ltitframework.boot.core.controller.BaseController;
//import com.ltitframework.boot.sys.dto.IdentityCardDTO;
//import com.ltitframework.boot.sys.service.SysLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author shaoqiang
// * @version 1.0 2020/3/4
// */
//@Slf4j
//@RestController
//@RequestMapping("/sysLog")
//public class SysLogController extends BaseController {
//
//	private final SysLogService sysLogService;
//
//	public SysLogController(SysLogService sysLogService) {
//		this.sysLogService = sysLogService;
//	}
//
//	@GetMapping("list")
//	public R list() {
//		return R.ok(sysLogService.list());
//	}
//
//	@PostMapping("list1")
//	public R list1(@Validated IdentityCardDTO dto) {
//		return R.ok(sysLogService.list());
//	}
//
//	@PostMapping("list2")
//	public R list2(@RequestBody @Validated IdentityCardDTO dto) {
//		return R.ok(sysLogService.list());
//	}
//}
