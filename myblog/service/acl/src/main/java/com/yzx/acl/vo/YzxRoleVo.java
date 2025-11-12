package com.yzx.acl.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yzx.acl.entity.YzxPermission;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yzx
 * @create 2022-12-23 3:39
 */
@Data
public class YzxRoleVo {
    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer isDeleted;
    /**
     * 评论日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 角色所对应的菜单
     */
    private List<YzxPermission> yzxPermissions;
    /**
     * 修改的日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
