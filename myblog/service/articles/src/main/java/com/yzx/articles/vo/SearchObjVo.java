package com.yzx.articles.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author yzx
 * @create 2022-12-20 20:14
 */
@Data
public class SearchObjVo {
    private String articleTitle;
    private Date createTime;
    private Date updateTime;
}
