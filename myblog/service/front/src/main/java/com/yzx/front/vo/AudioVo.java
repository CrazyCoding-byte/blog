package com.yzx.front.vo;

import lombok.Data;

/**
 * @author yzx
 * @create 2023-01-14 6:39
 */
@Data
public class AudioVo {
    //歌曲名称
    private String name;
    //歌曲作者
    private String artist;
    //歌曲源文件地址
    private  String url;
    //歌曲封面地址
    private String cover;
    //主题颜色
    private String theme;
}
