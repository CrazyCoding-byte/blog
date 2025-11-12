package com.yzx.acl.controller;


import com.yzx.acl.entity.YzxRole;
import com.yzx.acl.entity.YzxUser;
import com.yzx.acl.service.YzxRoleService;
import com.yzx.acl.service.YzxUserService;
import com.yzx.acl.vo.UserRoleVo;
import com.yzx.acl.vo.YzxRoleVo;
import com.yzx.common_utils.result.R;
import com.yzx.spring_security.entity.LoginUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/acl/user")
@CrossOrigin
public class YzxUserController {
    @Resource
    YzxUserService yzxUserService;

    @Resource
    YzxRoleService yzxRoleService;

    @PostMapping("/login")
    public R login(@RequestBody YzxUser yzxUser) {
        R r = yzxUserService.login(yzxUser);
        return r;
    }

    @GetMapping("/getInfo")
    public R getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        R result = yzxUserService.getUserInfo(principal.getYzxUser().getId());
        return result;
    }

    @GetMapping("/list")
    public R getUserInfo() {
        List<YzxUser> list = yzxUserService.list();
        return R.ok().setData("item", list);
    }

    @PostMapping("/saveUser")
    public R saveUser(@RequestBody YzxUser yzxUser) {
        Boolean b = yzxUserService.saveUser(yzxUser);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/getRelation/{userId}")
    public R getRelation(@PathVariable String userId) {
        UserRoleVo userRoleVo = yzxUserService.getRelation(userId);
        return R.ok().setData("item", userRoleVo);
    }

    @GetMapping("/userLogout")
    public R logout(HttpServletRequest servletRequest) {
        yzxUserService.logout(servletRequest);
        return R.ok();
    }

    @DeleteMapping("/removeRelation/{userId}/{roleId}")
    public R removeRelation(@PathVariable String userId, @PathVariable String roleId) {
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(roleId)) {
            yzxUserService.removeRelation(userId, roleId);
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/listrole")
    public R getListRole() {
        return R.ok().setData("item", yzxRoleService.list());
    }

    @PostMapping("/saveRelation")
    public R saveRelation(@RequestBody UserRoleVo userRoleVo) {
        if (!Objects.isNull(userRoleVo)) {
            yzxUserService.saveRelation(userRoleVo.getUserId(), userRoleVo.getRoleId());
            return R.ok();
        }
        return R.fail();
    }

    @DeleteMapping("/removeUser/{uId}")
    public R removUser(@PathVariable String uId) {
        yzxUserService.removeUser(uId);
        return R.ok();
    }
}

