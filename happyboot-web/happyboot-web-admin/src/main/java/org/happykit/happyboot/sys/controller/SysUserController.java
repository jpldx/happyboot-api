package org.happykit.happyboot.sys.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.sys.enums.AuthTypeEnum;
import org.happykit.happyboot.sys.facade.SysAuthFacade;
import org.happykit.happyboot.sys.facade.SysUserFacade;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.excel.SysUserData;
import org.happykit.happyboot.sys.model.excel.listener.SysUserDataListener;
import org.happykit.happyboot.sys.model.query.SysLoginLogPageQuery;
import org.happykit.happyboot.sys.model.query.SysUserPageQueryParam;
import org.happykit.happyboot.sys.service.*;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.sys.model.form.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    private final SysUserService sysUserService;
    private final SysUserRelService sysUserRelService;
    private final SysUserFacade sysUserFacade;
    private final SysSecurityUtils sysSecurityUtils;
    private final SysDeptObjService sysDeptObjService;
    private final StringRedisTemplate redisTemplate;
    private final TokenProperties tokenProperties;
    private final SysRoleService sysRoleService;
    private final SysAuthFacade sysAuthFacade;
    private final SysSubjectService sysSubjectService;

    public SysUserController(SysUserService sysUserService,
                             SysUserFacade sysUserFacade,
                             SysUserRelService sysUserRelService,
                             SysSecurityUtils sysSecurityUtils,
                             SysDeptObjService sysDeptObjService,
                             StringRedisTemplate redisTemplate,
                             TokenProperties tokenProperties,
                             SysRoleService sysRoleService,
                             SysAuthFacade sysAuthFacade,
                             SysSubjectService sysSubjectService) {
        this.sysUserService = sysUserService;
        this.sysUserFacade = sysUserFacade;
        this.sysUserRelService = sysUserRelService;
        this.sysSecurityUtils = sysSecurityUtils;
        this.sysDeptObjService = sysDeptObjService;
        this.redisTemplate = redisTemplate;
        this.tokenProperties = tokenProperties;
        this.sysRoleService = sysRoleService;
        this.sysAuthFacade = sysAuthFacade;
        this.sysSubjectService = sysSubjectService;
    }

    /**
     * 列表
     *
     * @return
     */
    @Log("用户-列表")
    @GetMapping({"/list"})
    @JsonView({View.SecurityView.class})
    public R list() {
        return success(sysUserService.list());
    }

    /**
     * 分页列表
     *
     * @param param
     * @return
     */
    @Log("用户-分页列表")
    @GetMapping("/page")
    @JsonView({View.SimpleView.class})
    public R page(@Validated SysUserPageQueryParam param) {
        return success(sysUserService.listSysUsersByPage(param));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Log("用户-查询")
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotNull Long id) {
        JSONObject json = new JSONObject();
        SysUserDO userinfo = sysUserService.getById(id);
        if (null == userinfo) {
            return R.failed("未找到相关记录");
        }
        List<SysUserDO> metaList = sysUserRelService.getUserRelListByUserId(userinfo.getId(), userinfo.getUserType());
        List<JSONObject> userlist = metaList.stream().map(v -> {
            JSONObject user = new JSONObject();
            user.put("id", v.getId());
            user.put("username", v.getUsername());
            user.put("nickname", v.getNickname());
            user.put("avatar", v.getHeadPic());
            return user;
        }).collect(Collectors.toList());
        // 基本信息
        json.put("id", userinfo.getId());
        json.put("username", userinfo.getUsername());
        json.put("nickname", userinfo.getNickname());
        json.put("userType", userinfo.getUserType());
        json.put("status", userinfo.getStatus());
        json.put("headPic", userinfo.getHeadPic());
        // 关联账号信息
        json.put("userlist", userlist);
        return success(json);
    }

    /**
     * 新增
     *
     * @param form
     * @return
     */
    @Log("用户-新增")
    @PostMapping("/add")
    @JsonView({View.SimpleView.class})
    public R add(@RequestBody @Validated SysUserForm form) {
        return success(sysUserFacade.saveSysUser(form));
    }

    /**
     * 更新
     *
     * @param form
     * @return
     */
    @Log("用户-更新")
    @PostMapping("/update")
    @JsonView({View.SimpleView.class})
    public R update(@RequestBody @Validated(Update.class) SysUserForm form) {
        return success(sysUserFacade.updateSysUser(form));
    }

    /**
     * 更新状态
     *
     * @param form
     * @return
     */
    @Log("用户-更新状态")
    @PostMapping("/updateStatus")
    public R updateStatus(@RequestBody @Validated SysUserStatusForm form) {
        return success(sysUserService.updateSysUserStatus(form));
    }

    /**
     * 更新密码
     *
     * @param form
     * @return
     */
    @Log("用户-更新密码")
    @PostMapping(value = "/updatePwd")
    public R updatePwd(@RequestBody @Validated SysUserPwdForm form) {
        return success(sysUserService.updateSysUserPwd(form));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log("用户-删除")
    @DeleteMapping("/delete")
    public R delete(@NotEmpty String[] ids) {
        return success(sysUserFacade.deleteSysUser(ids));
    }

    /**
     * 导入
     *
     * @param form
     * @return
     */
    @PostMapping("/importData")
    public R importData(@RequestBody @Validated SysUserImportForm form) {
        return success(sysUserFacade.importData(form));
    }

    /**
     * excel导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public R importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), SysUserData.class, new SysUserDataListener(sysUserService)).sheet()
                .doRead();
        return success("");
    }

    /**
     * excel导出
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<SysUserData> list = new ArrayList<SysUserData>();
        for (int i = 0; i < 10; i++) {
            SysUserData data = new SysUserData();
            data.setUsername("字符串" + 0);
            data.setNickname(i + "");
            list.add(data);
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), SysUserData.class).sheet("模板").doWrite(list);
    }

    /**
     * 新增用户与角色关联（1 - n）
     *
     * @param form
     * @return
     */
    @Log("用户-新增角色关联")
    @PostMapping("/saveUserRole")
    public R saveUserRole(@RequestBody @Validated SysRefUserRoleForm form) {
        return success(sysUserFacade.saveUserRole(form));
    }

    /**
     * 新增用户与区域关联（1 - n）
     *
     * @param form
     * @return
     */
    @Log("用户-新增区域关联")
    @PostMapping("/saveUserDeptRegion")
    public R saveUserDeptRegion(@RequestBody @Validated SysRefUserDeptRegionForm form) {
        return success(sysUserFacade.saveUserDeptRegion(form));
    }

    /**
     * 新增用户与部门关联（1 - n）
     *
     * @param form
     * @return
     */
    @Log("用户-新增部门关联")
    @PostMapping("/saveUserDeptObj")
    public R saveUserDeptObj(@RequestBody @Validated SysRefUserDeptObjForm form) {
        return success(sysUserFacade.saveUserDeptObj(form));
    }

    /**
     * 新增用户与功能组关联（1 - n）
     *
     * @param form
     * @return
     */
    @Log("用户-新增功能组关联")
    @PostMapping("/saveUserFacilityGroupRel")
    public R saveUserFacilityGroupRel(@RequestBody @Validated SysFacilityUserGroupRelForm form) {
        return success(sysUserFacade.saveUserFacilityGroupRel(form));
    }

    /**
     * 查询主账号列表
     *
     * @param keyword 搜索关键词
     * @return
     */
    @Log("用户-查询主账号列表")
    @GetMapping("/queryMainAccountList")
    public R queryMainAccountList(@NotBlank(message = "关键词不能为空") @RequestParam(name = "keyword") String keyword) {
        List<SysUserDO> metaList = sysUserService.getMainAccountList(keyword);
        List<JSONObject> list = metaList.stream().map(v -> {
            JSONObject user = new JSONObject();
            user.put("id", v.getId());
            user.put("username", v.getUsername());
            user.put("nickname", v.getNickname());
            user.put("avatar", v.getHeadPic());
            return user;
        }).collect(Collectors.toList());
        return success(list);
    }


//    @GetMapping("/usercache")
//    public R userinfo() {
//        SecurityUserDetails loginUser = sysSecurityUtils.getCurrentUserDetailsCache();
//        return R.ok(loginUser);
//    }

    @Log("用户-查询当前登录用户信息")
    @GetMapping("/userinfo")
    public R usercache() {
        return R.ok(sysSecurityUtils.getCurrentUserDetails());
    }

    /**
     * 查询关联账号列表
     *
     * @return
     */
    @Log("用户登录-查询主账号关联账号列表")
    @GetMapping("/userlist")
    public R userlist() {
        SecurityUserDetails loginUser = sysSecurityUtils.getCurrentUserDetails();
        String mainAccountId = loginUser.getMainAccountId();
        // 子账号登录返回空集合
        if (null == mainAccountId) {
            return R.ok(new ArrayList<>());
        }
        SysUserDO mainAccount = sysUserService.getById(mainAccountId);
        Assert.isNotFound(mainAccount);
        List<SysUserDO> metaList = new ArrayList<>();
        metaList.add(mainAccount);
        metaList.addAll(sysUserRelService.getUserRelListByUserId(mainAccountId, mainAccount.getUserType()));

        List<JSONObject> userlist = metaList.stream().map(v -> {
            JSONObject user = new JSONObject();
            user.put("id", v.getId());
            user.put("username", v.getUsername());
            user.put("nickname", v.getNickname());
            user.put("avatar", v.getHeadPic());
            user.put("usertype", v.getUserType());
            if (loginUser.getId().equals(v.getId())) {
                user.put("active", true);
            } else {
                user.put("active", false);
            }
            user.put("subjectname", sysSubjectService.getNameById(v.getSubjectId()));
            user.put("deptname", sysDeptObjService.getNameById(v.getDeptId()));
            return user;
        }).collect(Collectors.toList());
        return R.ok(userlist);
    }

    /**
     * 查询用户部门id
     *
     * @param userId 用户id
     * @return
     */
    @Log("用户-查询用户部门id")
    @GetMapping("/queryDeptId")
    public R queryDeptId(@NotBlank String userId) {
        SysUserDO dbRecord = sysUserService.getById(userId);
        Assert.isNotFound(dbRecord);
        return success(dbRecord.getDeptId());
    }

    /**
     * 用户选择账号登录
     *
     * @param form
     * @return
     */
    @Log("用户登录-选择账号登录")
    @PostMapping("/selectLogin")
    public R selectLogin(HttpServletRequest request, @RequestBody @Validated SysUserIdForm form) {
        SysUserDO selectedUser = sysUserService.getById(form.getUserId());
        Assert.isNotFoundUser(selectedUser);

        SecurityUserDetails loginUser = sysSecurityUtils.getCurrentUserDetails();
        // TODO 校验是否有权限使用选择的账号进行登录
//        sysUserRelService.getUserRelListByUserId(loginUser.getMainAccountId(),loginUser.)

        List<String> roles;
        List<String> permissions;
        if (sysAuthFacade.checkAdminByUsername(selectedUser.getUsername())) {
            permissions = sysAuthFacade.listAdminApis();
            roles = sysAuthFacade.listAdminRoles();
        } else {
            permissions = sysAuthFacade.listVisibleApisByUserId(selectedUser.getId());
            roles = sysRoleService.listAuthorityNamesByUserIdAndAuthType(selectedUser.getId(), AuthTypeEnum.VISIBLE.getCode());
        }

        SecurityUserDetails userDetails = new SecurityUserDetails(selectedUser.getId(),
                loginUser.getMainAccountId(),
                selectedUser.getUsername(),
                selectedUser.getPassword(),
                selectedUser.getDeptId(),
                selectedUser.getUserType(),
                selectedUser.getStatus(),
                permissions,
                roles,
                loginUser.getToken());

        redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + loginUser.getToken(), new Gson().toJson(userDetails), tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return R.ok(selectedUser);
    }

    /**
     * 查询用户登录历史
     *
     * @return
     */
    @Log("用户-查询用户登录历史")
    @GetMapping("/queryLoginHistory")
    public R queryLoginLogs(@Validated SysLoginLogPageQuery query) {
        return R.ok(sysUserService.queryLoginLogPageList(query));
    }
}
