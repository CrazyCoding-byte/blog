package com.yzx.articles.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author yzx
 * @create 2022-12-27 3:25
 */
@Data
public class BannerVo {
    private String id;
    private String articleId;
    private String articleTitle;
    private String articleCover;
    private String subjectId;
    private String subjectName;
    private Date createTime;
    private Date updateTime;
}
