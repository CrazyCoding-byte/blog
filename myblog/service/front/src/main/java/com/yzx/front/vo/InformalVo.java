package com.yzx.front.vo;

import com.yzx.front.entity.YzxInformal;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yzx
 * @create 2023-01-05 21:48
 */
@Data
public class InformalVo {
    private List<YzxInformal> informals;
    private List<PieData> pieData;
    private Integer pageCount;
}
