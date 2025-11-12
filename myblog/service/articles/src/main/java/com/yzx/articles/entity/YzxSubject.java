package com.yzx.articles.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YzxSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "sort_id", type = IdType.ID_WORKER_STR)
    private String sortId;


    /**
     * 分类名称
     */
    private String sortName;

    /**
     * 评论日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改的日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否下架
     */
    @TableLogic
    private Integer isDelete;


}
