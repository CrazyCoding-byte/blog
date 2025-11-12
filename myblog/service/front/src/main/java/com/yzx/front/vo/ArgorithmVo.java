package com.yzx.front.vo;

import com.yzx.front.entity.YzxArticles;
import com.yzx.front.entity.YzxTag;
import lombok.Data;

import java.util.List;

/**
 * @author yzx
 * @create 2023-01-04 17:07
 */
@Data
public class ArgorithmVo {
    private List<YzxArticles> articles;
    private List<YzxTag> tag;
    private Integer pageCount;
}
