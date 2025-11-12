package com.yzx.acl.service.impl;

import com.yzx.acl.entity.YzxPermission;
import com.yzx.acl.mapper.YzxPermissionMapper;
import com.yzx.acl.service.YzxPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@Service
public class YzxPermissionServiceImpl extends ServiceImpl<YzxPermissionMapper, YzxPermission> implements YzxPermissionService {

    @Override
    public List<String> getPermissionMenu(String id) {
        List<String> permissionMenu = baseMapper.getPermissionMenu(id);
        return permissionMenu;
    }
}
