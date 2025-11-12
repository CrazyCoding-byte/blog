package com.yzx.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzx.front.entity.YzxArticles;
import com.yzx.front.vo.PieData;
import com.yzx.front.vo.SearchObjVo;
import com.yzx.common_utils.result.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
public interface YzxArticlesService extends IService<YzxArticles> {

    R showArticles(Integer page, Integer limit, SearchObjVo searchObjVo);

    R getSource(HttpServletRequest servletRequest,Integer page,Integer limit);

    R Argorithm(Integer page, Integer limit);

    List<PieData> articlesCount();

    R searchArticlsOrInformal(Integer page, Integer limit, SearchObjVo searchObjVo);
}
