package com.yzx.articles.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author yzx
 * @since 2022-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YzxInformal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 随感id
     */
    private String id;

    /**
     * 随感标题
     */
    private String informalTitle;
    private String cover;
    /**
     * 随感内容
     */
    private String informalContent;
    private String tagId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    private Integer isDelete;
}
