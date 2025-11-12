package com.yzx.acl.controller;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.acl.entity.YzxPermission;
import com.yzx.acl.entity.YzxRole;
import com.yzx.acl.entity.YzxRolePermission;
import com.yzx.acl.service.YzxPermissionService;
import com.yzx.acl.service.YzxRolePermissionService;
import com.yzx.acl.service.YzxRoleService;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/acl/role")
@CrossOrigin
public class YzxRoleController {
    @Resource
    YzxRoleService yzxRoleService;
    @Resource
    YzxRolePermissionService yzxRolePermissionService;
    //添加一个角色
    @PostMapping("/save")
    public R savePermission(@RequestBody YzxRole yzxRole) {
        boolean save = yzxRoleService.save(yzxRole);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    //显示所有的角色
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('blog')")
    public R listPermission() throws UnsupportedEncodingException {
        List<YzxRole> list = yzxRoleService.list();
        return R.ok().setData("item", list);
    }

    //删除一个角色
    @DeleteMapping("/delete/{roleId}")
    public R deletePermission(@PathVariable String roleId) {
        QueryWrapper<YzxRolePermission> yzxRolePermissionQueryWrapper = new QueryWrapper<>();
        yzxRolePermissionQueryWrapper.eq("role_id", roleId);
        //删除当前的角色所对应的权限
        List<YzxRolePermission> list = yzxRolePermissionService.list(yzxRolePermissionQueryWrapper);
        List<String> collect = list.stream().map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        yzxRolePermissionService.removeByIds(collect);
        boolean b = yzxRoleService.removeById(roleId);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    //根据角色的id查询出角色对应的menu菜单
    @GetMapping("/getInfo/{id}")
    public R getPermission(@PathVariable String id) {
        YzxRoleVo roleVo = yzxRoleService.getRoleInfoByRoleId(id);
        if (!Objects.isNull(roleVo)) {
            return R.ok().setData("item", roleVo);
        }
        return R.fail().setCode(105).setMessageDeta("当前的角色菜单信息为空");
    }

    @GetMapping("/getMenu")
    //获取当前登录用户的权限菜单
    public List<JSONObject> getMenu(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        List<JSONObject> result= null;
        try {
            result = yzxRoleService.getMenu(principal.getYzxUser().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/update/{id}")
    public R updatePermission(@RequestBody List<String> listid,@PathVariable String id) {
        boolean b = yzxRoleService.updateRole(listid,id);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    //获取所有的阶梯菜单信息
    @GetMapping("/showPermission")
    public R showPermission() {
        List<YzxPermission> list = yzxRoleService.getYzxPermissionWithChild();
        if (!CollectionUtils.isEmpty(list)) {
            return R.ok().setData("item", list);
        }
        return R.fail();
    }
}

