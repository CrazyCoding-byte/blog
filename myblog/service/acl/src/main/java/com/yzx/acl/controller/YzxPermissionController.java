package com.yzx.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.acl.entity.YzxPermission;
import com.yzx.acl.service.YzxPermissionService;
import com.yzx.common_utils.exceptionhandle.MyException;
import com.yzx.common_utils.result.R;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/acl/permission")
@CrossOrigin
public class YzxPermissionController {
    @Resource
    YzxPermissionService yzxPermissionService;

    @PostMapping("/save")
    public R savePermission(@RequestBody YzxPermission yzxPermission) {
        if (Objects.isNull(yzxPermission)) {
            throw new MyException(108, "要添加的数据不能为空");
        }
        boolean save = yzxPermissionService.save(yzxPermission);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/list/{page}/{limit}")
    public R listPermission(@PathVariable Integer page, @PathVariable Integer limit, @RequestParam(required = false) String permissonname) throws UnsupportedEncodingException {
        if (!StringUtils.isEmpty(permissonname)) {
            String decode = URLDecoder.decode(permissonname, "utf-8");
            System.out.printf("当前解密后的数据%s", decode);
            System.out.println(permissonname);
        }
        LambdaQueryWrapper<YzxPermission> yzxPermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(permissonname)) {
            yzxPermissionLambdaQueryWrapper.like(YzxPermission::getName, permissonname);
        }
        Page<YzxPermission> page1 = yzxPermissionService.page(new Page<YzxPermission>(page, limit), yzxPermissionLambdaQueryWrapper);
        return R.ok().setData("item", page1);
    }

    @DeleteMapping("/delete/{permissionId}")
    public R deletePermission(@PathVariable String permissionId) {
        boolean b = yzxPermissionService.removeById(permissionId);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/getInfo/{id}")
    public R getPermission(@PathVariable String id) {
        YzxPermission permission = yzxPermissionService.getById(id);
        if (!Objects.isNull(permission)) {
            return R.ok().setData("item", permission);
        }
        return R.fail().setCode(105).setMessageDeta("当前的菜单信息为空");
    }

    @PostMapping("/update")
    public R getPermission(@RequestBody YzxPermission yzxPermission) {
        boolean b = yzxPermissionService.updateById(yzxPermission);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }


}

