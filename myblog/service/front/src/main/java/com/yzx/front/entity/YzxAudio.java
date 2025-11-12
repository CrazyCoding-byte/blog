package com.yzx.front.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;


}
