package com.yzx.acl.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.acl.entity.YzxPermission;
import com.yzx.acl.entity.YzxRole;
import com.yzx.acl.entity.YzxRolePermission;
import com.yzx.acl.entity.YzxUserRole;
import com.yzx.acl.mapper.YzxRoleMapper;
import com.yzx.acl.service.YzxPermissionService;
import com.yzx.acl.service.YzxRolePermissionService;
import com.yzx.acl.service.YzxRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.acl.service.YzxUserRoleService;
import com.yzx.acl.utils.BuildYzxPermission;
import com.yzx.acl.vo.YzxRoleVo;
import com.yzx.common_utils.exceptionhandle.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@Service
public class YzxRoleServiceImpl extends ServiceImpl<YzxRoleMapper, YzxRole> implements YzxRoleService {
    @Resource
    YzxRolePermissionService yzxRolePermissionService;
    @Resource
    YzxPermissionService yzxPermissionService;
    @Resource
    YzxUserRoleService yzxUserRoleService;

    @Override
    //查询出当前角色的菜单信息
    public YzxRoleVo getRoleInfoByRoleId(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new MyException(106, "角色id不能为空!");
        }
        YzxRole yzxRole = baseMapper.selectById(id);
        YzxRoleVo roleVo = new YzxRoleVo();
        if (!Objects.isNull(yzxRole)) {
            BeanUtils.copyProperties(yzxRole, roleVo);
        }
        QueryWrapper<YzxRolePermission> yzxRolePermissionQueryWrapper = new QueryWrapper<>();
        yzxRolePermissionQueryWrapper.eq("role_id", id);

        //查询当前角色和菜单关联的表
        List<YzxRolePermission> list = yzxRolePermissionService.list(yzxRolePermissionQueryWrapper);
        //转换为集合
        List<String> collect = list.stream().map(item -> {
            return item.getPermissionId();
        }).collect(Collectors.toList());
        //查询出关联的菜单信息
        List<YzxPermission> yzxPermissions1 = null;
        if (!CollectionUtils.isEmpty(collect)) {
            List<YzxPermission> yzxPermissions = yzxPermissionService.listByIds(collect);
            yzxPermissions1 = getYzxPermission(yzxPermissions);
        }
        if (!CollectionUtils.isEmpty(yzxPermissions1)) {
            roleVo.setYzxPermissions(yzxPermissions1);
        }
        return roleVo;
    }


    private List<YzxPermission> getYzxPermission(List<YzxPermission> yzxPermissions) {
        List<YzxPermission> yzxPermissions1 = new ArrayList<>();
        for (YzxPermission yzxPermission : yzxPermissions) {
            if ("1".equals(yzxPermission.getPid())) {
                yzxPermissions1.add(getChildYzxPermission(yzxPermission, yzxPermissions));
            }
        }
        return yzxPermissions1;
    }

    private YzxPermission getChildYzxPermission(YzxPermission yzxPermission, List<YzxPermission> yzxPermissions) {
        for (YzxPermission permission : yzxPermissions) {
            if (yzxPermission.getId().equals(permission.getPid())) {
                if (CollectionUtils.isEmpty(yzxPermission.getChild())) {
                    yzxPermission.setChild(new ArrayList<YzxPermission>());
                }
                yzxPermission.getChild().add(getChildYzxPermission(permission, yzxPermissions));
            }
        }
        return yzxPermission;
    }

    @Override
    /**思路 1.如果当前是包含全部 并且还带没有的那就是添加操作
     *     2.如果当前是包含全部 并且没带没有的那就是不变
     *     3.如果当前是没包含全部 那就是删除操作 删除不包含的
     */
    public boolean updateRole(List<String> listid, String id) {
        QueryWrapper<YzxRolePermission> yzxRolePermissionQueryWrapper = new QueryWrapper<>();
        yzxRolePermissionQueryWrapper.eq("role_id", id);
        //根据当前的角色id 查询出当前role和permission想关联的表
        List<YzxRolePermission> list = yzxRolePermissionService.list(yzxRolePermissionQueryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            List<String> ids = list.stream().map(item -> {
                return item.getPermissionId();
            }).collect(Collectors.toList());
            if (!Objects.isNull(ids)) {
                ArrayList<YzxRolePermission> yzxRolePermissions = new ArrayList<>();
                for (String str : listid) {
                    //如果是当前集合不包含的就说明是添加操作or是删除操作
                    if (!ids.contains(str)) {
                        YzxRolePermission yzxRolePermission = new YzxRolePermission();
                        yzxRolePermission.setPermissionId(str);
                        yzxRolePermission.setRoleId(id);
                        yzxRolePermissions.add(yzxRolePermission);
                    } else {
                        //如果当前是相等的话 那么就删除原来的 直到最后如果还有 那么这些也就是要删除的数据
                        ids.remove(str);
                    }
                }
                yzxRolePermissionService.saveBatch(yzxRolePermissions);
                if (!CollectionUtils.isEmpty(ids)) {
                    //根据当前的角色 和 permision 批量删除
                    yzxRolePermissionService.removeByPermissionIds(ids, id);
                }
            }
        } else {
            //如果当前的角色什么权限都没有就直接添加成功
            ArrayList<YzxRolePermission> yzxRolePermissions = new ArrayList<>();
            for (String i : listid) {
                YzxRolePermission yzxRolePermission = new YzxRolePermission();
                yzxRolePermission.setPermissionId(i);
                yzxRolePermission.setRoleId(id);
                yzxRolePermissions.add(yzxRolePermission);
            }
            yzxRolePermissionService.saveBatch(yzxRolePermissions);
        }
        return true;
    }

    @Override
    public List<YzxPermission> getYzxPermissionWithChild() {
        List<YzxPermission> list = yzxPermissionService.list();
        List<YzxPermission> yzxPermission = getYzxPermission(list);
        return yzxPermission;
    }

    @Override
    public List<JSONObject> getMenu(String id) throws JSONException {
        QueryWrapper<YzxUserRole> yzxUserRoleQueryWrapper = new QueryWrapper<>();
        yzxUserRoleQueryWrapper.eq("user_id", id);
        YzxUserRole one = yzxUserRoleService.getOne(yzxUserRoleQueryWrapper);
        if (!Objects.isNull(one)) {
            QueryWrapper<YzxRolePermission> yzxRolePermissionQueryWrapper = new QueryWrapper<>();
            yzxRolePermissionQueryWrapper.eq("role_id", one.getRoleId());
            List<YzxRolePermission> list = yzxRolePermissionService.list(yzxRolePermissionQueryWrapper);
            List<String> collect = list.stream().map(item -> {
                return item.getPermissionId();
            }).collect(Collectors.toList());
            List<YzxPermission> yzxPermissions = yzxPermissionService.listByIds(collect);
            List<YzxPermission> yzxPermission = getYzxPermission(yzxPermissions);
            List<JSONObject> bulid = BuildYzxPermission.bulid(yzxPermission);
            System.out.println(bulid);
            return bulid;
        }
        return null;
    }

}
