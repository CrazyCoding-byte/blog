package com.yzx.articles.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2022-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YzxBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 前台banner的id
     */
    private String id;

    /**
     * 博文的id
     */
    private String articleId;

    /**
     * 创建的时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改的时间
     */

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 0为未删除,1为删除
     */
    @TableLogic
    private Integer isDeleted;


}
