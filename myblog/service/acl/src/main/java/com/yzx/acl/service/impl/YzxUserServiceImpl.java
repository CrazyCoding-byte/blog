package com.yzx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.acl.entity.YzxRole;
import com.yzx.acl.entity.YzxUser;
import com.yzx.acl.entity.YzxUserRole;
import com.yzx.acl.mapper.YzxUserMapper;
import com.yzx.acl.service.YzxRoleService;
import com.yzx.acl.service.YzxUserRoleService;
import com.yzx.acl.service.YzxUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.acl.vo.UserRoleVo;
import com.yzx.acl.vo.UserVo;
import com.yzx.common_utils.exceptionhandle.MyException;
import com.yzx.common_utils.result.R;
import com.yzx.common_utils.utils.JwtUtils;
import com.yzx.spring_security.entity.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@Service
public class YzxUserServiceImpl extends ServiceImpl<YzxUserMapper, YzxUser> implements YzxUserService {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    RedisTemplate redisTemplate;
    @Resource
    YzxRoleService yzxRoleService;
    @Resource
    YzxUserRoleService yzxUserRoleService;

    @Override
    public R login(YzxUser yzxUser) {
        if (Objects.isNull(yzxUser)) {
            throw new MyException(110, "登录的数据不能为空!");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(yzxUser.getUsername(), yzxUser.getPassword());
        //调用我们创建的yzxuserdetailsservice接口返回longuser的数据 然后返回给前端
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        if (Objects.isNull(loginUser)) {
            throw new MyException(102, "当前的用户密码错误");
        }
        com.yzx.spring_security.entity.YzxUser aclUser1 = loginUser.getYzxUser();

        String jwtToken = JwtUtils.getJwtToken(aclUser1.getId(), aclUser1.getNickName());
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        //30分钟用户的信息失效
        redisTemplate.opsForValue().set("login:" + jwtToken + "20010103001x", loginUser, 30, TimeUnit.MINUTES);
        return R.ok().setData("result", map);
    }

    @Override
    public R getUserInfo(String id) {
        YzxUser yzxUser = baseMapper.selectById(id);
         if(!Objects.isNull(yzxUser)){
             UserVo userVo = new UserVo();
             QueryWrapper<YzxUserRole> objectQueryWrapper = new QueryWrapper<>();
             objectQueryWrapper.eq("user_id", yzxUser.getId());
             YzxUserRole one = yzxUserRoleService.getOne(objectQueryWrapper);
             YzxRole role = yzxRoleService.getById(one.getRoleId());
             userVo.setAvatar(yzxUser.getSalt());
             userVo.setName(yzxUser.getNickName());
             ArrayList<String> list = new ArrayList<>();
             list.add(role.getRoleName());
             userVo.setRoles(list);
             userVo.setIntroduction(role.getRemark());
             return R.ok().setData("item", userVo);
         }
         return R.fail();
    }

    @Override
    public Boolean saveUser(YzxUser yzxUser) {

        if (!Objects.isNull(yzxUser)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder.encode(yzxUser.getPassword());
            yzxUser.setPassword(encode);
            baseMapper.insert(yzxUser);
            return true;
        }
        return false;
    }

    @Override
    public UserRoleVo getRelation(String userId) {
        if (!StringUtils.isEmpty(userId)) {
            //获取当前用户的信息
            YzxUser yzxUser = baseMapper.selectById(userId);
            UserRoleVo yzxRoleVo = new UserRoleVo();
            QueryWrapper<YzxUserRole> yzxUserRoleQueryWrapper = new QueryWrapper<>();
            yzxUserRoleQueryWrapper.eq("user_id", userId);
            //获取用户角色关联信息
            YzxUserRole one = yzxUserRoleService.getOne(yzxUserRoleQueryWrapper);
            BeanUtils.copyProperties(one, yzxRoleVo);
            //获取角色信息
            YzxRole roleinfo = yzxRoleService.getById(one.getRoleId());
            yzxRoleVo.setNickName(yzxUser.getNickName());
            yzxRoleVo.setRemark(roleinfo.getRemark());
            return yzxRoleVo;
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("token");
        //删除登录的用户信息
        redisTemplate.delete("login:" + token + "20010103001x");
    }

    @Override
    public void removeRelation(String userId, String roleId) {
        QueryWrapper<YzxUserRole> yzxUserRoleQueryWrapper = new QueryWrapper<>();
        yzxUserRoleQueryWrapper.eq("user_id", userId);
        yzxUserRoleQueryWrapper.eq("role_id", roleId);
        yzxUserRoleService.remove(yzxUserRoleQueryWrapper);
    }

    @Override
    public void saveRelation(String userId, String roleId) {
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(roleId)) {
            QueryWrapper<YzxUserRole> yzxUserRoleQueryWrapper = new QueryWrapper<>();
            yzxUserRoleQueryWrapper.eq("user_id", userId);
            int count = yzxUserRoleService.count(yzxUserRoleQueryWrapper);
            if (count >= 1) {
                throw new MyException(112, "已经有关联的数据了");
            }
            YzxUserRole yzxUserRole = new YzxUserRole();
            yzxUserRole.setUserId(userId);
            yzxUserRole.setRoleId(roleId);
            yzxUserRoleService.save(yzxUserRole);
            return;
        } else {
            throw new MyException(111, "关联失败");
        }
    }

    @Override
    public void removeUser(String uId) {
        //删除用户
        int i = baseMapper.deleteById(uId);
        //删除掉关联的角色中间表
        QueryWrapper<YzxUserRole> yzxUserRoleServiceQueryWrapper = new QueryWrapper<>();
        yzxUserRoleServiceQueryWrapper.eq("user_id", uId);
        yzxUserRoleService.remove(yzxUserRoleServiceQueryWrapper);
    }
}
