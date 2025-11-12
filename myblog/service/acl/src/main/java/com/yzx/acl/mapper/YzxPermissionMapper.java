package com.yzx.acl.mapper;

import com.yzx.acl.entity.YzxPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
@Mapper
public interface YzxPermissionMapper extends BaseMapper<YzxPermission> {

    List<String> getPermissionMenu(String id);
}
