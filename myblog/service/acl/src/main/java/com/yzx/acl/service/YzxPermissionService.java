package com.yzx.acl.service;

import com.yzx.acl.entity.YzxPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
public interface YzxPermissionService extends IService<YzxPermission> {

    //根据用户的id查询权限信息
    List<String> getPermissionMenu(String id);

}
