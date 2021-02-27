package org.happykit.happyboot.sys.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import org.happykit.happyboot.base.R;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.model.collections.SecurityLogCollection;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.security.util.JwtUtils;
import org.happykit.happyboot.sys.enums.AuthTypeEnum;
import org.happykit.happyboot.sys.facade.SysAuthFacade;
import org.happykit.happyboot.sys.facade.SysUserFacade;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.excel.SysUserData;
import org.happykit.happyboot.sys.model.excel.listener.SysUserDataListener;
import org.happykit.happyboot.sys.model.query.SysSecurityLogPageQuery;
import org.happykit.happyboot.sys.model.query.SysUserPageQueryParam;
import org.happykit.happyboot.sys.service.*;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.Assert;
import org.happykit.happyboot.validation.Update;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.sys.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRelService sysUserRelService;
    @Autowired
    private SysUserFacade sysUserFacade;
    @Autowired
    private SysSecurityUtils sysSecurityUtils;
    @Autowired
    private SysDeptObjService sysDeptObjService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAuthFacade sysAuthFacade;
    @Autowired
    private SysSubjectService sysSubjectService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SecurityCacheService securityCacheService;


    /**
     * 列表
     *
     * @return
     */
    @Log("用户-列表")
    @GetMapping({"/list"})
    @JsonView({View.SecurityView.class})
    public R list() {
        return R.ok(sysUserService.list());
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
        return R.ok(sysUserService.listSysUsersByPage(param));
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
        return R.ok(json);
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
        return R.ok(sysUserFacade.saveSysUser(form));
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
        return R.ok(sysUserFacade.updateSysUser(form));
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
        return R.ok(sysUserService.updateSysUserStatus(form));
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
        return R.ok(sysUserService.updateSysUserPwd(form));
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
        return R.ok(sysUserFacade.deleteSysUser(ids));
    }

    /**
     * 导入
     *
     * @param form
     * @return
     */
    @PostMapping("/importData")
    public R importData(@RequestBody @Validated SysUserImportForm form) {
        return R.ok(sysUserFacade.importData(form));
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
        return R.ok("");
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
        return R.ok(sysUserFacade.saveUserRole(form));
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
        return R.ok(sysUserFacade.saveUserDeptRegion(form));
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
        return R.ok(sysUserFacade.saveUserDeptObj(form));
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
        return R.ok(sysUserFacade.saveUserFacilityGroupRel(form));
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
        return R.ok(list);
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
        if (mainAccountId == null) {
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
        return R.ok(dbRecord.getDeptId());
    }

    /**
     * 用户切换账号登录
     *
     * @param form
     * @return
     */
    @Log("用户登录-切换账号登录")
    @PostMapping("/selectLogin")
    public R selectLogin(HttpServletRequest request, @RequestBody @Validated SysUserIdForm form) {

        SysUserDO selectUser = sysUserService.getById(form.getUserId());
        Assert.isNotFoundUser(selectUser);

        SecurityUserDetails loginUser = sysSecurityUtils.getCurrentUserDetails();
        // TODO 校验是否有权限使用选择的账号进行登录
//        sysUserRelService.getUserRelListByUserId(loginUser.getMainAccountId(),loginUser.)
        String selectUserId = selectUser.getId();
        String selectUsername = selectUser.getUsername();
        String selectUserType = selectUser.getUserType();
        String loginUserId = loginUser.getId();
        String mainAccountId = loginUser.getMainAccountId();
        if (loginUserId.equals(selectUserId)) {
            return R.ok(selectUser);
        }

        List<String> roles;
        List<String> permissions;
        if (sysAuthFacade.checkAdminByUsername(selectUsername)) {
            permissions = sysAuthFacade.listAdminApis();
            roles = sysAuthFacade.listAdminRoles();
        } else {
            permissions = sysAuthFacade.listVisibleApisByUserId(selectUserId);
            roles = sysRoleService.listAuthorityNamesByUserIdAndAuthType(selectUserId, AuthTypeEnum.VISIBLE.getCode());
        }

        Map<String, String> payload = new HashMap<>(2);
        payload.put(JwtUtils.CLAIM_USER_ID, selectUserId);
        payload.put(JwtUtils.CLAIM_USER_NAME, selectUsername);
        payload.put(JwtUtils.CLAIM_USER_TYPE, selectUserType);
        payload.put(JwtUtils.CLAIM_MAIN_ACCOUNT_ID, mainAccountId);
        // 重新颁发token
        String newToken = jwtUtils.create(payload);

        SecurityUserDetails userDetails = new SecurityUserDetails(
                selectUserId,
                mainAccountId,
                selectUserType,
                selectUsername,
                selectUser.getPassword(),
                selectUser.getDeptId(),
                selectUser.getStatus(),
                permissions,
                roles,
                newToken);
        securityCacheService.setUserDetails(userDetails);

        // old token 拉入黑名单
        String oldToken = loginUser.getToken();
        securityCacheService.removeUserDetails(oldToken);
        securityCacheService.setTokenToBlackList(oldToken);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        JSONObject json = new JSONObject();
        json.put("userinfo", selectUser);
        json.put("token", newToken);
        return R.ok(json);
    }

    /**
     * 查询用户安全日志
     *
     * @return
     */
    @Log("用户-查询用户安全日志")
    @GetMapping("/querySecurityLog")
    public R<Page<SecurityLogCollection>> querySecurityLog(@Validated SysSecurityLogPageQuery query) {
        return R.ok(sysUserService.querySecurityLogPageList(query));
    }
}
