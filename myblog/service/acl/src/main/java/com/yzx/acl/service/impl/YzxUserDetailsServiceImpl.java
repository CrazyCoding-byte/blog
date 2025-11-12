package com.yzx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.spring_security.entity.YzxUser;
import com.yzx.acl.service.YzxPermissionService;
import com.yzx.acl.service.YzxUserService;
import com.yzx.common_utils.exceptionhandle.MyException;
import com.yzx.spring_security.entity.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author yzx
 * @create 2022-12-22 4:14
 */
@Service
//security 和数据库交互的方法
public class YzxUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private YzxUserService yzxUserService;
    @Resource
    YzxPermissionService yzxPermissionService;

    @Override
    public LoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<com.yzx.acl.entity.YzxUser> yzxUserQueryWrapper = new QueryWrapper<>();
        yzxUserQueryWrapper.eq("username", username);
        com.yzx.acl.entity.YzxUser one = yzxUserService.getOne(yzxUserQueryWrapper);
        if (Objects.isNull(one)) {
            throw new MyException(101, "当前的用户信息不对");
        }
        YzxUser yzxUser = new YzxUser();
        BeanUtils.copyProperties(one, yzxUser);
        //获取当前用户的菜单信息
        List<String> permission = yzxPermissionService.getPermissionMenu(one.getId());
        return new LoginUser(yzxUser, permission);
    }
}
