package com.yzx.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.front.entity.YzxArticles;
import com.yzx.front.vo.PieData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
@Mapper
public interface YzxArticlesMapper extends BaseMapper<YzxArticles> {

    List<PieData> showArticlesCount();
}
