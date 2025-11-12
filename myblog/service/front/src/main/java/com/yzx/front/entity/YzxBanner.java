package com.yzx.front.entity;

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
    private Date createTime;

    /**
     * 修改的时间
     */
    private Date updateTime;

    /**
     * 0为未删除,1为删除
     */
    private Integer isDeleted;


}
