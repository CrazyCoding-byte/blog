package com.yzx.acl.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yzx
 * @create 2022-12-29 9:36
 */
@Data
public class UserVo {

    //角色
    private List<String> roles;
    //头像
    private String avatar;
    private String name;
    private String introduction;
}
