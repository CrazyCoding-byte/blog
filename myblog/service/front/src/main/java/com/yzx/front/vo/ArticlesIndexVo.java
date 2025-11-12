package com.yzx.front.vo;

import lombok.Data;

import java.util.Date;


/**
 * @author yzx
 * @create 2022-12-27 3:49
 */
@Data
public class ArticlesIndexVo {
    private String articleId;
    private String articleTitle;
    private String articleCover;
    //浏览量
    private Long articleViews;
    //评论数量
    private Long articelCommentCount;
    private Date createTime;
    private String subjectId;
    private String subjectName;
    private String tagName;

}
