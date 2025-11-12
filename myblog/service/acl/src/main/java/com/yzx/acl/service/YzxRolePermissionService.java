package com.yzx.acl.service;

import com.yzx.acl.entity.YzxRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限 服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
public interface YzxRolePermissionService extends IService<YzxRolePermission> {

    void removeByPermissionIds(List<String> ids,String id);
}
