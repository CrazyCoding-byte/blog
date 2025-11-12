package com.yzx.articles.service;

import com.yzx.articles.entity.YzxArticles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzx.articles.vo.SearchObjVo;
import com.yzx.common_utils.result.R;

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

    boolean removeArticleById(String articleId);
}
