package com.yzx.acl.mapper;

import com.yzx.acl.entity.YzxRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限 Mapper 接口
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@Mapper
public interface YzxRolePermissionMapper extends BaseMapper<YzxRolePermission> {

    void removeByPermissionIds(@Param("ids") List<String> ids,@Param("roleId") String id);
}
