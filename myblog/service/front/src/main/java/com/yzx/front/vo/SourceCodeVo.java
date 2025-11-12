package com.yzx.front.vo;

import com.yzx.front.entity.YzxArticles;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yzx
 * @create 2023-01-02 13:53
 */
@Data
public class SourceCodeVo {
    //当前的文章
    private List<YzxArticles> articles;
    //文章观看数
    private Integer articlesViewCount;
    //文章评论数量
    private Integer articelsCommentCount;
    private Integer articlesCount;
    //标签总数
    private Integer tagCount;
    //修改时间
    private Date  updateTime;

    private Integer subjctCount;
    //当前分页有的总数据
    private Integer pageCount;
}
