package com.yzx.acl.service.impl;

import com.yzx.acl.entity.YzxRolePermission;
import com.yzx.acl.mapper.YzxRolePermissionMapper;
import com.yzx.acl.service.YzxRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@Service
public class YzxRolePermissionServiceImpl extends ServiceImpl<YzxRolePermissionMapper, YzxRolePermission> implements YzxRolePermissionService {

    @Override
    public void removeByPermissionIds(List<String> ids,String id) {
        baseMapper.removeByPermissionIds(ids,id);
    }
}
