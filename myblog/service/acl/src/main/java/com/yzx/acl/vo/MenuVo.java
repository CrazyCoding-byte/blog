package com.yzx.acl.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * @author yzx
 * @create 2022-12-19 14:12
 */
@Data
public class MenuVo {
    private String id;
    private String pid;
    private String permissionValue;
    private String name;
    private String path;
    private String component;
    private Integer type;
    private List<MenuVo> child;
    private Integer status;
    @TableField(exist = false)
    private Integer level;
    private String icon;
}
