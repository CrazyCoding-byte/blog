package com.yzx.front.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author yzx
 * @create 2023-01-12 1:03
 */
@Data
public class ArticlesDetails {
    private String articleId;
    private String articleTitle;
    private String articleContent;
    //浏览量
    private Long articleViews;
    private Date createTime;
    private String subjectId;
    private String subjectName;
}
