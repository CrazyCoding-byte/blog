package com.yzx.articles.entity;

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
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YzxAudio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 歌曲名称
     */
    private String name;

    /**
     * 歌曲作者
     */
    private String artist;

    /**
     * 歌曲源文件
     */
    private String url;

    /**
     * 歌曲封面
     */
    private String cover;

    /**
     * 歌曲主题颜色
     */
    private String theme;

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

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;


}
