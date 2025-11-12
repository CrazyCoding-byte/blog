package com.yzx.acl.service;

import com.yzx.acl.entity.YzxUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzx.acl.vo.UserRoleVo;
import com.yzx.acl.vo.YzxRoleVo;
import com.yzx.common_utils.result.R;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-22
 */
public interface YzxUserService extends IService<YzxUser> {

    R login(YzxUser yzxUser);

    R getUserInfo(String id);

    Boolean saveUser(YzxUser yzxUser);

    UserRoleVo getRelation(String userId);

    void logout(HttpServletRequest servletRequest);

    void removeRelation(String userId, String roleId);

    void saveRelation(String userId, String roleId);

    void removeUser(String uId);
}
