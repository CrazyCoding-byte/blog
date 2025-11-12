package com.yzx.front.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
public class YzxComments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "comment_id", type = IdType.ID_WORKER_STR)
    private String commentId;

    /**
     * 评论ID的名称
     */
    private String userNickname;


    /**
     * 评论ID的qq
     */
    private String userQq;

    /**
     * 评论博文ID
     */
    private Long articleId;

    private String articleTitle;
    /**
     * 评论内容
     */
    private String commentContent;

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

   private String avatar;
}
