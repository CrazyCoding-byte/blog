package com.yzx.acl.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yzx.acl.entity.YzxPermission;
import com.yzx.acl.entity.YzxRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzx.acl.vo.YzxRoleVo;
import com.yzx.common_utils.result.R;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
public interface YzxRoleService extends IService<YzxRole> {

    YzxRoleVo getRoleInfoByRoleId(String id);

    //修改当前角色拥有的菜单 可以
    boolean updateRole(List<String> listid,String id);

    List<YzxPermission> getYzxPermissionWithChild();

    List<JSONObject> getMenu(String id) throws JSONException;
}
