package org.happykit.happyboot.controller.sys;//package com.ltitframework.boot.controller.sys;
//
//import com.baomidou.mybatisplus.extension.api.R;
//import com.ltitframework.boot.core.controller.BaseController;
//import com.ltitframework.boot.sys.service.SysAccountService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author shaoqiang
// * @version 1.0 2020/3/5
// */
//@Slf4j
//@RestController
//@RequestMapping("/sysAccount")
//public class SysAccountController extends BaseController {
//
//	private final SysAccountService sysAccountService;
//
//	public SysAccountController(SysAccountService sysAccountService) {
//		this.sysAccountService = sysAccountService;
//	}
//
//	@GetMapping("list")
//	public R list() {
//		return R.ok(sysAccountService.list());
//	}
//
//	@GetMapping("get1")
//	public R get1() {
//		return R.ok(sysAccountService.getByAppKey("1"));
//	}
//}
